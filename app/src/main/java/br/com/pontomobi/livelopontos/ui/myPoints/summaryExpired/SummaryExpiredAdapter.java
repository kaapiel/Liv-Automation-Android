package br.com.pontomobi.livelopontos.ui.myPoints.summaryExpired;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.Histogram;


/**
 * Created by selemafonso on 05/06/15.
 */
public class SummaryExpiredAdapter extends RecyclerView.Adapter<SummaryExpiredRowHolder> {

    private Context context;
    private List<Histogram> histogramList;
    private NumberFormat numberFormat;

    public SummaryExpiredAdapter(Context context, List<Histogram> histogramList) {
        this.context = context;
        this.histogramList = histogramList;
        numberFormat = NumberFormat.getNumberInstance();
    }

    @Override
    public SummaryExpiredRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_summary_expired, parent, false);

        return new SummaryExpiredRowHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(SummaryExpiredRowHolder holder, int position) {
        Histogram histogram = histogramList.get(position);

        if (isPointNotExpire(histogram.getYear())) {
            holder.getSummaryExpiredItemMonth().setText(context.getString(R.string.point_not_expire));
        } else {
            holder.getSummaryExpiredItemMonth().setText(histogram.getMonthName().substring(0, 3).toUpperCase() + "/" + histogram.getYear());
        }

        holder.getSummaryExpiredItemPoints().setText(numberFormat.format(histogram.getValue()));
    }

    @Override
    public int getItemCount() {
        return (null != histogramList ? histogramList.size() : 0);
    }

    /*
     *JIRA CLIENTE - LIV-3073
     *Regra implementada conforme solicitação do cliente.
     *
     *A regra é, qualquer data com mais de 90 anos deve
     *mostrar como "Não Expira"
     */
    private boolean isPointNotExpire(String year) {
        boolean expire = false;

        try {
            int yearCurrent = Calendar.getInstance().get(Calendar.YEAR);
            int yearPoint = Integer.parseInt(year);

            if((yearPoint - yearCurrent) > 90)
                expire = true;

        } catch (Exception e) {
            expire = false;
        }
        return expire;
    }
}
