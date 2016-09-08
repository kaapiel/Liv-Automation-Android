package br.com.pontomobi.livelopontos.ui.orderHistory;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.orders.model.Order;
import br.com.pontomobi.livelopontos.service.livelo.orders.model.OrderResponse;

/**
 * Created by vilmar.filho on 12/11/15.
 */
public class OrderBusiness {

    private Context mContext;
    private OnServiceOrdersListener mOnServiceOrdersListener;

    public OrderBusiness (Context context, OnServiceOrdersListener onServiceOrdersListener){
        mContext = context;
        mOnServiceOrdersListener = onServiceOrdersListener;
    }

    public void callServiceOrders(){
        LiveloRepository.with(mContext).getUserOrders(mContext, mOnServiceOrdersListener);
    }

    private OnServiceOrdersListener getOnServiceOrdersListener(){
        return new OnServiceOrdersListener(){

            @Override
            public void onGetOrdersSuccess(OrderResponse response) {

            }

            @Override
            public void onGetOrdersFail(LiveloException exception) {

            }
        };
    }

    public interface OnServiceOrdersListener {
        void onGetOrdersSuccess(OrderResponse response);

        void onGetOrdersFail(LiveloException exception);
    }

    public List<Order> filterListByStatus(List<Order> listOrders, String... status) {
        List<Order> newListOrder = new ArrayList<Order>();

        if (status.length == 0) {
            return listOrders;
        }

        for(int i = 0; i < status.length; i++){

            for (int j = 0; j < listOrders.size(); j++) {
                Order order = listOrders.get(j);
                if (order.getStateAsString().equalsIgnoreCase(status[i]) || status[i].equals(Constants.StatusOrder.ALL_STATUS)) {
                    newListOrder.add(order);
                }
            }

        }

        return newListOrder;
    }
}
