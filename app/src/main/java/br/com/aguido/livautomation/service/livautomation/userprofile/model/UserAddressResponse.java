package br.com.aguido.livautomation.service.livautomation.userprofile.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by selem.gomes on 13/11/15.
 */
public class UserAddressResponse {

    @SerializedName("middleName")
    private String middleName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("item-id")
    @Expose
    private String itemId;

    @SerializedName("state")
    private String state;

    @SerializedName("address1")
    private String address1;

    @SerializedName("address2")
    private String address2;

    @SerializedName("address3")
    private String address3;

    @SerializedName("companyName")
    private String companyName;

    @SerializedName("suffix")
    private String suffix;

    @SerializedName("repositoryId")
    private String repositoryId;

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("postalCode")
    private String postalCode;

    @SerializedName("faxNumber")
    private String faxNumber;
    @SerializedName("phoneNumber")

    private String phoneNumber;

    @SerializedName("county")
    private String county;

    @SerializedName("prefix")
    private String prefix;

    @SerializedName("firstName")
    private String firstName;

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String formatAddressToShow() {
        return (getInfoToShow(address1, ", ") +
                getInfoToShow(address2, ", ") +
                getInfoToShow(address3, ", ") +
                getInfoToShow(country, " - ") +
                getInfoToShow(city, " / ") +
                getInfoToShow(state, " ") +
                getInfoToShow(postalCode, ""));
    }

    private String getInfoToShow(String txt, String sep) {
        return TextUtils.isEmpty(txt) ? "" : (txt + sep);
    }


}
