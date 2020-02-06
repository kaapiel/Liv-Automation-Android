package br.com.aguido.livautomation.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;

import br.com.aguido.livautomation.Constants;
import br.com.aguido.livautomation.LivAutomationApp;
import br.com.aguido.livautomation.helper.SharedPreferencesHelper;
import br.com.aguido.livautomation.service.livautomation.login.model.LoginResponse;

/**
 * Created by selem.gomes on 29/10/15.
 */
public class LoginUtil {

    public static String generateAutorizationBase64() {
        String stringAccess = Constants.AccessConfiguration.CLIENT_ID + ":" + Constants.AccessConfiguration.SECRET;
        byte[] byteArray = stringAccess.getBytes();

        String autorization = "Basic " + Base64.encodeToString(byteArray, Base64.DEFAULT);

        return autorization.trim();
    }

    public static void saveInfoAboutLogin(Context context, LoginResponse response) {
        Gson gson = new Gson();
        String json = gson.toJson(response);
        SharedPreferencesHelper.write(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.LOGIN, json);
    }

    public static LoginResponse loadInfoAboutLogin(Context context) {
        String jsonLogin = SharedPreferencesHelper.read(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.LOGIN, null);
        LoginResponse response = null;
        if (!TextUtils.isEmpty(jsonLogin)) {

            Gson gson = new Gson();

            response = gson.fromJson(jsonLogin, LoginResponse.class);
            LivAutomationApp.getInstance().setLogin(response);
        }

        return response;
    }

    public static boolean isUserLogged(Context context){
        return SharedPreferencesHelper.read(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.DEVICE_ACTIVE, false);
    }

    public static void clearLogin(Context context) {
        SharedPreferencesHelper.remove(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.LOGIN);
        SharedPreferencesHelper.remove(context, Constants.SharedPrefsKeys.SHARED_PREF_SUMMARY_NAME, Constants.SharedPrefsKeys.KEY_SUMMARY);
        SharedPreferencesHelper.remove(context, Constants.SharedPrefsKeys.SHARED_PREF_EXTRACT_NAME, Constants.SharedPrefsKeys.KEY_EXTRACT);
        SharedPreferencesHelper.remove(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.DEVICE_ACTIVE);
        SharedPreferencesHelper.remove(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.BRANDING_BANK);
        LivAutomationApp.getInstance().setProfileResponse(null);
        LivAutomationApp.getInstance().setBrandingBank(null);
        LivAutomationApp.getInstance().setServiceCallSummaryOk(false);
        LivAutomationApp.getInstance().setServiceCallExtractOk(false);
    }
}
