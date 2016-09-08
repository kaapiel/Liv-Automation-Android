package br.com.pontomobi.livelopontos.service.wear;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.model.PointsPerMonth;
import br.com.pontomobi.livelopontos.model.Summary;
import br.com.pontomobi.livelopontos.service.wear.model.WearLogin;
import br.com.pontomobi.livelopontos.service.wear.model.WearPoints;
import br.com.pontomobi.livelopontos.service.wear.model.WearPointsPerMonth;
import br.com.pontomobi.livelopontos.service.wear.model.WearRescueCode;
import br.com.pontomobi.livelopontos.ui.splash.SplashActivity;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import br.com.pontomobi.livelopontos.util.RescueCodeUtil;

/**
 * Created by selem.gomes on 13/01/16.
 */
public class WearListenerService extends WearableListenerService implements WearInterfaceFunctions,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final static String TAG = "LiveloWear";
    private static final long CONNECTION_TIME_OUT_MS = 100;


    private String requestFromWear;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        requestFromWear = "";
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
        String id = peer.getId();
        String name = peer.getDisplayName();

        Log.v(TAG, id);
        Log.v(TAG, name);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        if (messageEvent.getPath().equals(Constants.WearParameters.REQUEST_PATH)) {
            requestFromWear = new String(messageEvent.getData());
            Log.v(TAG, requestFromWear);

            if (requestFromWear.equalsIgnoreCase(Constants.WearParameters.REQUEST_CHECK_LOGIN)) {
                checkLogin();
            } else if (requestFromWear.equalsIgnoreCase(Constants.WearParameters.REQUEST_GET_AMOUNT_POINTS)) {
                getAmountPoints();
            } else if (requestFromWear.equalsIgnoreCase(Constants.WearParameters.REQUEST_VALIDATION_CODE)) {
                getOrStartValidationCode();
            } else if (requestFromWear.equalsIgnoreCase(Constants.WearParameters.REQUEST_GO_TO_APP)) {
                openApp();
            } else if(requestFromWear.equalsIgnoreCase(Constants.WearParameters.REQUEST_DELETE_SHARED)){
                deleteShared();
            } else {
                messageError();
            }

        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    @Override
    public void checkLogin() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        boolean isLoged = LoginUtil.isUserLogged(getBaseContext());

        WearLogin wearLogin = new WearLogin(isLoged);
        String json = gson.toJson(wearLogin);
        sendResponseToWear(Constants.WearParameters.KEY_MAP_REQUEST_LOGIN, json);

        if(!isLoged)
            openApp();

    }

    @Override
    public void getAmountPoints() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String jsonSummary = SharedPreferencesHelper.read(getBaseContext(), Constants.SharedPrefsKeys.SHARED_PREF_SUMMARY_NAME, Constants.SharedPrefsKeys.KEY_SUMMARY, null);
        Summary summary = null;

        if (TextUtils.isEmpty(jsonSummary)) {
            return;
        }

        summary = gson.fromJson(jsonSummary, Summary.class);

        WearPoints wearPoints = new WearPoints(summary.getAmountPoints(), summary.getLastUpdateData(), summary.getAmountPointsAccumulated());

        List<WearPointsPerMonth> wearPointsPerMonthList = new ArrayList<WearPointsPerMonth>();
        if (summary.getPointsPerMonthList() != null) {
            for (PointsPerMonth pointsPerMonth : summary.getPointsPerMonthList()) {
                wearPointsPerMonthList.add(new WearPointsPerMonth(pointsPerMonth.getMonth(), pointsPerMonth.getYear(), pointsPerMonth.getPoints()));
            }
        }
        wearPoints.setWearPointsPerMonthList(wearPointsPerMonthList);
        String json = gson.toJson(wearPoints);
        sendResponseToWear(Constants.WearParameters.KEY_MAP_REQUEST_POINTS, json);
    }

    @Override
    public void getOrStartValidationCode() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String rescueCodeStr = SharedPreferencesHelper.read(getBaseContext(), Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.KEY_TIME, null);
        if (TextUtils.isEmpty(rescueCodeStr)) {
            generateAndSerdCode();
            return;
        }

        WearRescueCode wearRescueCode = gson.fromJson(rescueCodeStr, WearRescueCode.class);

        if(wearRescueCode.getRescueCode().equals("000000")){
            generateAndSerdCode();
            return;
        }

        long time = wearRescueCode.getTimeCurrent() - System.currentTimeMillis();
        if (time >= 30000) {
            generateAndSerdCode();
            return;
        }

        sendResponseToWear(Constants.WearParameters.KEY_MAP_REQUEST_VALIDATION_CODE, rescueCodeStr);

    }

    private void generateAndSerdCode() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String token = RescueCodeUtil.generateToken(getBaseContext(), LiveloPontosApp.getInstance().getCpf());
        Long currentTime = System.currentTimeMillis();
        WearRescueCode rescueCode = new WearRescueCode(currentTime, token);

        String rescueCodeStr = gson.toJson(rescueCode);
        SharedPreferencesHelper.write(getBaseContext(), Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.KEY_TIME, rescueCodeStr);
        sendResponseToWear(Constants.WearParameters.KEY_MAP_REQUEST_VALIDATION_CODE, rescueCodeStr);
    }

    private void deleteShared(){
        SharedPreferencesHelper.remove(getBaseContext(), Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.KEY_TIME);
    }

    @Override
    public void messageError() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
    }

    @Override
    public void openApp() {

        if(LiveloPontosActivity.isAppInForeground()) return;

        Intent intent = new Intent(getBaseContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void sendResponseToWear(String keyMapRequest, String jsonObject) {

        mGoogleApiClient = new GoogleApiClient.Builder(getBaseContext())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
        if (mGoogleApiClient.isConnected()) {
            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(Constants.WearParameters.RESPONSE_PATH);
            putDataMapRequest.getDataMap().putString(keyMapRequest, jsonObject);

            Calendar cal = Calendar.getInstance();
            putDataMapRequest.getDataMap().putLong("HORA", cal.getTimeInMillis());

            Log.v(TAG, keyMapRequest);
            Log.v(TAG, jsonObject);

                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataMapRequest.asPutDataRequest())
                        .await();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
