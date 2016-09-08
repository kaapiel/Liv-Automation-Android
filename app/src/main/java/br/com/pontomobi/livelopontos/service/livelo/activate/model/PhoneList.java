
package br.com.pontomobi.livelopontos.service.livelo.activate.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhoneList implements Serializable{

    private static final long serialVersionUID = 1L;

    @SerializedName("@class")
    private String Class;

    @SerializedName("countryCode")
    private String countryCode;

    @SerializedName("id")
    private String id;

    @SerializedName("masterFlag")
    private boolean masterFlag;

    @SerializedName("partnerCode")
    private String partnerCode;

    @SerializedName("phoneAreaCode")
    private String phoneAreaCode;

    @SerializedName("phoneNo")
    private String phoneNo;

    @SerializedName("type")
    private String type;

    /**
     * 
     * @return
     *     The Class
     */
    public String getClass_() {
        return Class;
    }

    /**
     * 
     * @param Class
     *     The @class
     */
    public void setClass_(String Class) {
        this.Class = Class;
    }

    /**
     * 
     * @return
     *     The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 
     * @param countryCode
     *     The countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The masterFlag
     */
    public boolean isMasterFlag() {
        return masterFlag;
    }

    /**
     * 
     * @param masterFlag
     *     The masterFlag
     */
    public void setMasterFlag(boolean masterFlag) {
        this.masterFlag = masterFlag;
    }

    /**
     * 
     * @return
     *     The partnerCode
     */
    public String getPartnerCode() {
        return partnerCode;
    }

    /**
     * 
     * @param partnerCode
     *     The partnerCode
     */
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    /**
     * 
     * @return
     *     The phoneAreaCode
     */
    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    /**
     * 
     * @param phoneAreaCode
     *     The phoneAreaCode
     */
    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }

    /**
     * 
     * @return
     *     The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * 
     * @param phoneNo
     *     The phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
