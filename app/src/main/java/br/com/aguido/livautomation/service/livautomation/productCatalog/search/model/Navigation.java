
package br.com.aguido.livautomation.service.livautomation.productCatalog.search.model;

import com.google.gson.annotations.SerializedName;

public class Navigation {

    @SerializedName("totalNumERecs")
    private int totalNumERecs;

    /**
     * 
     * @return
     *     The totalNumERecs
     */
    public int getTotalNumERecs() {
        return totalNumERecs;
    }

    /**
     * 
     * @param totalNumERecs
     *     The totalNumERecs
     */
    public void setTotalNumERecs(int totalNumERecs) {
        this.totalNumERecs = totalNumERecs;
    }

}
