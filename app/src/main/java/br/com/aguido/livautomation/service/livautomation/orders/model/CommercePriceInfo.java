
package br.com.aguido.livautomation.service.livautomation.orders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercePriceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("currentPriceDetailsSorted")
    private List<CurrentPriceDetailsSorted> currentPriceDetailsSorted = new ArrayList<>();

    @SerializedName("salePrice")
    private Integer salePrice;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<CurrentPriceDetailsSorted> getCurrentPriceDetailsSorted() {
        return currentPriceDetailsSorted;
    }

    public void setCurrentPriceDetailsSorted(List<CurrentPriceDetailsSorted> currentPriceDetailsSorted) {
        this.currentPriceDetailsSorted = currentPriceDetailsSorted;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }
}
