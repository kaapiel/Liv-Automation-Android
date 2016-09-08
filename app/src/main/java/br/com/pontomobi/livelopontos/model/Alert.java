package br.com.pontomobi.livelopontos.model;

import java.io.Serializable;

/**
 * Created by selem.gomes on 08/09/15.
 */
public class Alert implements Serializable {
    private String image;
    private String title;
    private String description;
    private String positiveButton;
    private String negativeButton;
    private boolean getPassword = false;

    public Alert() {
    }

    public Alert(String image, String title, String description, String positiveButton, String negativeButton) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
        this.getPassword = false;
    }

    public Alert(String image, String title, String description, String positiveButton, String negativeButton, boolean getPassword) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
        this.getPassword = getPassword;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPositiveButton() {
        return positiveButton;
    }

    public void setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
    }

    public String getNegativeButton() {
        return negativeButton;
    }

    public void setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
    }

    public boolean isGetPassword() {
        return getPassword;
    }

    public void setGetPassword(boolean getPassword) {
        this.getPassword = getPassword;
    }
}
