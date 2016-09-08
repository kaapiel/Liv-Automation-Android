
package br.com.pontomobi.livelopontos.service.livelo.orders.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderResponse {

    @SerializedName("myOrders")
    private List<Order> orders = new ArrayList<Order>();

    /**
     * 
     * @return
     *     The myOrders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * 
     * @param orders
     *     The myOrders
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
