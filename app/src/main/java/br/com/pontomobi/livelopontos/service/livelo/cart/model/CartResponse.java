
package br.com.pontomobi.livelopontos.service.livelo.cart.model;

import com.google.gson.annotations.SerializedName;

public class CartResponse {

    @SerializedName("order")
    private CartOrder mCartOrder;

    /**
     * 
     * @return
     *     The order
     */
    public CartOrder getCartOrder() {
        return mCartOrder;
    }

    /**
     * 
     * @param cartOrder
     *     The order
     */
    public void setCartOrder(CartOrder cartOrder) {
        this.mCartOrder = cartOrder;
    }

}
