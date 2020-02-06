package br.com.aguido.livautomation.service.livautomation.productCatalog.product;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diogo Bittencourt on 16/05/2016.
 */
public class ProductRequest {

    @SerializedName("productId")
    private String productId;

    public ProductRequest() {
    }

    public ProductRequest(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
