package br.com.pontomobi.livelopontos.ui.myAccount;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.EditText;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.helper.KeyStoreHelper;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.login.LoginBusiness;
import br.com.pontomobi.livelopontos.ui.widget.BannerError;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by selem.gomes on 09/05/16.
 */
public class PasswordFingerPrintActivity extends LiveloPontosActivity {
    public final static String PARAM_PASSWORD = "param_password";

    @Bind(R.id.alerts_image)
    ImageView alertsImage;
    @Bind(R.id.custom_dialog_alerts_description)
    TextView customDialogAlertsDescription;
    @Bind(R.id.alerts_password)
    EditText alertsPassword;
    @Bind(R.id.alerts_negative_button)
    TextView alertsNegativeButton;
    @Bind(R.id.alerts_positive_button)
    TextView alertsPositiveButton;
    LoginBusiness loginBusiness;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;
    @Bind(R.id.main_content_fingerprint)
    RelativeLayout mainContentFingerprint;
    @Bind(R.id.banner_error)
    BannerError bannerError;

    private boolean hideBannerInProgess = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_fingerprint);
        ButterKnife.bind(this);
        loginBusiness = new LoginBusiness(getOnLoginBusinessListener(), getBaseContext());
        listenerPassword();

    }

    private void listenerPassword() {
        alertsPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                hideBanner();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideBanner();
            }

            @Override
            public void afterTextChanged(Editable s) {
                hideBanner();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        closeWithPassword(false);
    }

    private void closeWithPassword(boolean enable) {
        if (enable) {
            Intent intent = new Intent();
            intent.putExtra(PARAM_PASSWORD, enable);

            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }


    @OnClick({R.id.alerts_negative_button, R.id.alerts_positive_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alerts_negative_button:
                onBackPressed();
                break;
            case R.id.alerts_positive_button:
                hideBanner();
                if (TextUtils.isEmpty(alertsPassword.getText().toString())) {
                    return;
                }
                loadingContent.setVisibility(View.VISIBLE);
                loginBusiness.callServiceLogin(LiveloPontosApp.getInstance().getCpf(), alertsPassword.getText().toString());
                break;
        }
    }

    private LoginActivity.OnLoginBusinessListener getOnLoginBusinessListener() {
        LoginActivity.OnLoginBusinessListener onLoginBusinessListener = new LoginActivity.OnLoginBusinessListener() {
            @Override
            public void onLoginSuccess() {
                loadingContent.setVisibility(View.GONE);

                KeyStoreHelper keyStoreHelper = new KeyStoreHelper(getBaseContext());
                if (!TextUtils.isEmpty(keyStoreHelper.generateKey(alertsPassword.getText().toString()))) {
                    SharedPreferencesHelper.write(getBaseContext(), Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.FINGERPRINT_ENABLE, true);
                    closeWithPassword(true);
                } else {
                    closeWithPassword(false);
                }
            }

            @Override
            public void onLoginFail(final LiveloException exception) {
                loadingContent.setVisibility(View.GONE);
                bannerError.setVisibility(View.VISIBLE);
                bannerError.setBannerText("Verifique sua senha e tente novamente");
                bannerError.showAndAnimBannerError();

            }
        };

        return onLoginBusinessListener;
    }

    private void hideBanner() {
        hideBannerErrorLogin();
        hideBannerInProgess = true;
    }

    private void hideBannerErrorLogin() {
        if (hideBannerInProgess) {
            return;
        }
        bannerError.hideAndAnimBannerError();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bannerError.setVisibility(View.INVISIBLE);
                hideBannerInProgess = false;
            }
        }, 1000);
    }
}
