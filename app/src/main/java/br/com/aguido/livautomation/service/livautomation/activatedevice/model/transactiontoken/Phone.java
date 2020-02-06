package br.com.aguido.livautomation.service.livautomation.activatedevice.model.transactiontoken;

import com.google.gson.annotations.SerializedName;

public class Phone {

    @SerializedName("CountryCode")
    private int CountryCode;

    @SerializedName("AreaCode")
    private int AreaCode;

    @SerializedName("Number")
    private int Number;


    public Phone(int countryCode, int areaCode, int number) {
        CountryCode = countryCode;
        AreaCode = areaCode;
        Number = number;
    }

    /**
     * 
     * @return
     *     The CountryCode
     */
    public int getCountryCode() {
        return CountryCode;
    }

    /**
     * 
     * @param CountryCode
     *     The CountryCode
     */
    public void setCountryCode(int CountryCode) {
        this.CountryCode = CountryCode;
    }

    /**
     * 
     * @return
     *     The AreaCode
     */
    public int getAreaCode() {
        return AreaCode;
    }

    /**
     * 
     * @param AreaCode
     *     The AreaCode
     */
    public void setAreaCode(int AreaCode) {
        this.AreaCode = AreaCode;
    }

    /**
     * 
     * @return
     *     The Number
     */
    public int getNumber() {
        return Number;
    }

    /**
     * 
     * @param Number
     *     The Number
     */
    public void setNumber(int Number) {
        this.Number = Number;
    }

}
