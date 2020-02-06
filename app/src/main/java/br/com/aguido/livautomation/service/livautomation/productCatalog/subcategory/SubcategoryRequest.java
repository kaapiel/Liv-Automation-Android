package br.com.aguido.livautomation.service.livautomation.productCatalog.subcategory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vilmar.filho on 5/13/16.
 */
public class SubcategoryRequest {

    @SerializedName("categoryId")
    private String categoryId;

    public SubcategoryRequest(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

}
