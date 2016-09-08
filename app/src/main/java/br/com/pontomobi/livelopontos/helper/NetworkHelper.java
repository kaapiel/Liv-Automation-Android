package br.com.pontomobi.livelopontos.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.Serializable;

/**
 * Created by selem.gomes on 03/06/15.
 */
public class NetworkHelper implements Serializable {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public interface OnTryAgainListener {
        public void onItemClick();
    }
}
