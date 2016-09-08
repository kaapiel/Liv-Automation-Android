package br.com.pontomobi.livelopontos.ui.changePassword;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.EditText;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.helper.KeyStoreHelper;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.model.Password;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.widget.BannerError;
import br.com.pontomobi.livelopontos.ui.widget.PasswordComponent;
import br.com.pontomobi.livelopontos.util.Util;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by selemafonso on 20/04/16.
 */
public class ChangePasswordActivity extends LiveloPontosActivity {


    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.change_password_save)
    AppCompatButton changePasswordSave;
    @Bind(R.id.change_password_banner_error)
    BannerError changePasswordBannerError;
    @Bind(R.id.change_password_old)
    PasswordComponent changePasswordOld;
    @Bind(R.id.change_password_new)
    PasswordComponent changePasswordNew;
    @Bind(R.id.change_password_new_confirm)
    PasswordComponent changePasswordNewConfirm;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;

    private boolean hideBannerInProgess = false;
    private ChangePasswordBusiness changePasswordBusiness;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        configureActionBar();
        listenersText(changePasswordOld.getPassword());
        listenersText(changePasswordNew.getPassword());
        listenersText(changePasswordNewConfirm.getPassword());

        changePasswordBusiness = new ChangePasswordBusiness(getBaseContext(), getOnChangePasswordBusinessListener());
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(toolbar);
        super.configureActionBar();
        toolbarTitle.setText(R.string.change_password_title_toolbar);
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

    @OnClick(R.id.change_password_save)
    public void onClickChangePassword() {
        checkPasswords();
    }

    private void checkPasswords() {
        Password password = new Password(changePasswordNewConfirm.getPassword().getText().toString(),
                changePasswordNew.getPassword().getText().toString(),
                changePasswordOld.getPassword().getText().toString());

        password = password.checkIfPasswordIsValid(getBaseContext());
        if(!password.isValid()) {
            showBannerError(password.getMsg());
            return;
        }

        callService();
    }

    private void callService() {
        loadingContent.setVisibility(View.VISIBLE);
        changePasswordBusiness.callService(changePasswordOld.getPassword().getText().toString(), changePasswordNew.getPassword().getText().toString());
    }


    private void showBannerError(String msg) {
        changePasswordNew.getPassword().setError(" ");
        changePasswordNewConfirm.getPassword().setError(" ");
        changePasswordBannerError.setBannerText(msg);
        changePasswordBannerError.setVisibility(View.VISIBLE);
        changePasswordBannerError.showAndAnimBannerError();
    }

    private void hideBannerError() {
        if (hideBannerInProgess) {
            return;
        }
        changePasswordBannerError.hideAndAnimBannerError();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                changePasswordBannerError.setVisibility(View.INVISIBLE);
                hideBannerInProgess = false;
            }
        }, 1000);
    }

    private void listenersText(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkEnableButton();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEnableButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEnableButton();
            }
        });
    }

    private void checkEnableButton() {
        if (changePasswordBannerError.getVisibility() == View.VISIBLE) {
            hideBannerError();
            hideBannerInProgess = true;
        }

        String oldPassword = changePasswordOld.getPassword().getText().toString();
        String newPassword = changePasswordNew.getPassword().getText().toString();
        String confirmNewPassword = changePasswordNewConfirm.getPassword().getText().toString();
        if (oldPassword.length() == Constants.PASSWORD_TEXT_SIZE
                && newPassword.length() == Constants.PASSWORD_TEXT_SIZE
                && confirmNewPassword.length() == Constants.PASSWORD_TEXT_SIZE) {
            changePasswordSave.setEnabled(true);
            changePasswordSave.setTextColor(getResources().getColor(R.color.button_enable));
        } else {
            changePasswordSave.setEnabled(false);
            changePasswordSave.setTextColor(getResources().getColor(R.color.button_disable));
        }
        changePasswordNew.getPassword().setError(null);
        changePasswordNewConfirm.getPassword().setError(null);
    }

    private OnChangePasswordBusinessListener getOnChangePasswordBusinessListener() {
        return new OnChangePasswordBusinessListener() {
            @Override
            public void onChangeSuccess() {
                loadingContent.setVisibility(View.GONE);

                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_CHANGE_PASSWORD,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_CHANGE_PASSWORD,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_PASSWORD_CHANGED,
                        ""
                );

                showDialogSuccess();
            }

            @Override
            public void onChangeFail(LiveloException exception) {
                loadingContent.setVisibility(View.GONE);
                if (exception.getErrorCode() == LiveloException.EXCEPTION_INTERNET_ERROR) {
                    showDialogNoConnection(exception.getAlertToShow(getBaseContext()));
                } else {
                    showBannerError(getString(R.string.change_password_invalid));
                }
            }
        };
    }

    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(this, alert, true,
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

        alert.setTitle(getString(R.string.change_password_success_title));
        alert.setDescription(getString(R.string.change_password_success_description));
        alert.setImage(getResources().getResourceEntryName(R.drawable.msg_confirmacao));
        alert.setPositiveButton(getString(R.string.change_password_success_positive));
        dialogCustom.showCustomDialog(this, alert, true,
                new DialogCustomAlert.AlertDialogClickListener() {
                    @Override
                    public void onPositiveClick() {
                        saveLoginInfo();
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

    private void saveLoginInfo() {
        if (!Util.checkFingerPrintIsEnalbe(getBaseContext())) {
            return;
        }

        KeyStoreHelper keyStoreHelper = new KeyStoreHelper(getBaseContext());
        keyStoreHelper.generateKey(changePasswordNew.getPassword().getText().toString());
    }

    public interface OnChangePasswordBusinessListener {
        void onChangeSuccess();

        void onChangeFail(LiveloException exception);
    }
}
