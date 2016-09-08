package br.com.pontomobi.livelopontos.ui.myPoints.summaryExpired;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.ui.myPoints.summaryMonth.SummaryMonthActivity;

/**
 * Created by selemafonso on 05/06/15.
 */
public class SummaryExpiredRowHolder extends RecyclerView.ViewHolder {

    private TextView summaryExpiredItemMonth;
    private TextView summaryExpiredItemPoints;
    private Context context;

    public SummaryExpiredRowHolder(Context context, View view) {
        super(view);
        this.context = context;

        this.summaryExpiredItemMonth = (TextView) view.findViewById(R.id.summary_expired_item_month);
        this.summaryExpiredItemPoints = (TextView) view.findViewById(R.id.summary_expired_item_points);
    }

    public TextView getSummaryExpiredItemMonth() {
        return summaryExpiredItemMonth;
    }

    public void setSummaryExpiredItemMonth(TextView summaryExpiredItemMonth) {
        this.summaryExpiredItemMonth = summaryExpiredItemMonth;
    }

    public TextView getSummaryExpiredItemPoints() {
        return summaryExpiredItemPoints;
    }

    public void setSummaryExpiredItemPoints(TextView summaryExpiredItemPoints) {
        this.summaryExpiredItemPoints = summaryExpiredItemPoints;
    }
}
