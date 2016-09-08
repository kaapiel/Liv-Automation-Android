package br.com.pontomobi.livelopontos.service.livelo.cart;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vilmar.filho on 5/20/16.
 */
public class CoupomRequest {

    @SerializedName("couponClaimCode")
    private String couponClaimCode;

    public CoupomRequest(String couponClaimCode) {
        this.couponClaimCode = couponClaimCode;
    }

    /**
     *
     * @return
     * The couponClaimCode
     */
    public String getCouponClaimCode() {
        return couponClaimCode;
    }

    /**
     *
     * @param couponClaimCode
     * The couponClaimCode
     */
    public void setCouponClaimCode(String couponClaimCode) {
        this.couponClaimCode = couponClaimCode;
    }

}
