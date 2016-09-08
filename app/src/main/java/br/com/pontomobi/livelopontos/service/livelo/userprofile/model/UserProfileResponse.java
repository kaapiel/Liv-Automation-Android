package br.com.pontomobi.livelopontos.service.livelo.userprofile.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by selem.gomes on 13/11/15.
 */
public class UserProfileResponse {
    @SerializedName("receivePromoSMS")
    private boolean receivePromoSMS;
    @SerializedName("dateOfBirth")
    private UserDateResponse dateOfBirth;
    @SerializedName("receivePromoEmail")
    private boolean receivePromoEmail;
    @SerializedName("pushPermission")
    private boolean receivePush;
    //@SerializedName("expressCheckout")
    //private boolean expressCheckout;
    @SerializedName("locale")
    private String locale;
    @SerializedName("rg")
    private String rg;
    //@SerializedName("registrationDate")
    //private UserDateResponse registrationDate;
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("homeAddress")
    private UserAddressResponse homeAddress;
    //@SerializedName("favoriteStores")
    //private List<Object> favoriteStores = new ArrayList<Object>();
    @SerializedName("gender")
    private String gender;
    @SerializedName("secondaryAddresses")
    private ListUserAddress secondaryAddresses;
    @SerializedName("login")
    private String login;
    @SerializedName("cpf")
    private String cpf;
    @SerializedName("firstName")
    private String firstName;
    //@SerializedName("allowPartialShipment")
    //private boolean allowPartialShipment;
    @SerializedName("shippingAddress")
    private UserAddressResponse shippingAddress;
    //@SerializedName("middleName")
    //private String middleName;
    //@SerializedName("lastName")
    //private String lastName;
    //@SerializedName("defaultCreditCard")
    //private String defaultCreditCard;
    //@SerializedName("orderPriceLimit")
    //private String orderPriceLimit;
    @SerializedName("phoneNumbers")
    private List<UserPhoneResponse> phoneNumbers = new ArrayList<UserPhoneResponse>();
    //@SerializedName("currentLocation")
    //private String currentLocation;
    @SerializedName("email")
    private String email;
    //@SerializedName("daytimeTelephoneNumber")
    //private String daytimeTelephoneNumber;
    //@SerializedName("billingAddress")
    //private UserAddressResponse billingAddress;
    @SerializedName("fullName")
    private String fullName;
    //@SerializedName("purchaseLists")
    //private List<Object> purchaseLists = new ArrayList<Object>();

    //**
    //* This attribute was deprecated.
    //*
    //*  @deprecated Use {@link UserProfileResponse#lastUpdateData} instead.
    //*
    //*/
    //private transient Timestamp lastUpdate;

    private long lastUpdateData;

    public boolean isReceivePromoSMS() {
        return receivePromoSMS;
    }

    public void setReceivePromoSMS(boolean receivePromoSMS) {
        this.receivePromoSMS = receivePromoSMS;
    }

    public UserDateResponse getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(UserDateResponse dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isReceivePromoEmail() {
        return receivePromoEmail;
    }

    public void setReceivePromoEmail(boolean receivePromoEmail) {
        this.receivePromoEmail = receivePromoEmail;
    }

    public boolean isReceivePush() {
        return receivePush;
    }

    public void setReceivePush(boolean receivePush) {
        this.receivePush = receivePush;
    }

    /*public boolean isExpressCheckout() {
        return expressCheckout;
    }

    public void setExpressCheckout(boolean expressCheckout) {
        this.expressCheckout = expressCheckout;
    }*/

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    /*public UserDateResponse getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(UserDateResponse registrationDate) {
        this.registrationDate = registrationDate;
    }*/

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public UserAddressResponse getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(UserAddressResponse homeAddress) {
        this.homeAddress = homeAddress;
    }

    /*public List<Object> getFavoriteStores() {
        return favoriteStores;
    }

    public void setFavoriteStores(List<Object> favoriteStores) {
        this.favoriteStores = favoriteStores;
    }*/

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ListUserAddress getSecondaryAddresses() {
        return secondaryAddresses;
    }

    public void setSecondaryAddresses(ListUserAddress secondaryAddresses) {
        this.secondaryAddresses = secondaryAddresses;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /*public boolean isAllowPartialShipment() {
        return allowPartialShipment;
    }

    public void setAllowPartialShipment(boolean allowPartialShipment) {
        this.allowPartialShipment = allowPartialShipment;
    }*/

    public UserAddressResponse getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(UserAddressResponse shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /*public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }*/

    /*public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }*/

    /*public String getDefaultCreditCard() {
        return defaultCreditCard;
    }

    public void setDefaultCreditCard(String defaultCreditCard) {
        this.defaultCreditCard = defaultCreditCard;
    }*/

    /*public String getOrderPriceLimit() {
        return orderPriceLimit;
    }

    public void setOrderPriceLimit(String orderPriceLimit) {
        this.orderPriceLimit = orderPriceLimit;
    }*/

    public List<UserPhoneResponse> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<UserPhoneResponse> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    /*public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public String getDaytimeTelephoneNumber() {
        return daytimeTelephoneNumber;
    }

    public void setDaytimeTelephoneNumber(String daytimeTelephoneNumber) {
        this.daytimeTelephoneNumber = daytimeTelephoneNumber;
    }*/

    /*public UserAddressResponse getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(UserAddressResponse billingAddress) {
        this.billingAddress = billingAddress;
    }*/

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /*public List<Object> getPurchaseLists() {
        return purchaseLists;
    }

    public void setPurchaseLists(List<Object> purchaseLists) {
        this.purchaseLists = purchaseLists;
    }*/

    /*public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }*/

    public long getLastUpdateData() {
        return lastUpdateData;
    }

    public void setLastUpdateData(long lastUpdateData) {
        this.lastUpdateData = lastUpdateData;
    }

    public String getPhoneNumberByType(String type) {
        List<UserPhoneResponse> listByType = getListPhoneByType(type);

        for (UserPhoneResponse phoneResponse : getPhoneNumbers()) {
            if (phoneResponse.getPhoneType().equals(type) &&  listByType.size() > 1) {

                if (phoneResponse.getPhoneNo().length() < 8) {
                    continue;
                }

                return getMasterPhone(listByType);

            } else if (phoneResponse.getPhoneType().equals(type)) {

                if (phoneResponse.getPhoneNo().length() < 8) {
                    continue;
                }

                return (phoneResponse.getPhoneAreaCode()) + phoneResponse.getPhoneNo();
            }
        }

        return "";
    }

    public UserPhoneResponse getUserPhoneByType(String type){
        for (UserPhoneResponse phoneResponse : getPhoneNumbers()) {
            if (phoneResponse.getPhoneType().equals(type)) {
                return phoneResponse;
            }
        }

        return null;
    }

    private List<UserPhoneResponse> getListPhoneByType(String type) {
        List<UserPhoneResponse> listTemp = new ArrayList<>();

        for (UserPhoneResponse phoneResponse : getPhoneNumbers()) {
            if (phoneResponse.getPhoneType().equals(type)) {
                if (phoneResponse.getPhoneNo().length() < 8) {
                    continue;
                }

                listTemp.add(phoneResponse);
            }
        }
        return listTemp;
    }

    private String getMasterPhone(List<UserPhoneResponse> list) {
        for (int i = 0; i < list.size(); i++) {
            UserPhoneResponse phone = list.get(i);

            if (phone.getMasterFlag()) {
               return  (phone.getPhoneAreaCode()) + phone.getPhoneNo();
            }
        }

        return  (list.get(0).getPhoneAreaCode()) + list.get(0).getPhoneNo();
    }
}
