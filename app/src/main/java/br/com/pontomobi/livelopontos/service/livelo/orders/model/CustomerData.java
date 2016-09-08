
package br.com.pontomobi.livelopontos.service.livelo.orders.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerData implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("fullName")
    private String fullName;

    /**
     * 
     * @return
     *     The fullName
     */
    public String getFullName() {
        return TextUtils.isEmpty(fullName) ? "" : fullName;
    }

    /**
     * 
     * @param fullName
     *     The fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
