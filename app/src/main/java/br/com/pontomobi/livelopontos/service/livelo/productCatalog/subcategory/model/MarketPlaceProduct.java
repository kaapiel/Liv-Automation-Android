
package br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.model;

import com.google.gson.annotations.SerializedName;

public class MarketPlaceProduct {

    @SerializedName("repositoryId")
    private String repositoryId;

    @SerializedName("url")
    private String url;

    @SerializedName("longDescription")
    private String longDescription;

    @SerializedName("largeImageUrl")
    private String largeImageUrl;

    @SerializedName("partner")
    private Partner partner;

    @SerializedName("mediumImageUrl")
    private String mediumImageUrl;

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("thumbnailImageUrl")
    private String thumbnailImageUrl;

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

}
