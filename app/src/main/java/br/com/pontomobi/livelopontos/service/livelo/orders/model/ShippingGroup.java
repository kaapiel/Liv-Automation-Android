
package br.com.pontomobi.livelopontos.service.livelo.orders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShippingGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("shippingAddress")
    private ShippingAddress shippingAddress;

    /**
     * 
     * @return
     *     The shippingAddress
     */
    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    /**
     * 
     * @param shippingAddress
     *     The shippingAddress
     */
    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
