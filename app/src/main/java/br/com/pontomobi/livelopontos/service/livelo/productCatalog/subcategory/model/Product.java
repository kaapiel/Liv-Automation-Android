
package br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product {

    @SerializedName("marketPlaceProducts")
    private List<MarketPlaceProduct> marketPlaceProducts = new ArrayList<MarketPlaceProduct>();

    @SerializedName("childSKUs")
    private List<ChildSKU> childSKUs = new ArrayList<ChildSKU>();

    @SerializedName("repositoryId")
    private String repositoryId;

    @SerializedName("url")
    private String url;

    @SerializedName("id")
    private String id;

    @SerializedName("largeImageUrl")
    private String largeImageUrl;

    @SerializedName("longDescription")
    private String longDescription;

    @SerializedName("partner")
    private Partner partner;

    @SerializedName("mediumImageUrl")
    private String mediumImageUrl;

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("ratings")
    private int ratings;

    @SerializedName("thumbnailImageUrl")
    private String thumbnailImageUrl;

    @SerializedName("highestSalePrice")
    long highestSalePrice;

    @SerializedName("lowestSalePrice")
    long lowestSalePrice;

    /**
     * 
     * @return
     *     The marketPlaceProducts
     */
    public List<MarketPlaceProduct> getMarketPlaceProducts() {
        return marketPlaceProducts;
    }

    /**
     * 
     * @param marketPlaceProducts
     *     The marketPlaceProducts
     */
    public void setMarketPlaceProducts(List<MarketPlaceProduct> marketPlaceProducts) {
        this.marketPlaceProducts = marketPlaceProducts;
    }

    /**
     * 
     * @return
     *     The childSKUs
     */
    public List<ChildSKU> getChildSKUs() {
        return childSKUs;
    }

    /**
     * 
     * @param childSKUs
     *     The childSKUs
     */
    public void setChildSKUs(List<ChildSKU> childSKUs) {
        this.childSKUs = childSKUs;
    }

    /**
     * 
     * @return
     *     The repositoryId
     */
    public String getRepositoryId() {
        return repositoryId;
    }

    /**
     * 
     * @param repositoryId
     *     The repositoryId
     */
    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
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
     *     The largeImageUrl
     */
    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    /**
     * 
     * @param largeImageUrl
     *     The largeImageUrl
     */
    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    /**
     * 
     * @return
     *     The longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * 
     * @param longDescription
     *     The longDescription
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * 
     * @return
     *     The partner
     */
    public Partner getPartner() {
        return partner;
    }

    /**
     * 
     * @param partner
     *     The partner
     */
    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    /**
     * 
     * @return
     *     The mediumImageUrl
     */
    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    /**
     * 
     * @param mediumImageUrl
     *     The mediumImageUrl
     */
    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
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

    /**
     * 
     * @return
     *     The ratings
     */
    public int getRatings() {
        return ratings;
    }

    /**
     * 
     * @param ratings
     *     The ratings
     */
    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    /**
     * 
     * @return
     *     The thumbnailImageUrl
     */
    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    /**
     * 
     * @param thumbnailImageUrl
     *     The thumbnailImageUrl
     */
    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public long getHighestSalePrice() {
        return highestSalePrice;
    }

    public void setHighestSalePrice(long highestSalePrice) {
        this.highestSalePrice = highestSalePrice;
    }

    public long getLowestSalePrice() {
        return lowestSalePrice;
    }

    public void setLowestSalePrice(long lowestSalePrice) {
        this.lowestSalePrice = lowestSalePrice;
    }
}
