
package br.com.pontomobi.livelopontos.service.livelo.cart.model;

import com.google.gson.annotations.SerializedName;

public class CommerceItem {

    @SerializedName("productDisplayName")
    private String productDisplayName;

    @SerializedName("productId")
    private String productId;

    @SerializedName("id")
    private String id;

    @SerializedName("priceInfo")
    private ItemPriceInfo mItemPriceInfo;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("catalogRefId")
    private String catalogRefId;

    private String urlImage;

    /**
     * 
     * @return
     *     The productDisplayName
     */
    public String getProductDisplayName() {
        return productDisplayName;
    }

    /**
     * 
     * @param productDisplayName
     *     The productDisplayName
     */
    public void setProductDisplayName(String productDisplayName) {
        this.productDisplayName = productDisplayName;
    }

    /**
     * 
     * @return
     *     The productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 
     * @param productId
     *     The productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
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
    public ItemPriceInfo getItemPriceInfo() {
        if (mItemPriceInfo != null) {
            return mItemPriceInfo;
        } else {
            return new ItemPriceInfo();
        }
    }

    /**
     * 
     * @param itemPriceInfo
     *     The priceInfo
     */
    public void setItemPriceInfo(ItemPriceInfo itemPriceInfo) {
        this.mItemPriceInfo = itemPriceInfo;
    }

    /**
     * 
     * @return
     *     The quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * @return
     *     The catalogRefId
     */
    public String getCatalogRefId() {
        return catalogRefId;
    }

    /**
     * 
     * @param catalogRefId
     *     The catalogRefId
     */
    public void setCatalogRefId(String catalogRefId) {
        this.catalogRefId = catalogRefId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
