
package br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import br.com.pontomobi.livelopontos.service.livelo.productCatalog.category.model.Category;

public class Subcategory {

    @SerializedName("category")
    private Category category;

    @SerializedName("childCategories")
    private List<Category> childCategories = new ArrayList<>();

    @SerializedName("childProducts")
    private List<Product> mProducts = new ArrayList<>();

    /**
     * 
     * @return
     *     The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The childCategories
     */
    public List<Category> getChildCategories() {
        return childCategories;
    }

    /**
     *
     * @param childCategories
     * The childCategories
     */
    public void setChildCategories(List<Category> childCategories) {
        this.childCategories = childCategories;
    }

    /**
     * 
     * @return
     *     The childProducts
     */
    public List<Product> getProducts() {
        return mProducts;
    }

    /**
     * 
     * @param products
     *     The childProducts
     */
    public void setProducts(List<Product> products) {
        this.mProducts = products;
    }

}
