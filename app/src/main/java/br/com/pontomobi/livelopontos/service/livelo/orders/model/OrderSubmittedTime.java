package br.com.pontomobi.livelopontos.service.livelo.orders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderSubmittedTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("time")
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
