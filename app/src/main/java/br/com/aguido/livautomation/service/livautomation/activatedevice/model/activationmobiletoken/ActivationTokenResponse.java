package br.com.aguido.livautomation.service.livautomation.activatedevice.model.activationmobiletoken;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vilmar.filho on 1/26/16.
 */
public class ActivationTokenResponse {

    @SerializedName("ActivateMobileTokenResponse")
    private boolean activateMobileTokenResponse;

    public boolean isActivateMobileTokenResponse() {
        return activateMobileTokenResponse;
    }

    public void setActivateMobileTokenResponse(boolean activateMobileTokenResponse) {
        this.activateMobileTokenResponse = activateMobileTokenResponse;
    }
}
