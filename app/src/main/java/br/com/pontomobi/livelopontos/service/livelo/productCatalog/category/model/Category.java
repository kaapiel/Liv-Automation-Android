
package br.com.pontomobi.livelopontos.service.livelo.productCatalog.category.model;

import com.google.gson.annotations.SerializedName;


public class Category {

    @SerializedName("id")
    String id;

    @SerializedName("displayName")
    String displayName;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * @param displayName
     *     The displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
