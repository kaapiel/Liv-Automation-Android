package br.com.aguido.livautomation.model;


import java.io.Serializable;

/**
 * Created by selem.gomes on 02/09/15.
 */
public class Tutorial implements Serializable {

    private String titleHeader;
    private String descriptionHeader;
    private String titleFooter;
    private String descriptionFooter;
    private String background;
    private String imageHeader;
    private String imageFooter;


    public Tutorial(String titleHeader, String descriptionHeader, String titleFooter, String descriptionFooter, String background, String imageHeader, String imageFooter) {
        this.titleHeader = titleHeader;
        this.descriptionHeader = descriptionHeader;
        this.titleFooter = titleFooter;
        this.descriptionFooter = descriptionFooter;
        this.background = background;
        this.imageHeader = imageHeader;
        this.imageFooter = imageFooter;
    }

    public String getTitleHeader() {
        return titleHeader;
    }

    public void setTitleHeader(String titleHeader) {
        this.titleHeader = titleHeader;
    }

    public String getDescriptionHeader() {
        return descriptionHeader;
    }

    public void setDescriptionHeader(String descriptionHeader) {
        this.descriptionHeader = descriptionHeader;
    }

    public String getTitleFooter() {
        return titleFooter;
    }

    public void setTitleFooter(String titleFooter) {
        this.titleFooter = titleFooter;
    }

    public String getDescriptionFooter() {
        return descriptionFooter;
    }

    public void setDescriptionFooter(String descriptionFooter) {
        this.descriptionFooter = descriptionFooter;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getImageHeader() {
        return imageHeader;
    }

    public void setImageHeader(String imageHeader) {
        this.imageHeader = imageHeader;
    }

    public String getImageFooter() {
        return imageFooter;
    }

    public void setImageFooter(String imageFooter) {
        this.imageFooter = imageFooter;
    }
}
