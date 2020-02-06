package br.com.aguido.livautomation.service.livautomation.productCatalog.product;

import com.google.gson.annotations.SerializedName;

import br.com.aguido.livautomation.service.livautomation.productCatalog.subcategory.model.Product;

/**
 * Created by Diogo Bittencourt on 16/05/2016.
 */
public class ProductResponse {

    @SerializedName("product")
    private Product product;

    public ProductResponse() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
