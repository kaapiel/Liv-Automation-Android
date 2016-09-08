package br.com.pontomobi.livelopontos.service.wear.model;

/**
 * Created by selem.gomes on 18/01/16.
 */
public class WearRescueCode {
    long timeCurrent;
    String rescueCode;

    public WearRescueCode(long timeCurrent, String rescueCode) {
        this.timeCurrent = timeCurrent;
        this.rescueCode = rescueCode;
    }

    public long getTimeCurrent() {
        return timeCurrent;
    }

    public void setTimeCurrent(long timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    public String getRescueCode() {
        return rescueCode;
    }

    public void setRescueCode(String rescueCode) {
        this.rescueCode = rescueCode;
    }
}
