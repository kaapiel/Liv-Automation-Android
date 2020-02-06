
package br.com.aguido.livautomation.service.livautomation.registration.model;

import com.google.gson.annotations.SerializedName;

public class RegisterUserRequest {

    private transient String jSessionId;

    @SerializedName("authenticationToken")
    private String authenticationToken;

    @SerializedName("cpf")
    private String cpf;

    @SerializedName("login")
    private String login;

    @SerializedName("email")
    private String email;

    @SerializedName("confirmEmail")
    private String confirmEmail;

    @SerializedName("gender")
    private String gender;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("nickName")
    private String nickName;

    @SerializedName("addressId")
    private String addressId;

    @SerializedName("pushPermission")
    private Boolean pushPermission;

    @SerializedName("receivePromoEmail")
    private Boolean receivePromoEmail;

    @SerializedName("receivePromoSMS")
    private Boolean receivePromoSMS;

    @SerializedName("password")
    private String password;

    @SerializedName("confirmPassword")
    private String confirmPassword;

    @SerializedName("phoneCountryCode")
    private String phoneCountryCode;

    @SerializedName("phoneAreaCode")
    private String phoneAreaCode;

    @SerializedName("phoneNo")
    private String phoneNo;

    @SerializedName("phoneType")
    private String phoneType;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;


    public RegisterUserRequest(String jSessionId, String authenticationToken, String cpf, String login, String email, String confirmEmail, String gender, String fullName, String nickName, String addressId, Boolean pushPermission, Boolean receivePromoEmail, Boolean receivePromoSMS, String password, String confirmPassword, String phoneCountryCode, String phoneAreaCode, String phoneNo, String phoneType, String dateOfBirth) {
        this.jSessionId = jSessionId;
        this.authenticationToken = authenticationToken;
        this.cpf = cpf;
        this.login = login;
        this.email = email;
        this.confirmEmail = confirmEmail;
        this.gender = gender;
        this.fullName = fullName;
        this.nickName = nickName;
        this.addressId = addressId;
        this.pushPermission = pushPermission;
        this.receivePromoEmail = receivePromoEmail;
        this.receivePromoSMS = receivePromoSMS;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneCountryCode = phoneCountryCode;
        this.phoneAreaCode = phoneAreaCode;
        this.phoneNo = phoneNo;
        this.phoneType = phoneType;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @return
     * The jSessionId
     */
    public String getjSessionId() {
        return jSessionId;
    }

    /**
     *
     * @param jSessionId
     * The jSessionId
     */
    public void setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
    }

    /**
     *
     * @return
     * The authenticationToken
     */
    public String getAuthenticationToken() {
        return authenticationToken;
    }

    /**
     *
     * @param authenticationToken
     * The authenticationToken
     */
    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    /**
     *
     * @return
     * The cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     *
     * @param cpf
     * The cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     *
     * @return
     * The login
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     * The login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The confirmEmail
     */
    public String getConfirmEmail() {
        return confirmEmail;
    }

    /**
     *
     * @param confirmEmail
     * The confirmEmail
     */
    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     * The nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     *
     * @param nickName
     * The nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     *
     * @return
     * The addressId
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     *
     * @param addressId
     * The addressId
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     *
     * @return
     * The pushPermission
     */
    public Boolean getPushPermission() {
        return pushPermission;
    }

    /**
     *
     * @param pushPermission
     * The pushPermission
     */
    public void setPushPermission(Boolean pushPermission) {
        this.pushPermission = pushPermission;
    }

    /**
     *
     * @return
     * The receivePromoEmail
     */
    public Boolean getReceivePromoEmail() {
        return receivePromoEmail;
    }

    /**
     *
     * @param receivePromoEmail
     * The receivePromoEmail
     */
    public void setReceivePromoEmail(Boolean receivePromoEmail) {
        this.receivePromoEmail = receivePromoEmail;
    }

    /**
     *
     * @return
     * The receivePromoSMS
     */
    public Boolean getReceivePromoSMS() {
        return receivePromoSMS;
    }

    /**
     *
     * @param receivePromoSMS
     * The receivePromoSMS
     */
    public void setReceivePromoSMS(Boolean receivePromoSMS) {
        this.receivePromoSMS = receivePromoSMS;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     *
     * @param confirmPassword
     * The confirmPassword
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     *
     * @return
     * The phoneCountryCode
     */
    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    /**
     *
     * @param phoneCountryCode
     * The phoneCountryCode
     */
    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    /**
     *
     * @return
     * The phoneAreaCode
     */
    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    /**
     *
     * @param phoneAreaCode
     * The phoneAreaCode
     */
    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }

    /**
     *
     * @return
     * The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     *
     * @param phoneNo
     * The phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     *
     * @return
     * The phoneType
     */
    public String getPhoneType() {
        return phoneType;
    }

    /**
     *
     * @param phoneType
     * The phoneType
     */
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
