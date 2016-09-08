package br.com.pontomobi.livelopontos.ui.myPoints.summaryMonth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.PointsPerMonth;
import br.com.pontomobi.livelopontos.util.DateUtil;


/**
 * Created by selemafonso on 05/06/15.
 */
public class SummaryMonthAdapter extends RecyclerView.Adapter<SummaryMonthRowHolder> {

    private Context context;
    private SummaryMonthActivity.OnSummaryMonthClickListener onSummaryMonthClickListener;
    private List<PointsPerMonth> pointsPerMonthList;
    private NumberFormat numberFormat;

    private int summaryType;

    public SummaryMonthAdapter(Context context, List<PointsPerMonth> pointsPerMonthList, int summaryType, SummaryMonthActivity.OnSummaryMonthClickListener onSummaryMonthClickListener) {
        this.context = context;
        this.pointsPerMonthList = pointsPerMonthList;
        this.summaryType = summaryType;
        this.onSummaryMonthClickListener = onSummaryMonthClickListener;
        numberFormat = NumberFormat.getNumberInstance();
    }

    @Override
    public SummaryMonthRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_summary_month, parent, false);

        return new SummaryMonthRowHolder(context, itemView, onSummaryMonthClickListener);
    }

    @Override
    public void onBindViewHolder(SummaryMonthRowHolder holder, int position) {
        PointsPerMonth pointsPerMonth = pointsPerMonthList.get(position);

        holder.getSummaryMonthItemMonth().setText(DateUtil.getMonthName(pointsPerMonth.getMonth()) + "/" + pointsPerMonth.getYear());

        int points = pointsPerMonth.getPoints();
        if(summaryType == Constants.SUMMARY_RESCUED)
            points = Math.abs(points);

        holder.getSummaryMonthItemPoints().setText(numberFormat.format(points));
    }

    @Override
    public int getItemCount() {
        return (null != pointsPerMonthList ? pointsPerMonthList.size() : 0);
    }

}
