
package br.com.aguido.livautomation.service.livautomation.cart.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartOrder {

    @SerializedName("lastModifiedTime")
    private long lastModifiedTime;

    @SerializedName("commerceItems")
    private List<CommerceItem> commerceItems = new ArrayList<CommerceItem>();

    @SerializedName("id")
    private String id;

    @SerializedName("priceInfo")
    private CartPriceInfo mCartPriceInfo;

    @SerializedName("totalCommerceItemCount")
    private int totalCommerceItemCount;

    /**
     * 
     * @return
     *     The lastModifiedTime
     */
    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     * 
     * @param lastModifiedTime
     *     The lastModifiedTime
     */
    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    /**
     * 
     * @return
     *     The commerceItems
     */
    public List<CommerceItem> getCommerceItems() {
        return commerceItems;
    }

    /**
     * 
     * @param commerceItems
     *     The commerceItems
     */
    public void setCommerceItems(List<CommerceItem> commerceItems) {
        this.commerceItems = commerceItems;
    }

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
     *     The priceInfo
     */
    public CartPriceInfo getCartPriceInfo() {
        return mCartPriceInfo;
    }

    /**
     * 
     * @param cartPriceInfo
     *     The priceInfo
     */
    public void setCartPriceInfo(CartPriceInfo cartPriceInfo) {
        this.mCartPriceInfo = cartPriceInfo;
    }

    /**
     * 
     * @return
     *     The totalCommerceItemCount
     */
    public int getTotalCommerceItemCount() {
        return totalCommerceItemCount;
    }

    /**
     * 
     * @param totalCommerceItemCount
     *     The totalCommerceItemCount
     */
    public void setTotalCommerceItemCount(int totalCommerceItemCount) {
        this.totalCommerceItemCount = totalCommerceItemCount;
    }

}
