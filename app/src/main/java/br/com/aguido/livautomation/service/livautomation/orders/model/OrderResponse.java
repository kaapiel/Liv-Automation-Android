
package br.com.aguido.livautomation.service.livautomation.orders.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
