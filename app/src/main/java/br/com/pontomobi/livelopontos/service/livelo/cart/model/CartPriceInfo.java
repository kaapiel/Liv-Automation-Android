
package br.com.pontomobi.livelopontos.service.livelo.cart.model;

import com.google.gson.annotations.SerializedName;

public class CartPriceInfo {

    @SerializedName("discountAmount")
    private int discountAmount;

    @SerializedName("amount")
    private int amount;

    @SerializedName("cashQualifiedSubTotal")
    private int cashQualifiedSubTotal;

    /**
     * 
     * @return
     *     The discountAmount
     */
    public int getDiscountAmount() {
        return discountAmount;
    }

    /**
     * 
     * @param discountAmount
     *     The discountAmount
     */
    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * 
     * @return
     *     The amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The cashQualifiedSubTotal
     */
    public int getCashQualifiedSubTotal() {
        return cashQualifiedSubTotal;
    }

    /**
     * 
     * @param cashQualifiedSubTotal
     *     The cashQualifiedSubTotal
     */
    public void setCashQualifiedSubTotal(int cashQualifiedSubTotal) {
        this.cashQualifiedSubTotal = cashQualifiedSubTotal;
    }

}
