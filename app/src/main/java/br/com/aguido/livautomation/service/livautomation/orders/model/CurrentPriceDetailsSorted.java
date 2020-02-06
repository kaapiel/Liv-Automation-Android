
package br.com.aguido.livautomation.service.livautomation.orders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentPriceDetailsSorted implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("currencyCode")
    private String currencyCode;

    //@SerializedName("detailedUnitPrice")
    //private Integer detailedUnitPrice;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("quantity")
    private Integer quantity;

    /**
     * 
     * @return
     *     The currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 
     * @param currencyCode
     *     The currencyCode
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * 
     * @return
     *     The detailedUnitPrice
     */
    //public Integer getDetailedUnitPrice() {
    //    return detailedUnitPrice;
    //}

    /**
     * 
     * @param detailedUnitPrice
     *     The detailedUnitPrice
     */
    //public void setDetailedUnitPrice(Integer detailedUnitPrice) {
    //    this.detailedUnitPrice = detailedUnitPrice;
    //}

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
     *     The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
