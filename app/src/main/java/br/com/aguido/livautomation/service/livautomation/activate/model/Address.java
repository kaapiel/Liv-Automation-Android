
package br.com.aguido.livautomation.service.livautomation.activate.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable{

    private static final long serialVersionUID = 1L;

    @SerializedName("addressLine1")
    private String addressLine1;

    @SerializedName("addressLine2")
    private String addressLine2;

    @SerializedName("addressLine3")
    private String addressLine3;

    @SerializedName("cityName")
    private String cityName;

    @SerializedName("@class")
    private String Class;

    @SerializedName("countryCode")
    private String countryCode;

    @SerializedName("countryName")
    private String countryName;

    @SerializedName("districtName")
    private String districtName;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("id")
    private String id;

    @SerializedName("masterFlag")
    private boolean masterFlag;

    @SerializedName("postalCode")
    private String postalCode;

    @SerializedName("stateName")
    private String stateName;

    @SerializedName("type")
    private String type;

    /**
     * 
     * @return
     *     The addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * 
     * @param addressLine1
     *     The addressLine1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * 
     * @return
     *     The addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * 
     * @param addressLine2
     *     The addressLine2
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * 
     * @return
     *     The addressLine3
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * 
     * @param addressLine3
     *     The addressLine3
     */
    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    /**
     * 
     * @return
     *     The cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 
     * @param cityName
     *     The cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

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
     *     The countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * 
     * @param countryName
     *     The countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * 
     * @return
     *     The districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * 
     * @param districtName
     *     The districtName
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * 
     * @return
     *     The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * @param fullName
     *     The fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
     *     The postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * 
     * @param postalCode
     *     The postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * 
     * @return
     *     The stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * 
     * @param stateName
     *     The stateName
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
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
