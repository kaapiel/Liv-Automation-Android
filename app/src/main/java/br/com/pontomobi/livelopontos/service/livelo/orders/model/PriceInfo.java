
package br.com.pontomobi.livelopontos.service.livelo.orders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PriceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("amountInPoints")
    private Integer amountInPoints;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("cashAmount")
    private float cashAmount;

    /**
     * 
     * @return
     *     The amountInPoints
     */
    public Integer getAmountInPoints() {
        return amountInPoints;
    }

    /**
     * 
     * @param amountInPoints
     *     The amountInPoints
     */
    public void setAmountInPoints(Integer amountInPoints) {
        this.amountInPoints = amountInPoints;
    }

    /**
     * 
     * @return
     *     The amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The cashAmount
     */
    public float getCashAmount() {
        return cashAmount;
    }

    /**
     * 
     * @param cashAmount
     *     The cashAmount
     */
    public void setCashAmount(float cashAmount) {
        this.cashAmount = cashAmount;
    }

}
