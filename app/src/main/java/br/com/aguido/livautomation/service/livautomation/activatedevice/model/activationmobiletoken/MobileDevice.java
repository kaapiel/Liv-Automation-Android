package br.com.aguido.livautomation.service.livautomation.activatedevice.model.activationmobiletoken;

import com.google.gson.annotations.SerializedName;

public class MobileDevice {

    @SerializedName("DeviceID")
    private String DeviceID;

    @SerializedName("DeviceModel")
    private String DeviceModel;

    @SerializedName("DeviceName")
    private String DeviceName;

    @SerializedName("DeviceNumber")
    private String DeviceNumber;

    @SerializedName("PushNotificationToken")
    private String PushNotificationToken;

    public MobileDevice(){}

    public MobileDevice(String deviceID, String deviceModel, String deviceName, String deviceNumber, String pushNotificationToken) {
        DeviceID = deviceID;
        DeviceModel = deviceModel;
        DeviceName = deviceName;
        DeviceNumber = deviceNumber;
        PushNotificationToken = pushNotificationToken;
    }

    /**
     * 
     * @return
     *     The DeviceID
     */
    public String getDeviceID() {
        return DeviceID;
    }

    /**
     * 
     * @param DeviceID
     *     The DeviceID
     */
    public void setDeviceID(String DeviceID) {
        this.DeviceID = DeviceID;
    }

    /**
     * 
     * @return
     *     The DeviceModel
     */
    public String getDeviceModel() {
        return DeviceModel;
    }

    /**
     * 
     * @param DeviceModel
     *     The DeviceModel
     */
    public void setDeviceModel(String DeviceModel) {
        this.DeviceModel = DeviceModel;
    }

    /**
     * 
     * @return
     *     The DeviceName
     */
    public String getDeviceName() {
        return DeviceName;
    }

    /**
     * 
     * @param DeviceName
     *     The DeviceName
     */
    public void setDeviceName(String DeviceName) {
        this.DeviceName = DeviceName;
    }

    /**
     * 
     * @return
     *     The DeviceNumber
     */
    public String getDeviceNumber() {
        return DeviceNumber;
    }

    /**
     * 
     * @param DeviceNumber
     *     The DeviceNumber
     */
    public void setDeviceNumber(String DeviceNumber) {
        this.DeviceNumber = DeviceNumber;
    }

    /**
     * 
     * @return
     *     The PushNotificationToken
     */
    public String getPushNotificationToken() {
        return PushNotificationToken;
    }

    /**
     * 
     * @param PushNotificationToken
     *     The PushNotificationToken
     */
    public void setPushNotificationToken(String PushNotificationToken) {
        this.PushNotificationToken = PushNotificationToken;
    }

}
