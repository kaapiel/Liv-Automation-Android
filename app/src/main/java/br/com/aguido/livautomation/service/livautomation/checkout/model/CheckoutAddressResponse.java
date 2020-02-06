
package br.com.aguido.livautomation.service.livautomation.checkout.model;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class CheckoutAddressResponse {

    @SerializedName("shippingGroups")
    private LinkedHashMap<String, ShippingAddress> shippingGroups;

    public LinkedHashMap<String, ShippingAddress> getShippingGroups() {
        return shippingGroups;
    }

    public void setShippingGroups(LinkedHashMap<String, ShippingAddress> shippingGroups) {
        this.shippingGroups = shippingGroups;
    }
}
