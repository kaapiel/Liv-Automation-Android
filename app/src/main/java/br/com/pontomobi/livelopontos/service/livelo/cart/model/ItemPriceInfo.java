
package br.com.pontomobi.livelopontos.service.livelo.cart.model;

import com.google.gson.annotations.SerializedName;

public class ItemPriceInfo {

    @SerializedName("listPrice")
    private int listPrice;

    @SerializedName("salePrice")
    private int salePrice;

    /**
     * 
     * @return
     *     The listPrice
     */
    public int getListPrice() {
        return listPrice;
    }

    /**
     * 
     * @param listPrice
     *     The listPrice
     */
    public void setListPrice(int listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * 
     * @return
     *     The salePrice
     */
    public int getSalePrice() {
        return salePrice;
    }

    /**
     * 
     * @param salePrice
     *     The salePrice
     */
    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

}
