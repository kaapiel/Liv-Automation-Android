package br.com.aguido.livautomation.service.livautomation.productCatalog.product;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diogo Bittencourt on 5/23/16.
 */
public class AddItemToCartRequest {

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("catalogRefIds")
    private String catalogRefIds;

    @SerializedName("productId")
    private String productId;

    public AddItemToCartRequest() {
    }

    public AddItemToCartRequest(int quantity, String catalogRefIds, String productId) {
        this.quantity = quantity;
        this.catalogRefIds = catalogRefIds;
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCatalogRefIds() {
        return catalogRefIds;
    }

    public void setCatalogRefIds(String catalogRefIds) {
        this.catalogRefIds = catalogRefIds;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
