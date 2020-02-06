package br.com.aguido.livautomation.service.livautomation.userprofile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by selem.gomes on 13/11/15.
 */
public class UserDateResponse {
    @SerializedName("time")
    private long dateTime;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
