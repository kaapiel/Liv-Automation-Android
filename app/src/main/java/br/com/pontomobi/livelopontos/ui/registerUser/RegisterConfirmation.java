package br.com.pontomobi.livelopontos.ui.registerUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.ui.home.HomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 18/09/15.
 */
public class RegisterConfirmation extends LiveloPontosActivity implements View.OnClickListener {

    public static final String BUNDLE_USER = "user_cpf";
    public static final String BUNDLE_PASSWORD = "user_password";

    @Bind(R.id.register_confirmation_title) TextView registerConfirmationTitle;
    @Bind(R.id.register_confirmation_button) Button registerConfirmationButton;
    @Bind(R.id.loading_content) RelativeLayout loadingContent;

    //private RegisterConfirmationBusiness mRegisterConfirmationBusiness;

    private String mUserCpf;
    private String mUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirmation);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            mUserCpf = getIntent().getExtras().getString(BUNDLE_USER, "");
            mUserPassword = getIntent().getExtras().getString(BUNDLE_PASSWORD, "");
        }

        //mRegisterConfirmationBusiness = new RegisterConfirmationBusiness(this, getRegisterConfirmation());

        initButtonColor();

    }


    private void initButtonColor() {
        registerConfirmationButton.setTextColor(getResources().getColor(R.color.activate_account_button_enable));
        registerConfirmationButton.setEnabled(true);
        registerConfirmationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_confirmation_button:
                openHomeActivity();
                break;

        }
    }

    private void openHomeActivity(){
        Intent intent = new Intent(RegisterConfirmation.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /*private void callService(){
        loadingContent.setVisibility(View.VISIBLE);
        registerConfirmationButton.setEnabled(false);
        mRegisterConfirmationBusiness.callServiceLogin(mUserCpf, mUserPassword);
    }

    private RegisterConfirmationBusiness.OnRegisterConfirmationListener getRegisterConfirmation(){
        return new RegisterConfirmationBusiness.OnRegisterConfirmationListener() {
            @Override
            public void onSuccess() {
                loadingContent.setVisibility(View.GONE);
                registerConfirmationButton.setEnabled(true);
                startActivity(new Intent(RegisterConfirmation.this, ActivateDeviceActivity.class));
                finish();
            }

            @Override
            public void onFail(final LiveloException exception) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingContent.setVisibility(View.GONE);
                        registerConfirmationButton.setEnabled(true);
                        if (exception.getErrorCode() == LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR) {
                            showBannerErrorRegister();
                        } else {
                            showDialogNoConnection(exception.getAlertToShow(getBaseContext()));
                        }
                    }
                }, 500);
            }
        };
    }

    private void showBannerErrorRegister() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();
        dialogCustom.showCustomDialog(this, LiveloPontosApp.getInstance().getErrorGeneric(), false,
                new DialogCustomAlert.AlertDialogClickListener() {
                    @Override
                    public void onPositiveClick() {
                        callService();
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


    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();
        dialogCustom.showCustomDialog(RegisterConfirmation.this, alert, true,
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
    }*/

}
