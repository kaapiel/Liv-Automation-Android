package br.com.pontomobi.livelopontos.ui.login;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.rey.material.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.helper.KeyStoreHelper;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.model.User;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.ui.activateAccount.ActivateAccountActivity;
import br.com.pontomobi.livelopontos.ui.activateDevice.ActivateDeviceActivity;
import br.com.pontomobi.livelopontos.ui.brandingbank.BrandingBankActivity;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.fingerPrint.FingerprintAuthenticationDialogFragment;
import br.com.pontomobi.livelopontos.ui.forgotPassword.ForgotPasswordActivity;
import br.com.pontomobi.livelopontos.ui.home.HomeActivity;
import br.com.pontomobi.livelopontos.ui.widget.BannerError;
import br.com.pontomobi.livelopontos.util.*;
import br.com.pontomobi.livelopontos.util.Mask;
import br.com.pontomobi.livelopontos.util.StringUtil;
import br.com.pontomobi.livelopontos.util.Util;
import br.com.pontomobi.livelopontos.util.ValidateUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by selem.gomes on 03/09/15.
 */
public class LoginActivity extends LiveloPontosActivity {
    private static final String KEY_NAME = "livelo_key";
    private static final String DIALOG_FRAGMENT_TAG = "fingerPrintFragment";

    @Bind(R.id.login_forgot_password)
    TextView loginForgotPassword;
    @Bind(R.id.login_banner_error)
    BannerError loginBannerError;
    @Bind(R.id.login_join)
    AppCompatButton loginJoin;
    @Bind(R.id.login_activate_account)
    AppCompatButton loginActivateAccount;
    @Bind(R.id.login_clean_cpf_email)
    ImageView loginCleanCpfEmail;
    @Bind(R.id.login_clean_password)
    ImageView loginCleanPassword;
    @Bind(R.id.login_cpf)
    EditText loginCpf;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;
    @Bind(R.id.login_main_content)
    RelativeLayout loginMainContent;

    private boolean hideBannerInProgess = false;
    private LoginBusiness loginBusiness;
    private boolean activityEnable = false;
    private KeyguardManager mKeyguardManager;
    private FingerprintManager mFingerprintManager;
    private FingerprintAuthenticationDialogFragment mFragment;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private Cipher mCipher;
    private FirebaseOptions options;
    private Map<String, Object> auth = new HashMap<String, Object>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activityEnable = true;

        //loginBusiness = new LoginBusiness(getOnLoginBusinessListener(), getBaseContext());

        //checkIfLoginIsNeed();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        initButtonsColor();
        setTypefaceInEditText();
        textChangedListeners();
        maskCpf();
        initFingerPrints();
    }

    private void initFingerPrints() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (!Util.checkFingerPrintIsEnalbe(getBaseContext())) {
            return;
        }

        //mFingerprintManager = loginBusiness.getFingerprintManager();
        mKeyguardManager = loginBusiness.getKeyguardManager();

        if (!mKeyguardManager.isKeyguardSecure()) {
            return;
        }
        if (!mFingerprintManager.hasEnrolledFingerprints()) {
            return;
        }

        mKeyStore = loginBusiness.getKeystore();
        mKeyGenerator = loginBusiness.getKeyGenerator();
        mCipher = loginBusiness.getCipher(mKeyStore);
        mFragment = new FingerprintAuthenticationDialogFragment();

        createKey();
        initCipher();
        mFragment.setmCryptoObject(new FingerprintManager.CryptoObject(mCipher));
        mFragment.setOnLoginByFingerPrint(getOnLoginByFingerPrint());
        mFragment.setFingerprintManager(mFingerprintManager);
        mFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);

    }

    public void createKey() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        try {
            mKeyStore.load(null);
            mKeyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            mKeyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean initCipher() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityEnable = true;
    }

    @Override
    protected void onStop() {
        activityEnable = false;
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void checkIfLoginIsNeed() {
        if (LoginUtil.loadInfoAboutLogin(getBaseContext()) != null) {
            if (checkDeviceActive()) {
                goToHome();
            } else {
                goToActivateDevice();
            }
        }
    }

    private void setTypefaceInEditText() {
        Typeface MuseoSans100 = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_100.otf");
        loginCpf.setTypeface(MuseoSans100);
        loginPassword.setTypeface(MuseoSans100);
    }

    private void initButtonsColor() {
        enableOrDisableBtnLogin(false);
        loginActivateAccount.setTextColor(getResources().getColor(R.color.login_buttons_enable));
        loginActivateAccount.setEnabled(true);
    }

    private void enableOrDisableBtnLogin(boolean enable) {
        if (enable) {
            loginActivateAccount.setTextColor(getResources().getColor(R.color.login_buttons_disable));
            loginActivateAccount.setEnabled(false);

            loginJoin.setTextColor(getResources().getColor(R.color.login_buttons_enable));
            loginJoin.setEnabled(true);
        } else {
            loginJoin.setTextColor(getResources().getColor(R.color.login_buttons_disable));
            loginJoin.setEnabled(false);

            loginActivateAccount.setTextColor(getResources().getColor(R.color.login_buttons_enable));
            loginActivateAccount.setEnabled(true);
        }
    }

    private void maskCpf() {
        loginCpf.addTextChangedListener(Mask.insert("###.###.###-##", loginCpf));
    }

    private void showBannerErrorLogin(String message) {
        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_LOGIN,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_LOGIN,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_RESCUED_PASSWORD,
                Constants.GoogleAnalytisEvents.EVENT_LABEL_VALIDATION_ERROR
        );

        if (message == null || message.isEmpty()) {
            loginBannerError.setBannerText(getResources().getString(R.string.login_wrong_user_ou_password));
        } else {
            loginBannerError.setBannerText(message);
        }

        loginBannerError.setVisibility(View.VISIBLE);
        loginBannerError.showAndAnimBannerError();
    }

    private void hideBannerErrorLogin() {
        if (hideBannerInProgess) {
            return;
        }
        loginBannerError.hideAndAnimBannerError();
        hideBannerInProgess = false;
    }

    @OnClick(R.id.login_join)
        public void onLoginJoinClick() {
        Util.hideKeyboard(this.getCurrentFocus(), getBaseContext());
        loadingContent.setVisibility(View.VISIBLE);
        //callServiceLogin();
        goToHome();
    }

    @OnClick(R.id.login_activate_account)
    public void onLoginActivateAccountClick() {
        Util.hideKeyboard(this.getCurrentFocus(), getBaseContext());

        //openUrl(LiveloPontosApp.getInstance().getOpenUrl(), Constants.URL_ACTIVATE_ACCOUNT, false);
        Intent intent = new Intent(this, ActivateAccountActivity.class);
        startActivity(intent);
    }

    private void openUrl(Alert alert, final String urlPage, final boolean isForgotPassword) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(LoginActivity.this, alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            if (isForgotPassword) {
                                LiveloPontosApp.getInstance().sendTrackerEvent(
                                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_LOGIN,
                                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_LOGIN,
                                        Constants.GoogleAnalytisEvents.EVENT_ACTION_RESCUED_PASSWORD,
                                        ""
                                );
                            } else {
                                LiveloPontosApp.getInstance().sendTrackerEvent(
                                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_LOGIN,
                                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_LOGIN,
                                        Constants.GoogleAnalytisEvents.EVENT_ACTION_ACTIVATE_ACCOUNT,
                                        ""
                                );
                            }

                            String url = urlPage;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }

                        @Override
                        public void onNegativeClick() {

                        }

                        @Override
                        public void onBackPressedInDialog() {
                        }
                    });
        }
    }

    @OnClick(R.id.login_clean_cpf_email)
    public void onLoginCleanCpfEmailClick() {
        loginCpf.setText("");
    }

    @OnClick(R.id.login_clean_password)
    public void onLoginCleanPasswordClick() {
        loginPassword.setText("");
    }

    @OnClick(R.id.login_forgot_password)
    public void onLoginForgotPasswordClick() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    private void checkCpfTextSizeAndPassword() {
        if (loginBannerError.getVisibility() == View.VISIBLE) {
            hideBannerErrorLogin();
            hideBannerInProgess = true;
        }

        if (loginCpf.getText().toString().length() == Constants.CPF_TEXT_SIZE_NOT_FORMATTED
                && (loginPassword.getText().toString().length() == Constants.PASSWORD_TEXT_SIZE)) {
            enableOrDisableBtnLogin(true);
        } else {
            enableOrDisableBtnLogin(false);
        }
    }

    private void showOrHideCleanCpf() {
        if (loginCpf.getText().length() > 0) {
            loginCleanCpfEmail.setVisibility(View.VISIBLE);
        } else {
            loginCleanCpfEmail.setVisibility(View.INVISIBLE);
        }
    }

    private void showOrHideCleanPassword() {
        if (loginPassword.getText().length() > 0) {
            loginCleanPassword.setVisibility(View.VISIBLE);
        } else {
            loginCleanPassword.setVisibility(View.INVISIBLE);
        }
    }

    private void textChangedListeners() {
        loginCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().length() < Constants.CPF_TEXT_SIZE_NOT_FORMATTED) {
                    checkCpfTextSizeAndPassword();
                    showOrHideCleanCpf();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < Constants.CPF_TEXT_SIZE_NOT_FORMATTED) {
                    checkCpfTextSizeAndPassword();
                    showOrHideCleanCpf();
                } else {
                    if (loginBannerError.getVisibility() == View.VISIBLE) {
                        loginBannerError.setVisibility(View.INVISIBLE);
                        hideBannerInProgess = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtil.removeCaractersCPF(s.toString()).length() == Constants.CPF_TEXT_SIZE) {
                    if (ValidateUtil.isValidCPF(StringUtil.removeCaractersCPF(s.toString()))) {
                        loginPassword.requestFocus();
                    } else {
                        if (!(loginBannerError.getVisibility() == View.VISIBLE)) {
                            showBannerErrorLogin(getResources().getString(R.string.login_wrong_cpf));
                        }
                    }
                }
            }
        });


        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkCpfTextSizeAndPassword();
                showOrHideCleanPassword();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCpfTextSizeAndPassword();
                showOrHideCleanPassword();
                if (s.length() >= 6) {
                    Util.hideKeyboard(loginMainContent, getBaseContext());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                showOrHideCleanPassword();
            }
        });

        loginPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && loginJoin.isEnabled()) {
                    callServiceLogin();
                }
                return false;
            }
        });

    }

    private void callServiceLogin() {
//        LiveloPontosApp.getInstance().sendTrackerEvent(
//                Constants.GoogleAnalytisEvents.EVENT_SCREEN_LOGIN,
//                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_LOGIN,
//                Constants.GoogleAnalytisEvents.EVENT_ACTION_JOIN,
//                ""
//        );

        loadingContent.setVisibility(View.VISIBLE);
        String cpf = StringUtil.removeCaractersCPF(loginCpf.getText().toString());
        String pass = loginPassword.getText().toString();

        loginBusiness.callServiceLogin(cpf, pass);
    }

    private OnLoginBusinessListener getOnLoginBusinessListener() {
        OnLoginBusinessListener onLoginBusinessListener = new OnLoginBusinessListener() {
            @Override
            public void onLoginSuccess() {
                loadingContent.setVisibility(View.GONE);
                openBrandingBank();
            }

            @Override
            public void onLoginFail(final LiveloException exception) {
                if (!activityEnable) {
                    return;
                }

                LoginUtil.clearLogin(getBaseContext());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingContent.setVisibility(View.GONE);
                        if (exception.getErrorCode() == LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR) {
                            showBannerErrorLogin(null);
                        } else {
                            showDialogNoConnection(exception.getAlertToShow(getBaseContext()));
                        }
                    }
                }, 500);
            }
        };

        return onLoginBusinessListener;
    }

    private void goToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToActivateDevice() {
        startActivity(new Intent(LoginActivity.this, ActivateDeviceActivity.class));
        finish();
    }

    private void openBrandingBank() {
        startActivity(new Intent(this, BrandingBankActivity.class));
        finish();
    }

    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(LoginActivity.this, alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callServiceLogin();
                        }

                        @Override
                        public void onNegativeClick() {
                        }

                        @Override
                        public void onBackPressedInDialog() {

                        }
                    });
        }
    }

    private boolean checkDeviceActive() {
        return SharedPreferencesHelper.read(this, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.DEVICE_ACTIVE, false);
    }

    private OnLoginByFingerPrint getOnLoginByFingerPrint() {
        return new OnLoginByFingerPrint() {
            @Override
            public void onResultLogin(boolean success) {
                if (success) {
                    LiveloPontosApp.getInstance().sendTrackerEvent(
                            Constants.GoogleAnalytisEvents.EVENT_SCREEN_LOGIN,
                            Constants.GoogleAnalytisEvents.EVENT_CATEGORY_LOGIN,
                            Constants.GoogleAnalytisEvents.EVENT_ACTION_JOIN_WITH_TOUCH_ID,
                            ""
                    );

                    KeyStoreHelper keyStoreHelper = new KeyStoreHelper(getBaseContext());
                    User user = keyStoreHelper.recoverKey();

                    if (user != null) {
                        loginCpf.setText(user.getCpf());
                        loginPassword.setText(user.getPassword());
                        loginJoin.callOnClick();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Util.hideKeyboard(loginMainContent, getBaseContext());
                            }
                        }, 100);

                    }
                }
            }
        };
    }

    public interface OnLoginBusinessListener {
        void onLoginSuccess();

        void onLoginFail(LiveloException exception);
    }

    public interface OnLoginByFingerPrint {
        void onResultLogin(boolean success);
    }


}
