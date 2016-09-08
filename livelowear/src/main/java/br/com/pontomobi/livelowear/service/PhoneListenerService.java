package br.com.pontomobi.livelowear.service;

import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.pontomobi.livelowear.Constants;
import br.com.pontomobi.livelowear.LiveloWearApp;
import br.com.pontomobi.livelowear.service.model.WearError;
import br.com.pontomobi.livelowear.service.model.WearLogin;
import br.com.pontomobi.livelowear.service.model.WearPoints;
import br.com.pontomobi.livelowear.service.model.WearRescueCode;

/**
 * Created by selem.gomes on 13/01/16.
 */
public class PhoneListenerService extends WearableListenerService {

    private static final String TAG = "LiveloWearReceive";

    @Override
    public void onCreate() {
        super.onCreate();
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
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.v(TAG, "data changed");

        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {

                Log.v(TAG, "event.type");
                String login = DataMap.fromByteArray(event.getDataItem().getData()).get(Constants.RequestSendPhone.KEY_MAP_REQUEST_LOGIN);
                String error = DataMap.fromByteArray(event.getDataItem().getData()).get(Constants.RequestSendPhone.KEY_MAP_REQUEST_ERROR);
                String myPoints = DataMap.fromByteArray(event.getDataItem().getData()).get(Constants.RequestSendPhone.KEY_MAP_REQUEST_POINTS);
                String validationCode = DataMap.fromByteArray(event.getDataItem().getData()).get(Constants.RequestSendPhone.KEY_MAP_REQUEST_VALIDATION_CODE);

                checkReturnError(error);
                checkReturnLogin(login);
                checkReturnMyPoints(myPoints);
                checkReturnValidationCode(validationCode);
            }
        }
        dataEvents.close();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v(TAG, "RECEIVE");
        super.onMessageReceived(messageEvent);
    }

    private void checkReturnLogin(String jsonLogin) {
        if (TextUtils.isEmpty(jsonLogin)) {
            return;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        WearLogin wearLogin = gson.fromJson(jsonLogin, WearLogin.class);

        LiveloWearApp.getInstance().getBus().post(wearLogin);
    }

    private void checkReturnError(String jsonError) {
        if (TextUtils.isEmpty(jsonError)) {
            return;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        WearError wearError = gson.fromJson(jsonError, WearError.class);

        LiveloWearApp.getInstance().getBus().post(wearError);
    }

    private void checkReturnMyPoints(String jsonPoints) {
        if (TextUtils.isEmpty(jsonPoints)) {
            return;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        WearPoints wearPoints = gson.fromJson(jsonPoints, WearPoints.class);

        LiveloWearApp.getInstance().getBus().post(wearPoints);
    }

    private void checkReturnValidationCode(String jsonValidationCode) {
        if (TextUtils.isEmpty(jsonValidationCode)) {
            return;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        WearRescueCode wearRescueCode = gson.fromJson(jsonValidationCode, WearRescueCode.class);

        Log.v(TAG, jsonValidationCode);

        LiveloWearApp.getInstance().getBus().post(wearRescueCode);
    }

}
