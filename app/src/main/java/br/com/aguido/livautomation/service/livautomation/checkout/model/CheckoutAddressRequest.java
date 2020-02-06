package br.com.aguido.livautomation.service.livautomation.checkout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vilmar on 5/21/16.
 */
public class CheckoutAddressRequest {

    @SerializedName("shipToAddressId")
    private String shipToAddressId;

    public CheckoutAddressRequest(String shipToAddressId) {
        this.shipToAddressId = shipToAddressId;
    }

    /**
     *
     * @return
     * The shipToAddressId
     */
    public String getShipToAddressId() {
        return shipToAddressId;
    }

    /**
     *
     * @param shipToAddressId
     * The shipToAddressId
     */
    public void setShipToAddressId(String shipToAddressId) {
        this.shipToAddressId = shipToAddressId;
    }

}
