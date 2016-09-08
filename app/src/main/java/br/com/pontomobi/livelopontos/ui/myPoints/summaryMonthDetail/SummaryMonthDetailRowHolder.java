package br.com.pontomobi.livelopontos.ui.myPoints.summaryMonthDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by selemafonso on 05/06/15.
 */
public class SummaryMonthDetailRowHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView summaryMonthDetailItemdateNumber;
    private TextView summaryMonthDetailItemdateDesc;
    private TextView summaryMonthDetailItemStore;
    private TextView summaryMonthDetailItemPoints;


    public SummaryMonthDetailRowHolder(Context context, View view) {
        super(view);
        this.context = context;
        this.summaryMonthDetailItemdateNumber = (TextView) view.findViewById(R.id.summary_month_detail_item_date_number);
        this.summaryMonthDetailItemdateDesc = (TextView) view.findViewById(R.id.summary_month_detail_item_date_desc);
        this.summaryMonthDetailItemStore = (TextView) view.findViewById(R.id.summary_month_detail_item_store);
        this.summaryMonthDetailItemPoints = (TextView) view.findViewById(R.id.summary_month_detail_item_points);
    }

    public TextView getSummaryMonthDetailItemdateNumber() {
        return summaryMonthDetailItemdateNumber;
    }

    public void setSummaryMonthDetailItemdateNumber(TextView summaryMonthDetailItemdateNumber) {
        this.summaryMonthDetailItemdateNumber = summaryMonthDetailItemdateNumber;
    }

    public TextView getSummaryMonthDetailItemdateDesc() {
        return summaryMonthDetailItemdateDesc;
    }

    public void setSummaryMonthDetailItemdateDesc(TextView summaryMonthDetailItemdateDesc) {
        this.summaryMonthDetailItemdateDesc = summaryMonthDetailItemdateDesc;
    }

    public TextView getSummaryMonthDetailItemStore() {
        return summaryMonthDetailItemStore;
    }

    public void setSummaryMonthDetailItemStore(TextView summaryMonthDetailItemStore) {
        this.summaryMonthDetailItemStore = summaryMonthDetailItemStore;
    }

    public TextView getSummaryMonthDetailItemPoints() {
        return summaryMonthDetailItemPoints;
    }

    public void setSummaryMonthDetailItemPoints(TextView summaryMonthDetailItemPoints) {
        this.summaryMonthDetailItemPoints = summaryMonthDetailItemPoints;
    }
}
