package br.com.pontomobi.livelopontos.ui.orderHistory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.listener.OnItemClickListener;
import br.com.pontomobi.livelopontos.service.livelo.orders.model.Order;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.StringUtil;

/**
 * Created by vilmar.filho on 12/7/15.
 */
public class RescueHistoryAdapter extends RecyclerView.Adapter<RescueViewHolder> {

    private Context mContext;
    private List<Order> orderList;

    private OnItemClickListener mOnItemClickListener;

    public RescueHistoryAdapter(Context context, List<Order> orderList){
        this.mContext = context;
        this.orderList = orderList;
    }

    @Override
    public RescueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);

        return new RescueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RescueViewHolder holder, final int position) {
        Order order = orderList.get(position);

        String date = DateUtil.getFormatDateToShowJustDate(new Timestamp(order.getSubmittedDate().getTime()));
        holder.date.setText(date);

        holder.orderNumber.setText(order.getId());
        String fullName = (order.getCustomerData() != null) ? order.getCustomerData().getFullName() : "";
        holder.orderReceiver.setText(fullName);

        String cash;

        if (order.getPriceInfo().getCashAmount() > 0) {
            cash = StringUtil.formatPoints(order.getPriceInfo().getAmountInPoints()) + " + " + StringUtil.formatCash(order.getPriceInfo().getCashAmount());
        } else {
            cash = StringUtil.formatPoints(order.getPriceInfo().getAmount());
        }

        holder.orderTotal.setText(cash);

        holder.orderStatus.removeAllViews();
        holder.orderStatus.addView(getViewStatus(order.getStateAsString()));
        holder.orderBorder.setBackgroundColor(ContextCompat.getColor(mContext, getOrderStatus(order.getStateAsString()).getColor()));

        holder.containerOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener != null) mOnItemClickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    private View getViewStatus(String status){

        EnumStatus enumOrder = getOrderStatus(status);

        TextView tv = new TextView(mContext);
        tv.setText(enumOrder.getLabel());
        tv.setTextColor(mContext.getResources().getColor(android.R.color.white));
        tv.setPadding(10, 10, 10, 10);
        tv.setBackgroundDrawable(mContext.getResources().getDrawable(enumOrder.getDrawable()));

        return tv;

    }

    @NonNull
    private EnumStatus getOrderStatus(String status) {
        EnumStatus enumOrder = EnumStatus.PROCESS;

        switch (status){

            case Constants.StatusOrder.PROCESSING_STATUS:
            case Constants.StatusOrder.PENDING_CUSTOMER_RETURN_STATUS:
            case Constants.StatusOrder.PENDING_CUSTOMER_ACTION_STATUS:
            case Constants.StatusOrder.PENDING_MERCHANT_ACTION_STATUS:
                enumOrder = EnumStatus.PROCESS;
                break;


            case Constants.StatusOrder.SUBMITTED_STATUS:
                enumOrder = EnumStatus.SEND;
                break;



            case Constants.StatusOrder.NO_PENDING_ACTION_STATUS:
                enumOrder = EnumStatus.FINALIZED;
                break;


            case Constants.StatusOrder.PENDING_REMOVE_STATUS:
            case Constants.StatusOrder.REMOVED_STATUS:
                enumOrder = EnumStatus.CANCELED;
                break;
        }
        return enumOrder;
    }

    private long getCardCashAmount(Order o){
        if (o.getPaymentGroups() != null && o.getPaymentGroups().size() > 1) {

            for (int i = 0; i < o.getPaymentGroups().size(); i++) {
                if (o.getPaymentGroups().get(i).getCreditCardType() != null && o.getPaymentGroups().get(i).getCreditCardNumber() != null) {
                    return o.getPaymentGroups().get(i).getAmount();
                }
            }
        }

        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
