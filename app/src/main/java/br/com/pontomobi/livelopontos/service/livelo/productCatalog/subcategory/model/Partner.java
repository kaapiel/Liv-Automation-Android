
package br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.model;

import com.google.gson.annotations.SerializedName;

public class Partner {

    @SerializedName("enabled")
    boolean enabled;

    @SerializedName("repositoryId")
    String repositoryId;

    @SerializedName("displayName")
    String displayName;

    @SerializedName("thumbnailImageUrl")
    String thumbnailImageUrl;

    /**
     * 
     * @return
     *     The enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 
     * @param enabled
     *     The enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
