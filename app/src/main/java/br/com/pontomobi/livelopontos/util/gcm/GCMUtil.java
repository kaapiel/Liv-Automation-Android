package br.com.pontomobi.livelopontos.util.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.Random;

import br.com.pontomobi.livelopontos.ui.home.HomeActivity;

/**
 * Created by vilmar.filho on 1/26/16.
 */
public class GCMUtil {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final String TAG = "GCMUtil";
    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    public static boolean checkPlayServices(AppCompatActivity activity){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);

        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST);
            }
            else {
                Toast.makeText(activity, "PlayServices sem suporte", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }


    public static int getAppVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return (pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            Log.i(TAG, "Package name not found");
        }
        return (0);
    }


    public static int randInt() {
        Random rand = new Random();
        int randomNum = rand.nextInt((50000 - 0) + 1) + 0;
        return randomNum;
    }


    // SHARED PREFERENCES
    public static String getRegistrationId(Context context) {
        try {
            SharedPreferences prefs = getGCMPreferences(context);
            String registrationId = prefs.getString(PROPERTY_REG_ID, "");

            if (registrationId.trim().length() == 0) {
                Log.i(TAG, "Registration not found.");
                return ("");
            }

            int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
            int currentVersion = GCMUtil.getAppVersion(context);

            if (registeredVersion != currentVersion) {
                Log.i(TAG, "App Version has changed");
                return ("");
            }

            return (registrationId);

        } catch (Exception e) {
            Log.i("GCMUtil", "getRegistrationId: " + e.getMessage());
        }
        return "";
    }


    public static void storeRegistrationId(Context context, String regId) {
        try {
            SharedPreferences prefs = getGCMPreferences(context);
            int appVersion = GCMUtil.getAppVersion(context);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PROPERTY_REG_ID, regId);
            editor.putInt(PROPERTY_APP_VERSION, appVersion);
            editor.commit();
        } catch (Exception e){
            Log.i("GCMUtil", "storeRegistrationId: " + e.getMessage());
        }
    }


    public static SharedPreferences getGCMPreferences(Context context) {
        return (context.getSharedPreferences(HomeActivity.class.getSimpleName(), Context.MODE_PRIVATE));
    }

}
