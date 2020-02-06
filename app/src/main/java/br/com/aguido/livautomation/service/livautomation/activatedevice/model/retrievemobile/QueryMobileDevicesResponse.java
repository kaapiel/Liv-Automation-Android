package br.com.aguido.livautomation.service.livautomation.activatedevice.model.retrievemobile;

import com.google.gson.annotations.SerializedName;

public class QueryMobileDevicesResponse {

    @SerializedName("MobileDevicesList")
    private MobileDevicesList MobileDevicesList;

    /**
     * 
     * @return
     *     The MobileDevicesList
     */
    public MobileDevicesList getMobileDevicesList() {
        return MobileDevicesList;
    }

    /**
     * 
     * @param MobileDevicesList
     *     The MobileDevicesList
     */
    public void setMobileDevicesList(MobileDevicesList MobileDevicesList) {
        this.MobileDevicesList = MobileDevicesList;
    }

}
