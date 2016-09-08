package br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.retrievemobile;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

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
