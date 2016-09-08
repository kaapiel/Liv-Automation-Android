package br.com.pontomobi.livelopontos.ui.cart.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by vilmar.filho on 5/19/16.
 */
public class HeaderViewHolder extends RecyclerView.ViewHolder {

    TextView amountPoints;
    TextView updateTime;

    public HeaderViewHolder(View itemView) {
        super(itemView);

        amountPoints = (TextView) itemView.findViewById(R.id.amount_points);
        updateTime = (TextView) itemView.findViewById(R.id.update_time);
    }
}
