package br.com.pontomobi.livelopontos.ui.orderHistory;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by vilmar.filho on 12/7/15.
 */
public class RescueViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout containerOrder;
    public TextView date;
    public TextView orderNumber;
    public TextView orderReceiver;
    public TextView orderTotal;
    public RelativeLayout orderStatus;
    public View orderBorder;

    public RescueViewHolder(View itemView) {
        super(itemView);

        containerOrder = (LinearLayout) itemView.findViewById(R.id.container_order);
        date = (TextView) itemView.findViewById(R.id.tv_order_date);
        orderNumber = (TextView) itemView.findViewById(R.id.tv_order_number);
        orderReceiver = (TextView) itemView.findViewById(R.id.tv_order_receiver);
        orderTotal = (TextView) itemView.findViewById(R.id.tv_order_total);
        orderStatus = (RelativeLayout) itemView.findViewById(R.id.containter_status);
        orderBorder = (View) itemView.findViewById(R.id.order_border);

    }

}
