package br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import br.com.pontomobi.livelopontos.util.DateUtil;

/**
 * Created by selem.gomes on 13/11/15.
 */
public class PointsExpiredResponse implements Serializable {
    private int pointsToExp;
    private boolean emptyList;
    private int pointsSum;
    private ListHistogram histogramViewMap;

    public int getPointsToExp() {
        return pointsToExp;
    }

    public void setPointsToExp(int pointsToExp) {
        this.pointsToExp = pointsToExp;
    }

    public boolean isEmptyList() {
        return emptyList;
    }

    public void setEmptyList(boolean emptyList) {
        this.emptyList = emptyList;
    }

    public int getPointsSum() {
        return pointsSum;
    }

    public void setPointsSum(int pointsSum) {
        this.pointsSum = pointsSum;
    }

    public ListHistogram getHistogramViewMap() {
        return histogramViewMap;
    }

    public void setHistogramViewMap(ListHistogram histogramViewMap) {
        this.histogramViewMap = histogramViewMap;
    }

    public ListHistogram filterByDate(int months) {
        ListHistogram newListHistogram = new ListHistogram();
        Date dateMax = DateUtil.getDateAfterMonths(months);

        for(Histogram histogram : histogramViewMap) {
            Date dateHistogram = DateUtil.getDateInMonthAndYear(DateUtil.getMonthNumber(histogram.getMonth()), Integer.parseInt(histogram.getYear()));

            if (months == 0) {
                newListHistogram.add(histogram);
                continue;
            }

            if (dateHistogram.before(dateMax)) {
                newListHistogram.add(histogram);
            }
        }
        return newListHistogram;
    }

    public void orderListHistogram() {
        Collections.sort(histogramViewMap, new Comparator<Histogram>() {
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
    }
}
