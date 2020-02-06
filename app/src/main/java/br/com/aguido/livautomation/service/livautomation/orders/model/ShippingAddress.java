
package br.com.aguido.livautomation.service.livautomation.orders.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShippingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("state")
    private String state;

    @SerializedName("address1")
    private String address1;

    @SerializedName("address2")
    private String address2;

    @SerializedName("country")
    private String country;

    @SerializedName("city")
    private String city;

    @SerializedName("postalCode")
    private String postalCode;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("email")
    private String email;

    @SerializedName("firstName")
    private String firstName;

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return The address1
     */
    public String getAllAddress() {

        return (TextUtils.isEmpty(address1) ? "" : address1 + ", ") +
                (TextUtils.isEmpty(address2) ? "" : address2 + ", ") +
                (TextUtils.isEmpty(city) ? "" : city + ", ") +
                (TextUtils.isEmpty(state) ? "" : state + ", ") +
                (TextUtils.isEmpty(postalCode) ? "" : postalCode + ", ");
    }

    /**
     * @return The address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 The address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return The address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 The address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode The postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return The phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber The phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
