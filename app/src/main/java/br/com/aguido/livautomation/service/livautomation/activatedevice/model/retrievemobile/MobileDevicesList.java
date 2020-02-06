package br.com.aguido.livautomation.service.livautomation.activatedevice.model.retrievemobile;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MobileDevicesList {

    @SerializedName("MobileDevice")
    private List<MobileDevice> MobileDevice = new ArrayList<MobileDevice>();

    /**
     * 
     * @return
     *     The MobileDevice
     */
    public List<MobileDevice> getMobileDevice() {
        return MobileDevice;
    }

    /**
     * 
     * @param MobileDevice
     *     The MobileDevice
     */
    public void setMobileDevice(List<MobileDevice> MobileDevice) {
        this.MobileDevice = MobileDevice;
    }

}
