package br.com.pontomobi.livelopontos.ui.registerUser;

import android.content.Context;

import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.login.model.LoginRequest;
import br.com.pontomobi.livelopontos.service.livelo.login.model.LoginResponse;
import br.com.pontomobi.livelopontos.service.livelo.registration.model.RegisterUserRequest;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ProfileResponse;
import br.com.pontomobi.livelopontos.ui.activateDevice.ActivateDeviceBusiness;
import br.com.pontomobi.livelopontos.ui.home.HomeActivity;
import br.com.pontomobi.livelopontos.ui.login.LoginBusiness;
import br.com.pontomobi.livelopontos.ui.myInfo.MyInfoBusiness;
import br.com.pontomobi.livelopontos.util.LoginUtil;

/**
 * Created by vilmar.filho on 1/22/16.
 */
public class RegisterUserBusiness {

    private Context mContext;
    private OnServiceRegisterListener mOnServiceRegisterListener;

    private MyInfoBusiness myInfoBusiness;
    private ActivateDeviceBusiness mActivateDeviceBusiness;

    private RegisterUserRequest mRegisterUserRequest;

    private String mLogin;

    public RegisterUserBusiness(Context mContext, OnServiceRegisterListener onServiceRegisterListener) {
        this.mContext = mContext;
        this.mOnServiceRegisterListener = onServiceRegisterListener;

        myInfoBusiness = new MyInfoBusiness(mContext, getOnMyInfoBusinessListener());
        mActivateDeviceBusiness = new ActivateDeviceBusiness(mContext, getOnActiveDevice());
    }


    public void registerUser(RegisterUserRequest registerUserRequest) {
        mRegisterUserRequest = registerUserRequest;

        LiveloRepository.with(mContext).registerUser(mContext, registerUserRequest, getOnServiceRegisterListener());
    }


    private void callServiceLogin(String login, String password) {
        mLogin = login;
        LoginRequest request = new LoginRequest(login, password);
        LiveloRepository.with(mContext).tryLogin(mContext, request, getOnServiceLoginListener());
    }


    private OnServiceRegisterListener getOnServiceRegisterListener() {
        return new OnServiceRegisterListener() {
            @Override
            public void onRegisterSuccess(String renewToken) {
                callServiceLogin(mRegisterUserRequest.getCpf(), mRegisterUserRequest.getPassword());
            }

            @Override
            public void onRegisterFail(LiveloException exception, String renewToken) {
                mOnServiceRegisterListener.onRegisterFail(exception, renewToken);
            }
        };
    }


    private LoginBusiness.OnServiceLoginListener getOnServiceLoginListener() {
        return new LoginBusiness.OnServiceLoginListener() {
            @Override
            public void onLoginSuccess(LoginResponse response) {
                LiveloPontosApp.getInstance().setLogin(response);
                LoginUtil.saveInfoAboutLogin(mContext, response);
                myInfoBusiness.callServiceGetUserProfile();
            }

            @Override
            public void onLoginFail(LiveloException exception) {
                mOnServiceRegisterListener.onRegisterFail(exception, null);
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
                mOnServiceRegisterListener.onRegisterFail(exception, null);
            }
        };

        return onMyInfoBusinessListener;
    }

    /*private ActivateDeviceBusiness.OnRetrieveDevice getOnRetrieveDevice(){
        return new ActivateDeviceBusiness.OnRetrieveDevice() {
            @Override
            public void onRetrieveSuccess(RetrieveDeviceResponse response) {
                mOnServiceRegisterListener.onRegisterSuccess();
            }

            @Override
            public void onRetrieveFail(LiveloException exception) {
                mOnServiceRegisterListener.onRegisterFail(exception);
            }
        };
    }*/

    private ActivateDeviceBusiness.OnActiveDevice getOnActiveDevice() {
        return new ActivateDeviceBusiness.OnActiveDevice() {
            @Override
            public void onActiveSuccess(boolean hasActiveDevice) {
                mOnServiceRegisterListener.onRegisterSuccess(null);
            }

            @Override
            public void onActiveFail(LiveloException exception) {
                mOnServiceRegisterListener.onRegisterFail(exception, null);
            }
        };
    }


    public interface OnServiceRegisterListener {
        void onRegisterSuccess(String renewToken);

        void onRegisterFail(LiveloException exception, String renewToken);
    }
}
