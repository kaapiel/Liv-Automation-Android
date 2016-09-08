
package br.com.pontomobi.livelopontos.service.livelo.activate.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BirthDate implements Serializable{

    private static final long serialVersionUID = 1L;

    @SerializedName("time")
    private long time;

    /**
     * 
     * @return
     *     The time
     */
    public long getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(long time) {
        this.time = time;
    }

}
