
package br.com.pontomobi.livelopontos.service.livelo.productCatalog.category.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Categories {

    @SerializedName("rootCategories")
    private List<Category> categories = new ArrayList<>();

    /**
     * 
     * @return
     *     The Categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The Categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
