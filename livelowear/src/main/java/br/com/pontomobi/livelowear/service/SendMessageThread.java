package br.com.pontomobi.livelowear.service;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

import br.com.pontomobi.livelowear.Constants;
import br.com.pontomobi.livelowear.LiveloWearApp;
import br.com.pontomobi.livelowear.service.model.WearError;

/**
 * Created by selem.gomes on 13/01/16.
 */
public class SendMessageThread extends Thread {

    private final static String TAG = "LiveloThread";
    public String message;
    public GoogleApiClient googleClient;

    public SendMessageThread(String msg, GoogleApiClient googleClient) {
        this.message = msg;
        this.googleClient = googleClient;
    }

    public void run() {
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
        for (Node node : nodes.getNodes()) {
            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleClient, node.getId(), Constants.RequestSendPhone.REQUEST_PATH, message.getBytes()).await();
            if (result.getStatus().isSuccess()) {
                Log.v(TAG, "Message: {" + message + "} sent to: " + node.getDisplayName());
            } else {
                // Log an error
                Log.v(TAG, "ERROR: failed to send Message");
            }
        }

        List<Node> listNodes = nodes.getNodes();
        if (listNodes.size() > 0) {
            Log.v(TAG, "wearables found");
            if(!listNodes.get(0).isNearby()){
                LiveloWearApp.getInstance().getBus().post(new WearError());
            }

        } else {
            Log.v(TAG, "no wearables found");
            LiveloWearApp.getInstance().getBus().post(new WearError());
        }
    }
}
