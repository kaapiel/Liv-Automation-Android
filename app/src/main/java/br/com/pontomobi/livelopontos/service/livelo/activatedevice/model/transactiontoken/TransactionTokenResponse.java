package br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.transactiontoken;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vilmar.filho on 1/25/16.
 */
public class TransactionTokenResponse {

    @SerializedName("ActivateMobileTokenResponse")
    private boolean ActivateMobileTokenResponse;

    public boolean isActivateMobileTokenResponse() {
        return ActivateMobileTokenResponse;
    }

    public void setActivateMobileTokenResponse(boolean activateMobileTokenResponse) {
        ActivateMobileTokenResponse = activateMobileTokenResponse;
    }
}
