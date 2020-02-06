
package br.com.aguido.livautomation.service.livautomation.orders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommerceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("priceInfo")
    private CommercePriceInfo commercePriceInfo;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("partnerDisplayName")
    private String partnerDisplayName;

    @SerializedName("productName")
    private String productName;

    @SerializedName("statusProduct")
    private String statusProduct;

    /**
     * 
     * @return
     *     The priceInfo
     */
    public CommercePriceInfo getCommercePriceInfo() {
        return commercePriceInfo;
    }

    /**
     * 
     * @param commercePriceInfo
     *     The priceInfo
     */
    public void setCommercePriceInfo(CommercePriceInfo commercePriceInfo) {
        this.commercePriceInfo = commercePriceInfo;
    }

    /**
     *
     * @return
     *     The quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     *     The quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     *     The partnerDisplayName
     */
    public String getPartnerDisplayName() {
        return partnerDisplayName;
    }

    /**
     *
     * @param partnerDisplayName
     *     The partnerDisplayName
     */
    public void setPartnerDisplayName(String partnerDisplayName) {
        this.partnerDisplayName = partnerDisplayName;
    }

    /**
     * 
     * @return
     *     The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 
     * @param productName
     *     The productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
    }

}
