package br.com.pontomobi.livelopontos.ui.myPoints.summaryExpired;

import android.content.Context;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.Histogram;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.ListHistogram;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.PointsExpiredResponse;
import br.com.pontomobi.livelopontos.ui.myPoints.summary.SummaryBusiness;
import br.com.pontomobi.livelopontos.util.DateUtil;


/**
 * Created by selem.gomes on 09/11/15.
 */
public class SummaryExpiredBusiness {
    private Context context;
    private SummaryExpiredActivity.OnSummaryExpiredBusiness onSummaryExpiredBusiness;

    public SummaryExpiredBusiness(Context context, SummaryExpiredActivity.OnSummaryExpiredBusiness onSummaryExpiredBusiness) {
        this.context = context;
        this.onSummaryExpiredBusiness = onSummaryExpiredBusiness;
    }


    public void callServiceGetPointsExpired() {
        LiveloRepository.with(context).getPointsExpired(context, getOnServicePointsExpiredListener());
    }


    private SummaryBusiness.OnServicePointsExpiredListener getOnServicePointsExpiredListener() {
        SummaryBusiness.OnServicePointsExpiredListener onServicePointsExpiredListener = new SummaryBusiness.OnServicePointsExpiredListener() {
            @Override
            public void onGetPointsExpiredSuccess(PointsExpiredResponse response) {
                orderListHistogram(response.getHistogramViewMap());
                onSummaryExpiredBusiness.onGetExpiredBusinessSuccess(response);
            }

            @Override
            public void onGetPointsExpiredFail(LiveloException exception) {
                onSummaryExpiredBusiness.onGetExpiredBusinessFail(exception);
            }
        };

        return onServicePointsExpiredListener;
    }

    public void orderListHistogram(ListHistogram list) {
        Collections.sort(list, new Comparator<Histogram>() {
            public int compare(Histogram p1, Histogram p2) {

                Date mp1 = DateUtil.getFirstDateInMonthReturnDate(DateUtil.getMonthNumber(p1.getMonth()), Integer.parseInt(p1.getYear()));
                Date mp2 = DateUtil.getFirstDateInMonthReturnDate(DateUtil.getMonthNumber(p2.getMonth()), Integer.parseInt(p2.getYear()));
                if (mp1.getTime() < mp2.getTime())
                    return -1;
                else if (mp1.getTime() > mp2.getTime())
                    return 1;

                return 0;
            }
        });
//        Collections.reverse(list);
    }
}
