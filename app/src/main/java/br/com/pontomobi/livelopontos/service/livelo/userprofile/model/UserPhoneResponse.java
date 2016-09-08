package br.com.pontomobi.livelopontos.service.livelo.userprofile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by selem.gomes on 13/11/15.
 */
public class UserPhoneResponse {

    @SerializedName("phoneNo")
    private String phoneNo;
    @SerializedName("phoneAreaCode")
    private String phoneAreaCode;
    @SerializedName("partnerCode")
    private String partnerCode;
    @SerializedName("masterFlag")
    private Boolean masterFlag;
    @SerializedName("ownerId")
    private String ownerId;
    @SerializedName("id")
    private String id;
    @SerializedName("phoneCountryCode")
    private String phoneCountryCode;
    @SerializedName("phoneType")
    private String phoneType;
    @SerializedName("item-id")
    private String itemId;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Boolean getMasterFlag() {
        return masterFlag;
    }

    public void setMasterFlag(Boolean masterFlag) {
        this.masterFlag = masterFlag;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
