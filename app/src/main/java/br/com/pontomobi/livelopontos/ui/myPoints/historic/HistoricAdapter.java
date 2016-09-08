package br.com.pontomobi.livelopontos.ui.myPoints.historic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import java.util.List;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.HistoricExtract;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.Util;


/**
 * Created by selemafonso on 05/06/15.
 */
public class HistoricAdapter extends RecyclerView.Adapter<HistoricRowHolder> {

    private Context context;
    private List<HistoricExtract> historicExtractsList;
    private HistoricActivity.OnHistoricClickListener onHistoricClickListener;
    private int filterType;
    private NumberFormat numberFormat;

    public HistoricAdapter(Context context, List<HistoricExtract> historicExtractsList, HistoricActivity.OnHistoricClickListener onHistoricClickListener,
                           int filterType) {
        this.context = context;
        this.historicExtractsList = historicExtractsList;
        this.onHistoricClickListener = onHistoricClickListener;
        this.filterType = filterType;
        numberFormat = NumberFormat.getNumberInstance();
    }

    @Override
    public HistoricRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_historic, parent, false);

        return new HistoricRowHolder(context, itemView, onHistoricClickListener);
    }

    @Override
    public void onBindViewHolder(HistoricRowHolder holder, int position) {
        HistoricExtract historicExtract = historicExtractsList.get(position);


        if(filterType == HistoricActivity.FILTER_BY_PARTNER) {
            changeHolderPartner(holder);
        } else {
            changeHolderPoints(holder, position);
        }
        holder.getHistoricItemStore().setText(historicExtract.getPartnerName());

        if (historicExtract.getPoints() > 0) {
            holder.getHistoricItemPoints().setTextColor(context.getResources().getColor(R.color.extract_item_points_color1));
            holder.getHistoricItemPoints().setText(numberFormat.format(historicExtract.getPoints()));
        } else {
            holder.getHistoricItemPoints().setTextColor(context.getResources().getColor(R.color.extract_item_points_color2));
            holder.getHistoricItemPoints().setText(numberFormat.format(historicExtract.getPoints()));
        }
    }

    private void changeHolderPartner(HistoricRowHolder holder) {
        holder.getHistoricArrow().setVisibility(View.VISIBLE);

        holder.getHistoricDateContent().setVisibility(View.GONE);
        holder.getHistoricItemType().setVisibility(View.INVISIBLE);
        holder.getHistoricMonthYear().setVisibility(View.GONE);
    }

    private void changeHolderPoints(HistoricRowHolder holder, int position) {
        HistoricExtract historicExtract = historicExtractsList.get(position);
        holder.getHistoricArrow().setVisibility(View.GONE);

        if(position > 0 && historicExtract.getMonthYear().equals(historicExtractsList.get(position-1).getMonthYear())) {
            holder.getHistoricMonthYear().setVisibility(View.GONE);
        } else {
            holder.getHistoricMonthYear().setText(historicExtract.getMonthYear());
            holder.getHistoricMonthYear().setVisibility(View.VISIBLE);
        }

        String charSplit = (historicExtract.getTransactionDate().contains("-")) ? "-" : "/";
        String[] date = historicExtract.getTransactionDate().split(charSplit);

        holder.getHistoricItemdateNumber().setText(date[0]);
        holder.getHistoricItemdateDesc().setText(DateUtil.getDayOfWeek(historicExtract.getTransactionDate()));

        if (!TextUtils.isEmpty(historicExtract.getTransactionType())) {
            holder.getHistoricItemType().setVisibility(View.VISIBLE);
            holder.getHistoricItemType().setText(Util.getTypeTransactionName(historicExtract.getTransactionType()));
        } else {
            holder.getHistoricItemType().setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return (null != historicExtractsList ? historicExtractsList.size() : 0);
    }

}
