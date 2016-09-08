package br.com.pontomobi.livelopontos.ui.registerUser;

import android.content.Context;

import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.retrievemobile.RetrieveDeviceResponse;
import br.com.pontomobi.livelopontos.service.livelo.login.model.LoginRequest;
import br.com.pontomobi.livelopontos.service.livelo.login.model.LoginResponse;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ProfileResponse;
import br.com.pontomobi.livelopontos.ui.activateDevice.ActivateDeviceBusiness;
import br.com.pontomobi.livelopontos.ui.home.HomeActivity;
import br.com.pontomobi.livelopontos.ui.login.LoginBusiness;
import br.com.pontomobi.livelopontos.ui.myInfo.MyInfoBusiness;
import br.com.pontomobi.livelopontos.util.LoginUtil;

/**
 * Created by vilmar.filho on 2/4/16.
 */
public class RegisterConfirmationBusiness {

    private Context mContext;

    private MyInfoBusiness myInfoBusiness;

    //private LoginBusiness.OnServiceLoginListener mOnServiceLoginListener;
    private OnRegisterConfirmationListener mOnRegisterConfirmationListener;
    private ActivateDeviceBusiness mActivateDeviceBusiness;

    private String mLogin;

    public RegisterConfirmationBusiness(Context context, OnRegisterConfirmationListener onRegisterConfirmationListener) {
        this.mContext = context;
        this.mOnRegisterConfirmationListener = onRegisterConfirmationListener;

        myInfoBusiness = new MyInfoBusiness(context, getOnMyInfoBusinessListener());
        mActivateDeviceBusiness = new ActivateDeviceBusiness(context, getOnActiveDevice());
    }

    public void callServiceLogin(String login, String password) {
        mLogin = login;
        LoginRequest request = new LoginRequest(login, password);
        LiveloRepository.with(mContext).tryLogin(mContext, request, getOnServiceLoginListener());
    }

    private LoginBusiness.OnServiceLoginListener getOnServiceLoginListener(){
        return new LoginBusiness.OnServiceLoginListener() {
            @Override
            public void onLoginSuccess(LoginResponse response) {
                LiveloPontosApp.getInstance().setLogin(response);
                LoginUtil.saveInfoAboutLogin(mContext, response);
                myInfoBusiness.callServiceGetUserProfile();
            }

            @Override
            public void onLoginFail(LiveloException exception) {
                mOnRegisterConfirmationListener.onFail(exception);
            }
        };
    }

    private HomeActivity.OnMyInfoBusinessListener getOnMyInfoBusinessListener() {
        HomeActivity.OnMyInfoBusinessListener onMyInfoBusinessListener = new HomeActivity.OnMyInfoBusinessListener() {
            @Override
            public void onMyInfoBusinessSuccess(ProfileResponse userProfileResponse) {
                mActivateDeviceBusiness.retrieveDevices(mLogin);
            }

            @Override
            public void onMyInfoBusinessFail(LiveloException exception) {
                mOnRegisterConfirmationListener.onFail(exception);
            }
        };

        return onMyInfoBusinessListener;
    }

    /*private ActivateDeviceBusiness.OnRetrieveDevice getOnRetrieveDevice(){
        return new ActivateDeviceBusiness.OnRetrieveDevice() {
            @Override
            public void onRetrieveSuccess(RetrieveDeviceResponse response) {

                //if(response != null && response.getMobileDevicesList() != null && response.getMobileDevicesList().getMobileDevice().size() > 0) {
                //} else {
                    mOnRegisterConfirmationListener.onSuccess();
                //}
            }

            @Override
            public void onRetrieveFail(LiveloException exception) {
                mOnRegisterConfirmationListener.onFail(exception);
            }
        };
    }*/


    private ActivateDeviceBusiness.OnActiveDevice getOnActiveDevice(){
        return new ActivateDeviceBusiness.OnActiveDevice() {
            @Override
            public void onActiveSuccess(boolean hasActiveDevice) {
                mOnRegisterConfirmationListener.onSuccess();
            }

            @Override
            public void onActiveFail(LiveloException exception) {
                mOnRegisterConfirmationListener.onFail(exception);
            }
        };
    }

    public interface OnRegisterConfirmationListener {
        void onSuccess();

        void onFail(LiveloException exception);
    }

}
