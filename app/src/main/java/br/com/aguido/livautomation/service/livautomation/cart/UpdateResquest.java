package br.com.aguido.livautomation.service.livautomation.cart;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vilmar.filho on 5/20/16.
 */
public class UpdateResquest {

    @SerializedName("skuId")
    private String skuId;

    @SerializedName("quantity")
    private String quantity;

    public UpdateResquest(String skuId, String quantity) {
        this.skuId = skuId;
        this.quantity = quantity;
    }

    /**
     *
     * @return
     * The skuId
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     *
     * @param skuId
     * The skuId
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    /**
     *
     * @return
     * The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
