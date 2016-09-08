package br.com.pontomobi.livelowear.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import com.squareup.otto.Subscribe;

import br.com.pontomobi.livelowear.Constants;
import br.com.pontomobi.livelowear.LiveloWearApp;
import br.com.pontomobi.livelowear.R;
import br.com.pontomobi.livelowear.service.SendMessageThread;
import br.com.pontomobi.livelowear.service.model.WearError;
import br.com.pontomobi.livelowear.service.model.WearLogin;
import br.com.pontomobi.livelowear.ui.error.GenericErrorActivity;
import br.com.pontomobi.livelowear.ui.home.HomeActivity;

/**
 * Created by vilmar.filho on 2/18/16.
 */
public class LoadingActivity extends LiveloWearBaseActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "LiveloWearLoading";

    private GoogleApiClient mGoogleApiClient;

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Log.i(TAG, "onCreate()");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


    }

    @Subscribe
    public void responseCheckLogin(WearLogin wearLogin) {
        Log.v(TAG, "Subscribe_Login");
        cancelHandler();
        if (!wearLogin.isLogged()) {
            Intent intent = new Intent(this, GenericErrorActivity.class);
            intent.putExtra(GenericErrorActivity.KEY_ERROR_LOGIN, true);
            startActivity(intent);
            LoadingActivity.this.finish();
        } else {
            startActivity(new Intent(this, HomeActivity.class));
            LoadingActivity.this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LiveloWearApp.getInstance().getBus().register(this);
        mGoogleApiClient.connect();
        closeActivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        LiveloWearApp.getInstance().getBus().unregister(this);
        super.onPause();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v(TAG, "Connected Success");
        new SendMessageThread(Constants.RequestSendPhone.REQUEST_CHECK_LOGIN, mGoogleApiClient).start();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v(TAG, "Connected Suspended");
        callActivityError();
        LoadingActivity.this.finish();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(TAG, "Connected Failed");
        callActivityError();
        LoadingActivity.this.finish();
    }

    private void callActivityError(){
        Intent intent = new Intent(this, GenericErrorActivity.class);
        intent.putExtra(GenericErrorActivity.KEY_ERROR_LOGIN, false);
        startActivity(intent);
        LoadingActivity.this.finish();
    }

    @Subscribe
    public void errorConnection(WearError wearError) {
        cancelHandler();
        callActivityError();
    }

    private void closeActivity() {
        mHandler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                callActivityError();
            }
        };

        mHandler.postDelayed(mRunnable, 10000);
    }

    private void cancelHandler(){
        if(mHandler != null) mHandler.removeCallbacks(mRunnable);
    }
}
