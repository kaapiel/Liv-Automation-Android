
package br.com.pontomobi.livelopontos.service.livelo.activate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserData implements Serializable{

    private static final long serialVersionUID = 1L;

    @SerializedName("middleName")
    private String middleName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("RG")
    private String RG;

    @SerializedName("motherName")
    private String motherName;

    @SerializedName("phoneList")
    private List<PhoneList> phoneList = new ArrayList<PhoneList>();

    @SerializedName("emailAddress")
    private String emailAddress;

    @SerializedName("authenticationStatus")
    private boolean authenticationStatus;

    @SerializedName("emailId")
    private String emailId;

    @SerializedName("CPF")
    private String CPF;

    @SerializedName("address")
    private List<Address> address = new ArrayList<Address>();

    @SerializedName("nickName")
    private String nickName;

    @SerializedName("gender")
    private String gender;

    @SerializedName("birthDate")
    private BirthDate birthDate;

    @SerializedName("customerStatus")
    private String customerStatus;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("authenticationToken")
    private String authenticationToken;

    /**
     * 
     * @return
     *     The middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * 
     * @param middleName
     *     The middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The RG
     */
    public String getRG() {
        return RG;
    }

    /**
     * 
     * @param RG
     *     The RG
     */
    public void setRG(String RG) {
        this.RG = RG;
    }

    /**
     * 
     * @return
     *     The motherName
     */
    public String getMotherName() {
        return motherName;
    }

    /**
     * 
     * @param motherName
     *     The motherName
     */
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    /**
     * 
     * @return
     *     The phoneList
     */
    public List<PhoneList> getPhoneList() {
        return phoneList;
    }

    /**
     * 
     * @param phoneList
     *     The phoneList
     */
    public void setPhoneList(List<PhoneList> phoneList) {
        this.phoneList = phoneList;
    }

    /**
     * 
     * @return
     *     The emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * 
     * @param emailAddress
     *     The emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * 
     * @return
     *     The authenticationStatus
     */
    public boolean isAuthenticationStatus() {
        return authenticationStatus;
    }

    /**
     * 
     * @param authenticationStatus
     *     The authenticationStatus
     */
    public void setAuthenticationStatus(boolean authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    /**
     * 
     * @return
     *     The emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * 
     * @param emailId
     *     The emailId
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * 
     * @return
     *     The CPF
     */
    public String getCPF() {
        return CPF;
    }

    /**
     * 
     * @param CPF
     *     The CPF
     */
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    /**
     * 
     * @return
     *     The address
     */
    public List<Address> getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(List<Address> address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 
     * @param nickName
     *     The nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The birthDate
     */
    public BirthDate getBirthDate() {
        return birthDate;
    }

    /**
     * 
     * @param birthDate
     *     The birthDate
     */
    public void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * 
     * @return
     *     The customerStatus
     */
    public String getCustomerStatus() {
        return customerStatus;
    }

    /**
     * 
     * @param customerStatus
     *     The customerStatus
     */
    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
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
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The authenticationToken
     */
    public String getAuthenticationToken() {
        return authenticationToken;
    }

    /**
     * 
     * @param authenticationToken
     *     The authenticationToken
     */
    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

}
