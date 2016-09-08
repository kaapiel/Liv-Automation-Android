
package br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChildSKU {

    @SerializedName("gtin")
    String gtin;

    @SerializedName("variation")
    Map<String, String> variation;

    @SerializedName("hasPrice")
    boolean hasPrice;

    @SerializedName("CashAllowed")
    boolean cashAllowed;

    @SerializedName("available")
    boolean available;

    @SerializedName("repositoryId")
    String repositoryId;

    @SerializedName("listPrice")
    long listPrice;

    @SerializedName("skuImageSet")
    List<SkuImageSet> skuImageSet = new ArrayList<>();

    @SerializedName("salePrice")
    long salePrice;

    @SerializedName("partner")
    Partner partner;

    @SerializedName("displayName")
    String displayName;

    /**
     * @return The gtin
     */
    public String getGtin() {
        return gtin;
    }

    /**
     * @param gtin The gtin
     */
    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    /**
     * @return The variation
     */
    public Map<String, String> getVariation() {
        return variation;
    }

    /**
     * @param variation The variation
     */
    public void setVariation(Map<String, String> variation) {
        this.variation = variation;
    }

    /**
     * @return The hasPrice
     */
    public boolean isHasPrice() {
        return hasPrice;
    }

    /**
     * @param hasPrice The hasPrice
     */
    public void setHasPrice(boolean hasPrice) {
        this.hasPrice = hasPrice;
    }

    /**
     * @return The cashAllowed
     */
    public boolean isCashAllowed() {
        return cashAllowed;
    }

    /**
     * @param CashAllowed The cashAllowed
     */
    public void setCashAllowed(boolean CashAllowed) {
        this.cashAllowed = CashAllowed;
    }

    /**
     * @return The available
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * @param available The available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * @return The repositoryId
     */
    public String getRepositoryId() {
        return repositoryId;
    }

    /**
     * @param repositoryId The repositoryId
     */
    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * @return The listPrice
     */
    public long getListPrice() {
        return listPrice;
    }

    /**
     * @param listPrice The listPrice
     */
    public void setListPrice(int listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * @return The skuImageSet
     */
    public List<SkuImageSet> getSkuImageSet() {
        return skuImageSet;
    }

    /**
     * @param skuImageSet The skuImageSet
     */
    public void setSkuImageSet(List<SkuImageSet> skuImageSet) {
        this.skuImageSet = skuImageSet;
    }

    /**
     * @return The salePrice
     */
    public long getSalePrice() {
        return salePrice;
    }

    /**
     * @param salePrice The salePrice
     */
    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * @return The partner
     */
    public Partner getPartner() {
        return partner;
    }

    /**
     * @param partner The partner
     */
    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    /**
     * @return The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName The displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
