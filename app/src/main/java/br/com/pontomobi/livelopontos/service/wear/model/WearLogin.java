package br.com.pontomobi.livelopontos.service.wear.model;

/**
 * Created by selem.gomes on 15/01/16.
 */
public class WearLogin {

    private boolean isLogged;

    public WearLogin(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }
}
