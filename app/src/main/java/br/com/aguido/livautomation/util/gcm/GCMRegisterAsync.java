package br.com.aguido.livautomation.util.gcm;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by vilmar.filho on 1/27/16.
 */
public class GCMRegisterAsync extends AsyncTask<Void, Void, String> {

    private GoogleCloudMessaging mGcm;
    private OnGCMRegisterCompleted mOnGCMRegisterCompleted;
    private String mSenderId;

    public GCMRegisterAsync(GoogleCloudMessaging gcm, String serderId, OnGCMRegisterCompleted onGCMRegisterCompleted){
        mGcm = gcm;
        mOnGCMRegisterCompleted = onGCMRegisterCompleted;
        mSenderId = serderId;
    }


    @Override
    protected String doInBackground(Void... voids) {

        try {
            if(mGcm != null)
                return mGcm.register(mSenderId);

        } catch (IOException e) {
            Log.e("GCMRegisterAsync", "Error registerDevicePush: " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String id) {
        super.onPostExecute(id);
        if(mOnGCMRegisterCompleted != null) mOnGCMRegisterCompleted.onRegisterCompleted(id);
    }

    public interface OnGCMRegisterCompleted{
        void onRegisterCompleted(String regId);
    }
}
