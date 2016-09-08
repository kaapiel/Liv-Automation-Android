package br.com.pontomobi.livelopontos.ui.myPoints.historicdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import java.util.List;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.TransactionLineItem;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.Util;


/**
 * Created by selemafonso on 05/06/15.
 */
public class HistoricDetailAdapter extends RecyclerView.Adapter<HistoricDetailRowHolder> {

    private Context context;
    private List<TransactionLineItem> transactionLineItems;
    private NumberFormat numberFormat;

    public HistoricDetailAdapter(Context context, List<TransactionLineItem> transactionLineItems) {
        this.context = context;
        this.transactionLineItems = transactionLineItems;
        numberFormat = NumberFormat.getNumberInstance();
    }

    @Override
    public HistoricDetailRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_historic_detail, parent, false);

        return new HistoricDetailRowHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(HistoricDetailRowHolder holder, int position) {
        TransactionLineItem transactionLineItem = transactionLineItems.get(position);

        String charSplit = (transactionLineItem.getTransactionDate().contains("-")) ? "-" : "/";
        String[] date = transactionLineItem.getTransactionDate().split(charSplit);

        holder.getHistoricDetailItemDateNumber().setText(date[0]);
        holder.getHistoricDetailItemDateDesc().setText(DateUtil.getDayOfWeek(transactionLineItem.getTransactionDate()));
        holder.getHistoricDetailItemStore().setText(transactionLineItem.getPartnerName());

        if (!TextUtils.isEmpty(transactionLineItem.getTransactionType())) {
            holder.getHistoricDetailItemType().setVisibility(View.VISIBLE);
            holder.getHistoricDetailItemType().setText(Util.getTypeTransactionName(transactionLineItem.getTransactionType()));
        } else {
            holder.getHistoricDetailItemType().setVisibility(View.GONE);
        }

        if (transactionLineItem.getPoints() > 0) {
            holder.getHistoricDetailItemPoints().setTextColor(context.getResources().getColor(R.color.extract_item_points_color1));
            holder.getHistoricDetailItemPoints().setText(numberFormat.format(transactionLineItem.getPoints()));
        } else {
            holder.getHistoricDetailItemPoints().setTextColor(context.getResources().getColor(R.color.extract_item_points_color2));
            holder.getHistoricDetailItemPoints().setText(numberFormat.format(transactionLineItem.getPoints()));
        }
    }


    @Override
    public int getItemCount() {
        return (null != transactionLineItems ? transactionLineItems.size() : 0);
    }

}
