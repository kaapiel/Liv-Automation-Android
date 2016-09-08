package br.com.pontomobi.livelopontos.ui.forgotPassword;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.EditText;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.widget.BannerError;
import br.com.pontomobi.livelopontos.util.Mask;
import br.com.pontomobi.livelopontos.util.StringUtil;
import br.com.pontomobi.livelopontos.util.Util;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by selemafonso on 19/04/16.
 */
public class ForgotPasswordActivity extends LiveloPontosActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.forgot_password_cpf)
    EditText forgotPasswordCpf;
    @Bind(R.id.forgot_password_send)
    AppCompatButton forgotPasswordSend;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;
    @Bind(R.id.forgot_password_clean_cpf_email)
    ImageView forgotPasswordCleanCpfEmail;
    @Bind(R.id.forgot_password_banner_error)
    BannerError forgotPasswordBannerError;
    @Bind(R.id.main_content_forgot)
    RelativeLayout mainContentForgot;

    private boolean hideBannerInProgess = false;
    private ForgotPasswordBusiness forgotPasswordBusiness;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        configureActionBar();

        maskCpf();
        listenerEditCpf();
        setTypefaceInEditText();

        forgotPasswordBusiness = new ForgotPasswordBusiness(getBaseContext(), getOnForgotPasswordBusinessListener());
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(toolbar);
        super.configureActionBar();
        toolbarTitle.setText(R.string.forgot_password_titlle_toolbar);
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
        super.onBackPressed();
    }

    @OnClick(R.id.forgot_password_clean_cpf_email)
    public void onClickCleanCpf() {
        forgotPasswordCpf.setText("");
    }

    @OnClick(R.id.forgot_password_send)
    public void onClickSend() {
        Util.hideKeyboard(mainContentForgot, getBaseContext());
        callService();
    }

    private void callService() {
        loadingContent.setVisibility(View.VISIBLE);
        forgotPasswordBusiness.callService(StringUtil.removeCaractersCPF(forgotPasswordCpf.getText().toString()));
    }

    private void maskCpf() {
        forgotPasswordCpf.addTextChangedListener(Mask.insert("###.###.###-##", forgotPasswordCpf));
    }

    private void setTypefaceInEditText() {
        Typeface museoSans = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_300.otf");
        forgotPasswordCpf.setTypeface(museoSans);
    }

    private void listenerEditCpf() {
        forgotPasswordCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                controlOption();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                controlOption();
            }

            @Override
            public void afterTextChanged(Editable s) {
                controlOption();
            }
        });
    }

    private void controlOption() {
        if (forgotPasswordBannerError.getVisibility() == View.VISIBLE) {
            hideBannerError();
            hideBannerInProgess = true;
        }

        if (forgotPasswordCpf.getText().toString().length() == Constants.CPF_TEXT_SIZE_NOT_FORMATTED) {
            forgotPasswordSend.setEnabled(true);
            forgotPasswordSend.setTextColor(getResources().getColor(R.color.button_enable));
        } else {
            forgotPasswordSend.setEnabled(false);
            forgotPasswordSend.setTextColor(getResources().getColor(R.color.button_disable));
        }
    }

    private void showBannerError() {
        forgotPasswordCpf.setError(" ");
        forgotPasswordBannerError.setBannerText(getResources().getString(R.string.forgot_password_msg_error));
        forgotPasswordBannerError.setVisibility(View.VISIBLE);
        forgotPasswordBannerError.showAndAnimBannerError();
    }

    private void hideBannerError() {
        if (hideBannerInProgess) {
            return;
        }
        forgotPasswordCpf.setError(null);
        forgotPasswordBannerError.hideAndAnimBannerError();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                forgotPasswordBannerError.setVisibility(View.INVISIBLE);
                hideBannerInProgess = false;
            }
        }, 1000);
    }

    private OnForgotPasswordBusinessListener getOnForgotPasswordBusinessListener() {
        return new OnForgotPasswordBusinessListener() {
            @Override
            public void onRecoverSuccess() {
                loadingContent.setVisibility(View.GONE);
                showDialogSuccess();
            }

            @Override
            public void onRecoverFail(LiveloException exception) {
                loadingContent.setVisibility(View.GONE);
                if (exception.getErrorCode() == LiveloException.EXCEPTION_INTERNET_ERROR) {
                    showDialogNoConnection(exception.getAlertToShow(getBaseContext()));
                } else {
                    showBannerError();
                }
            }
        };
    }

    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(ForgotPasswordActivity.this, alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callService();
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

    private void showDialogSuccess() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();
        Alert alert = new Alert();

        alert.setTitle(getString(R.string.forgot_password_dialog_title));
        alert.setDescription(getString(R.string.forgot_password_dialog_descrioption));
        alert.setImage(getResources().getResourceEntryName(R.drawable.msg_confirmacao));
        alert.setPositiveButton(getString(R.string.forgot_password_dialog_positive));
        dialogCustom.showCustomDialog(ForgotPasswordActivity.this, alert, true,
                new DialogCustomAlert.AlertDialogClickListener() {
                    @Override
                    public void onPositiveClick() {

                        onBackPressed();
                    }

                    @Override
                    public void onNegativeClick() {
                    }

                    @Override
                    public void onBackPressedInDialog() {
                        onBackPressed();
                    }
                });
    }

    private void changePassToFingerPrint() {

    }

    public interface OnForgotPasswordBusinessListener {
        void onRecoverSuccess();

        void onRecoverFail(LiveloException exception);
    }

}
