package br.com.pontomobi.livelopontos.ui.myPoints.summaryMonth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by selemafonso on 05/06/15.
 */
public class SummaryMonthRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RelativeLayout summaryMonthItemContent;
    private TextView summaryMonthItemMonth;
    private TextView summaryMonthItemPoints;
    private SummaryMonthActivity.OnSummaryMonthClickListener onSummaryMonthClickListener;
    private Context context;

    public SummaryMonthRowHolder(Context context, View view, SummaryMonthActivity.OnSummaryMonthClickListener onSummaryMonthClickListener) {
        super(view);
        this.context = context;
        this.onSummaryMonthClickListener = onSummaryMonthClickListener;

        this.summaryMonthItemContent = (RelativeLayout) view.findViewById(R.id.summary_month_item_content);
        this.summaryMonthItemMonth = (TextView) view.findViewById(R.id.summary_month_item_month);
        this.summaryMonthItemPoints = (TextView) view.findViewById(R.id.summary_month_item_points);

        summaryMonthItemContent.setOnClickListener(this);
    }

    public RelativeLayout getSummaryMonthItemContent() {
        return summaryMonthItemContent;
    }

    public void setSummaryMonthItemContent(RelativeLayout summaryMonthItemContent) {
        this.summaryMonthItemContent = summaryMonthItemContent;
    }

    public TextView getSummaryMonthItemMonth() {
        return summaryMonthItemMonth;
    }

    public void setSummaryMonthItemMonth(TextView summaryMonthItemMonth) {
        this.summaryMonthItemMonth = summaryMonthItemMonth;
    }

    public TextView getSummaryMonthItemPoints() {
        return summaryMonthItemPoints;
    }

    public void setSummaryMonthItemPoints(TextView summaryMonthItemPoints) {
        this.summaryMonthItemPoints = summaryMonthItemPoints;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.summary_month_item_content:
                onSummaryMonthClickListener.onItemClick(getAdapterPosition());
                break;
        }
    }
}
