package br.com.pontomobi.livelopontos.model;

/**
 * Created by selemafonso on 03/11/15.
 */
public class MenuMyAccount {

    private String icon;
    private String title;
    private boolean isFingerPrint = false;
    private boolean fingerEnable = false;


    public MenuMyAccount(String icon, String title) {
        this.icon = icon;
        this.title = title;
        this.isFingerPrint = false;
        this.fingerEnable = false;
    }

    public MenuMyAccount(String icon, String title, boolean isFingerPrint, boolean fingerEnable) {
        this.icon = icon;
        this.title = title;
        this.isFingerPrint = isFingerPrint;
        this.fingerEnable = fingerEnable;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFingerEnable() {
        return fingerEnable;
    }

    public void setFingerEnable(boolean fingerEnable) {
        this.fingerEnable = fingerEnable;
    }

    public boolean isFingerPrint() {
        return isFingerPrint;
    }

    public void setFingerPrint(boolean fingerPrint) {
        isFingerPrint = fingerPrint;
    }
}
