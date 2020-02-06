package br.com.aguido.livautomation.service.livautomation.orders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vilmar.filho on 12/22/15.
 */
public class PaymentGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("creditCardNumber")
    private String creditCardNumber;

    @SerializedName("creditCardType")
    private String creditCardType;

    @SerializedName("amount")
    private long amount;


    /**
     * @return The creditCardNumber
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * @param creditCardNumber The creditCardNumber
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * @return The creditCardType
     */
    public String getCreditCardType() {
        return creditCardType;
    }

    /**
     * @param creditCardType The creditCardType
     */
    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    /**
     * @return The creditCardType
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }
}
