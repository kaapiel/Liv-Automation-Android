
package br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.model;

import com.google.gson.annotations.SerializedName;


public class SkuImageSet {

    @SerializedName("thumbnailImage")
    String thumbnailImage;

    @SerializedName("mediumImage")
    String mediumImage;

    @SerializedName("smallImage")
    String smallImage;

    @SerializedName("largeImage")
    String largeImage;

    @SerializedName("id")
    String id;

    @SerializedName("item-id")
    String itemId;

    /**
     * 
     * @return
     *     The thumbnailImage
     */
    public String getThumbnailImage() {
        return thumbnailImage;
    }

    /**
     * 
     * @param thumbnailImage
     *     The thumbnailImage
     */
    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    /**
     * 
     * @return
     *     The mediumImage
     */
    public String getMediumImage() {
        return mediumImage;
    }

    /**
     * 
     * @param mediumImage
     *     The mediumImage
     */
    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    /**
     * 
     * @return
     *     The smallImage
     */
    public String getSmallImage() {
        return smallImage;
    }

    /**
     * 
     * @param smallImage
     *     The smallImage
     */
    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    /**
     * 
     * @return
     *     The largeImage
     */
    public String getLargeImage() {
        return largeImage;
    }

    /**
     * 
     * @param largeImage
     *     The largeImage
     */
    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
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
     *     The itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 
     * @param itemId
     *     The item-id
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
