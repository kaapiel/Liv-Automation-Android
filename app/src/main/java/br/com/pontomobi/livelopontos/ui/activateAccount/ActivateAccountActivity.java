package br.com.pontomobi.livelopontos.ui.activateAccount;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rey.material.widget.EditText;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.activate.model.ActivateUserResponse;
import br.com.pontomobi.livelopontos.service.livelo.activate.model.UserData;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.registerUser.RegisterUserActivity;
import br.com.pontomobi.livelopontos.util.Mask;
import br.com.pontomobi.livelopontos.util.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by selem.gomes on 08/09/15.
 */
public class ActivateAccountActivity extends LiveloPontosActivity implements View.OnClickListener {
    private static final int CPF_TEXT_SIZE = 14;
    private static final int PASSWORD_TEXT_SIZE = 6;

    @Bind(R.id.activate_account_toolbar)
    Toolbar activateAccountToolbar;
    @Bind(R.id.activate_account_cpf)
    EditText activateAccountCpf;
    @Bind(R.id.activate_account_clean_cpf)
    ImageView activateAccountCleanCpf;
    @Bind(R.id.activate_account_activation_code)
    EditText activateAccountActivationCode;
    @Bind(R.id.activate_account_clean_activation_code)
    ImageView activateAccountCleanActivationCode;
    @Bind(R.id.activate_account_activate)
    AppCompatButton activateAccountActivate;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;

    @Bind(R.id.btn_request_code) AppCompatButton btnRequestCode;

    private ActivateBusiness mActivateBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_account);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        configureActionBar();
        initButtonColor();
        setTypefaceInEditText();
        maskCpf();
        textChangedListeners();

        mActivateBusiness = new ActivateBusiness(this, getOnServiceActivateListener());
    }

    private void setTypefaceInEditText() {
        Typeface MuseoSans100 = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_100.otf");

        activateAccountCpf.setTypeface(MuseoSans100);
        activateAccountActivationCode.setTypeface(MuseoSans100);
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(activateAccountToolbar);
        super.configureActionBar();
    }

    private void initButtonColor() {
        activateAccountActivate.setTextColor(getResources().getColor(R.color.activate_account_button_disable));
        activateAccountActivate.setEnabled(false);
        activateAccountActivate.setOnClickListener(this);

        btnRequestCode.setTextColor(getResources().getColor(R.color.activate_account_button_enable));
        btnRequestCode.setEnabled(true);
        btnRequestCode.setOnClickListener(this);

    }

    private void maskCpf() {
        activateAccountCpf.addTextChangedListener(Mask.insert("###.###.###-##", activateAccountCpf));
    }

    @OnClick(R.id.activate_account_clean_cpf)
    public void onLoginCleanCPF() {
        activateAccountCpf.setText("");
    }

    @OnClick(R.id.activate_account_clean_activation_code)
    public void onLoginCleanCode() {
        activateAccountActivationCode.setText("");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        openLogin();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        openLogin();
    }

    private void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openLink() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(ActivateAccountActivity.this, LiveloPontosApp.getInstance().getOpenUrl(), true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(Constants.URL_REQUEST_CODE));
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

    private void checkCpfTextSizeAndPassword() {
        if (activateAccountCpf.getText().toString().length() == CPF_TEXT_SIZE
                && (activateAccountActivationCode.getText().toString().length() == PASSWORD_TEXT_SIZE)) {
            enableOrDisableBtnActivate(true);
        } else {
            enableOrDisableBtnActivate(false);
        }
    }

    private void showOrHideCleanCpf() {
        if (activateAccountCpf.getText().length() > 0) {
            activateAccountCleanCpf.setVisibility(View.VISIBLE);
        } else {
            activateAccountCleanCpf.setVisibility(View.INVISIBLE);
        }
    }

    private void showOrHideCleanPassword() {
        if (activateAccountActivationCode.getText().length() > 0) {
            activateAccountCleanActivationCode.setVisibility(View.VISIBLE);
        } else {
            activateAccountCleanActivationCode.setVisibility(View.INVISIBLE);
        }
    }

    private void enableOrDisableBtnActivate(boolean enable) {
        if (enable) {
            activateAccountActivate.setTextColor(getResources().getColor(R.color.activate_account_button_enable));
            activateAccountActivate.setEnabled(true);

            btnRequestCode.setTextColor(getResources().getColor(R.color.activate_account_button_disable));
            btnRequestCode.setEnabled(false);

        } else {
            activateAccountActivate.setTextColor(getResources().getColor(R.color.activate_account_button_disable));
            activateAccountActivate.setEnabled(false);

            btnRequestCode.setTextColor(getResources().getColor(R.color.activate_account_button_enable));
            btnRequestCode.setEnabled(true);

        }
    }


    private void textChangedListeners() {
        activateAccountCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkCpfTextSizeAndPassword();
                showOrHideCleanCpf();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCpfTextSizeAndPassword();
                showOrHideCleanCpf();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkCpfTextSizeAndPassword();
                showOrHideCleanCpf();
            }
        });


        activateAccountActivationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkCpfTextSizeAndPassword();
                showOrHideCleanPassword();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCpfTextSizeAndPassword();
                showOrHideCleanPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
                showOrHideCleanPassword();
            }
        });

    }

    private ActivateBusiness.OnServiceActivateListener getOnServiceActivateListener() {
        return new ActivateBusiness.OnServiceActivateListener() {
            @Override
            public void onActivateSuccess(ActivateUserResponse response) {
                loadingContent.setVisibility(View.GONE);

                UserData userData = response.getOutput().getUserData();

                if (TextUtils.isEmpty(userData.getCPF()))
                    userData.setCPF(activateAccountCpf.getText().toString());

                Intent intent = new Intent(ActivateAccountActivity.this, RegisterUserActivity.class);
                intent.putExtra(RegisterUserActivity.BUNDLE_USER_DATA, userData);
                intent.putExtra(RegisterUserActivity.BUNDLE_COOKIE_JSESSIONID, response.getjSessionId());

                startActivity(intent);
            }

            @Override
            public void onLoginFail(final LiveloException exception) {
                loadingContent.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingContent.setVisibility(View.GONE);
                        if (exception.getErrorCode() == LiveloException.EXCEPTION_SERVICE_ERROR) {
                            showBannerErrorLogin();
                        } else {
                            showDialogNoConnection(exception.getAlertToShow(getBaseContext()));
                        }
                    }
                }, 500);
            }
        };
    }

    private void showBannerErrorLogin() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(this, LiveloPontosApp.getInstance().getErrorGeneric(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callServiceActivate();
                        }

                        @Override
                        public void onNegativeClick() {
                            Log.d("DIALOG", "NEGATIVE");
                        }

                        @Override
                        public void onBackPressedInDialog() {

                        }
                    });
        }
    }

    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(ActivateAccountActivity.this, alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callServiceActivate();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activate_account_activate:
                callServiceActivate();
                break;

            case R.id.btn_request_code:
                openLink();
                break;

        }
    }

    private void callServiceActivate() {
        loadingContent.setVisibility(View.VISIBLE);
        String cpf = StringUtil.removeCaractersCPF(activateAccountCpf.getText().toString());
        String code = activateAccountActivationCode.getText().toString();

        mActivateBusiness.callServiceActivate(cpf, code);
    }
}
