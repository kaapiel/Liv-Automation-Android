package br.com.pontomobi.livelopontos.ui.activateDevice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ProfileResponse;
import br.com.pontomobi.livelopontos.ui.activateDevice.activatecode.ActivateCodeFragment;
import br.com.pontomobi.livelopontos.ui.activateDevice.requestcode.RequestCodeFragment;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.home.HomeActivity;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.myInfo.MyInfoBusiness;
import br.com.pontomobi.livelopontos.ui.registerUser.RegisterConfirmation;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vilmar.filho on 1/25/16.
 */
public class ActivateDeviceActivity extends LiveloPontosActivity {

    public static final String REQUEST_CODE = "request_code";
    public static final String ACTIVATE_CODE = "activate_code";
    public static final String WELCOME = "welcome";


    @Bind(R.id.content_frag_activate) FrameLayout mContainerFrag;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.toolbar_title) TextView toolbarTitle;
    @Bind(R.id.loading_content) RelativeLayout loadingContent;


    private MyInfoBusiness myInfoBusiness;
    private boolean loadInfoFromCache = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_device);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        toolbarTitle.setText("Ativar Dispositivo");
        customizeToolbarWithButtonBack();
        myInfoBusiness = new MyInfoBusiness(getBaseContext(), getOnMyInfoBusinessListener());

        if (LiveloPontosApp.getInstance().getProfileResponse() == null) {
            loadInfoFromCache();
        }

        goTo(REQUEST_CODE);

    }

    public void goTo(String path) {
        switch (path) {
            case REQUEST_CODE:
                replaceFragment(RequestCodeFragment.newInstance(), "request_code_frag", false);
                break;

            case ACTIVATE_CODE:
                replaceFragment(ActivateCodeFragment.newInstance(true), "activate_code_frag", false);
                break;

            case WELCOME:
                //Intent intent = new Intent(ActivateDeviceActivity.this, HomeActivity.class);
                Intent intent = new Intent(ActivateDeviceActivity.this, RegisterConfirmation.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void loadInfoFromCache() {
        ProfileResponse profile = LiveloPontosApp.getInstance().loadInfoUserFromCache();

        if (profile == null) {
            loadInfoFromCache = false;
            callServiceUserProfile();
        } else {
            loadInfoFromCache = true;
            LiveloPontosApp.getInstance().setProfileResponse(profile);
            callServiceUserProfile();
        }
    }

    private void callServiceUserProfile() {
        loadingContent.setVisibility(View.VISIBLE);
        myInfoBusiness.callServiceGetUserProfile();
    }

    private HomeActivity.OnMyInfoBusinessListener getOnMyInfoBusinessListener() {
        HomeActivity.OnMyInfoBusinessListener onMyInfoBusinessListener = new HomeActivity.OnMyInfoBusinessListener() {
            @Override
            public void onMyInfoBusinessSuccess(ProfileResponse userProfileResponse) {
                loadingContent.setVisibility(View.GONE);
            }

            @Override
            public void onMyInfoBusinessFail(LiveloException exception) {
                getUserProfileFail(exception);
            }
        };

        return onMyInfoBusinessListener;
    }

    private void getUserProfileFail(final LiveloException exception) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingContent.setVisibility(View.GONE);
                if (exception.getErrorCode() == LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR) {
                    sessionExpired();
                } else {
                    showDialogNoConnection(exception.getAlertToShow(getBaseContext()));
                }
            }
        }, 500);
    }

    private void sessionExpired() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(ActivateDeviceActivity.this, LiveloPontosApp.getInstance().getExpiredSession(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            returnUserToLogin();
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
            dialogCustom.showCustomDialog(ActivateDeviceActivity.this, alert, false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            Log.d("DIALOG", "POSITIVE");
                            callServiceUserProfile();
                        }

                        @Override
                        public void onNegativeClick() {
                            Log.d("DIALOG", "NEGATIVE");
                        }

                        @Override
                        public void onBackPressedInDialog() {
                            LoginUtil.clearLogin(getBaseContext());
                            Intent intent = new Intent(ActivateDeviceActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        }
    }

    private void replaceFragment(Fragment fragment, String label, boolean toBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        transaction.replace(R.id.content_frag_activate, fragment, label);

        if (toBack) {
            transaction.addToBackStack(label);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            //super.onBackPressed();
            returnUserToLogin();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();

        if (item.getItemId() == android.R.id.home) {
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                //super.onBackPressed();
                returnUserToLogin();
            }
            return false;
        }
        return super.onOptionsItemSelected(item);
    }


    private void returnUserToLogin(){
        LoginUtil.clearLogin(getBaseContext());
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
