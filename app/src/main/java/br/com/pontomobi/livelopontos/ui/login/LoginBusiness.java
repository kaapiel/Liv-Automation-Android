package br.com.pontomobi.livelopontos.ui.login;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyProperties;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.login.model.LoginRequest;
import br.com.pontomobi.livelopontos.service.livelo.login.model.LoginResponse;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ProfileResponse;
import br.com.pontomobi.livelopontos.ui.home.HomeActivity.OnMyInfoBusinessListener;
import br.com.pontomobi.livelopontos.ui.myInfo.MyInfoBusiness;
import br.com.pontomobi.livelopontos.util.LoginUtil;

/**
 * Created by selem.gomes on 20/11/15.
 */
public class LoginBusiness {

    public static final String HOME = "home";
    public static final String ACTIVATE_DEVICE = "activate_device";


    private LoginActivity.OnLoginBusinessListener onLoginBusinessListener;
    private Context context;
    private MyInfoBusiness myInfoBusiness;

    private String mLogin;

    public LoginBusiness(LoginActivity.OnLoginBusinessListener onLoginBusinessListener, Context context) {
        this.onLoginBusinessListener = onLoginBusinessListener;
        this.context = context;
    }

    public void callServiceLogin(String login, String password) {
        mLogin = login;
        LoginRequest request = new LoginRequest(login, password);
        LiveloRepository.with(context).tryLogin(context, request, getOnServiceLoginListener());
    }

    private OnServiceLoginListener getOnServiceLoginListener() {
        OnServiceLoginListener onServiceLoginListener = new OnServiceLoginListener() {
            @Override
            public void onLoginSuccess(LoginResponse responde) {
                LiveloPontosApp.getInstance().setLogin(responde);
                LoginUtil.saveInfoAboutLogin(context, responde);
                myInfoBusiness.callServiceGetUserProfile();
            }

            @Override
            public void onLoginFail(LiveloException exception) {
                onLoginBusinessListener.onLoginFail(exception);
            }
        };

        return onServiceLoginListener;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public FingerprintManager getFingerprintManager() {
        return context.getSystemService(FingerprintManager.class);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public KeyguardManager getKeyguardManager() {
        return context.getSystemService(KeyguardManager.class);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public KeyStore getKeystore() {
        try {
            return KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException("Failed to get an instance of KeyStore", e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public KeyGenerator getKeyGenerator() {
        try {
            return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public Cipher getCipher(KeyStore keyStore) {
        try {
            return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get an instance of Cipher", e);
        }
    }


    public interface OnServiceLoginListener {
        void onLoginSuccess(LoginResponse response);

        void onLoginFail(LiveloException exception);
    }
}
