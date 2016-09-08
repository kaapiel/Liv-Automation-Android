package br.com.pontomobi.livelopontos.model;

import br.com.pontomobi.livelopontos.service.livelo.cart.model.CartOrder;

/**
 * Created by vilmar.filho on 5/20/16.
 */
public class Cart {

    private int pointsUser;
    private CartOrder cartOrderUser;

    public int getPointsUser() {
        return pointsUser;
    }

    public void setPointsUser(int pointsUser) {
        this.pointsUser = pointsUser;
    }

    public CartOrder getCartOrderUser() {
        return cartOrderUser;
    }

    public void setCartOrderUser(CartOrder cartOrderUser) {
        this.cartOrderUser = cartOrderUser;
    }
}
