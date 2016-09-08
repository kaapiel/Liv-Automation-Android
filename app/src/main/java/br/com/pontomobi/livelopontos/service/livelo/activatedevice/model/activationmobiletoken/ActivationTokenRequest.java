package br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.activationmobiletoken;

import com.google.gson.annotations.SerializedName;

public class ActivationTokenRequest {

    @SerializedName("Token")
    private String Token;

    @SerializedName("MobileDevice")
    private MobileDevice MobileDevice;

    public ActivationTokenRequest(String token, MobileDevice mobileDevice) {
        Token = token;
        MobileDevice = mobileDevice;
    }

    /**
     * 
     * @return
     *     The Token
     */
    public String getToken() {
        return Token;
    }

    /**
     * 
     * @param Token
     *     The Token
     */
    public void setToken(String Token) {
        this.Token = Token;
    }

    /**
     * 
     * @return
     *     The MobileDevice
     */
    public MobileDevice getMobileDevice() {
        return MobileDevice;
    }

    /**
     * 
     * @param MobileDevice
     *     The MobileDevice
     */
    public void setMobileDevice(MobileDevice MobileDevice) {
        this.MobileDevice = MobileDevice;
    }

}
