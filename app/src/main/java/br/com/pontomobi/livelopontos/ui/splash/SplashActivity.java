package br.com.pontomobi.livelopontos.ui.splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.R;

/**
 * Created by selem.gomes on 31/08/15.
 */
public class SplashActivity extends LiveloPontosActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupCrashlytics();
        printHashCodeLivelo();
    }

    private void printHashCodeLivelo() {
        PackageInfo packageInfo;

        try {
            packageInfo = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                Log.i("signature", "The signature is : " + signature.toCharsString());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setupCrashlytics(){
        //Crashlytics.setUserIdentifier(getString(R.string.build_user));

//        Crashlytics.setString(BUILD_USER, getString(R.string.build_user));
//        Crashlytics.setString(BUILD_DATE, getString(R.string.build_date));
//        Crashlytics.setString(LAST_COMMIT, getString(R.string.last_commit));
//        Crashlytics.setString(BRANCH, getString(R.string.branch));

        /** Causing a Test Crash */
        //throw new RuntimeException("This is a crash");
    }
}
