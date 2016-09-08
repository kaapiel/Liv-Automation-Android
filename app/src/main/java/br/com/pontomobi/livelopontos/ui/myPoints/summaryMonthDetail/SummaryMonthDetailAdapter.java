package br.com.pontomobi.livelopontos.ui.myPoints.summaryMonthDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import java.util.List;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.TransactionLineItem;
import br.com.pontomobi.livelopontos.util.DateUtil;


/**
 * Created by selemafonso on 05/06/15.
 */
public class SummaryMonthDetailAdapter extends RecyclerView.Adapter<SummaryMonthDetailRowHolder> {

    private Context context;
    private List<TransactionLineItem> transactionLineItemList;
    private NumberFormat numberFormat;

    public SummaryMonthDetailAdapter(Context context, List<TransactionLineItem> transactionLineItemList) {
        this.context = context;
        this.transactionLineItemList = transactionLineItemList;
        numberFormat = NumberFormat.getNumberInstance();
    }

    @Override
    public SummaryMonthDetailRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_summary_month_detail, parent, false);

        return new SummaryMonthDetailRowHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(SummaryMonthDetailRowHolder holder, int position) {
        TransactionLineItem transactionLineItem = transactionLineItemList.get(position);

        String charSplit = (transactionLineItem.getTransactionDate().contains("-")) ? "-" : "/";
        String[] date = transactionLineItem.getTransactionDate().split(charSplit);

        holder.getSummaryMonthDetailItemdateNumber().setText(date[0]);
        holder.getSummaryMonthDetailItemdateDesc().setText(DateUtil.getDayOfWeek(transactionLineItem.getTransactionDate()));
        holder.getSummaryMonthDetailItemStore().setText(transactionLineItem.getPartnerName());
        holder.getSummaryMonthDetailItemPoints().setText(numberFormat.format(transactionLineItem.getPoints()));
    }

    @Override
    public int getItemCount() {
        return (null != transactionLineItemList ? transactionLineItemList.size() : 0);
    }

}
