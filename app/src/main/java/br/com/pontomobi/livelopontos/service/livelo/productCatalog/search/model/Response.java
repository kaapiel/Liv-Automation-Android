
package br.com.pontomobi.livelopontos.service.livelo.productCatalog.search.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.model.Product;

public class Response {

    @SerializedName("navigation")
    private Navigation navigation;

    @SerializedName("records")
    private List<Product> products = new ArrayList<>();

    /**
     * 
     * @return
     *     The navigation
     */
    public Navigation getNavigation() {
        return navigation;
    }

    /**
     * 
     * @param navigation
     *     The navigation
     */
    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    /**
     * 
     * @return
     *     The products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * 
     * @param products
     *     The records
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
