package br.com.pontomobi.livelowear.ui.graphicpoints;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableHeaderTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.NumberFormat;

import br.com.pontomobi.livelowear.LiveloWearApp;
import br.com.pontomobi.livelowear.R;
import br.com.pontomobi.livelowear.service.model.WearPoints;
import br.com.pontomobi.livelowear.service.model.WearPointsPerMonth;
import br.com.pontomobi.livelowear.ui.widget.GraphicBar;
import br.com.pontomobi.livelowear.utils.DateUtil;

/**
 * Created by selem.gomes on 14/01/16.
 */
public class GraphicPointsFragment extends Fragment {

    private GraphicBar firstBar;
    private GraphicBar secondBar;
    private GraphicBar thirdBar;
    private NumberFormat numberFormat;
    private WearableHeaderTextView graphicPointsEmpty;

    private LinearLayout mContainerInfoPoints;
    private RelativeLayout mContainerEmptyState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_graphic_points, container, false);
        numberFormat = NumberFormat.getNumberInstance();

        final WatchViewStub stub = (WatchViewStub) view.findViewById(R.id.graphic_points_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                firstBar = (GraphicBar) stub.findViewById(R.id.graphic_points_first);
                secondBar = (GraphicBar) stub.findViewById(R.id.graphic_points_second);
                thirdBar = (GraphicBar) stub.findViewById(R.id.graphic_points_third);
                graphicPointsEmpty = (WearableHeaderTextView) stub.findViewById(R.id.graphic_points_empty);

                mContainerInfoPoints = (LinearLayout) stub.findViewById(R.id.container_info_points);
                mContainerEmptyState = (RelativeLayout) stub.findViewById(R.id.container_empty_state);

                setInfoGraphic(LiveloWearApp.getInstance().getWearPoints());
            }
        });

        return view;
    }

    private void setInfoGraphic(WearPoints wearPoints) {
        if (wearPoints == null) {
            //if(graphicPointsEmpty != null) graphicPointsEmpty.setVisibility(View.VISIBLE);
            if(mContainerInfoPoints != null) mContainerInfoPoints.setVisibility(View.GONE);
            if(mContainerEmptyState != null) mContainerEmptyState.setVisibility(View.VISIBLE);
            return;
        }

        if (wearPoints.getWearPointsPerMonthList() == null || wearPoints.getWearPointsPerMonthList().size() == 0) {
            //if(graphicPointsEmpty != null) graphicPointsEmpty.setVisibility(View.VISIBLE);
            if(mContainerInfoPoints != null) mContainerInfoPoints.setVisibility(View.GONE);
            if(mContainerEmptyState != null) mContainerEmptyState.setVisibility(View.VISIBLE);
            return;
        }

        //WearPointsPerMonth first = new WearPointsPerMonth();
        //WearPointsPerMonth second = new WearPointsPerMonth();
        //WearPointsPerMonth third = new WearPointsPerMonth();

        boolean firstBarUse = false;
        boolean secondBarUse = false;
        boolean thirdBarUse = false;

        int maxPoint = getMaxPoints(wearPoints);

        for (int i = wearPoints.getWearPointsPerMonthList().size() - 1; i >= 0; i--) {

            WearPointsPerMonth wearPointsPerMonth = wearPoints.getWearPointsPerMonthList().get(i);

            if (wearPointsPerMonth == null) {
                continue;
            }

            String month = DateUtil.getMonthName(wearPointsPerMonth.getMonth()) + "/" + wearPointsPerMonth.getYear();

            if (!firstBarUse) {
                firstBar.setVisibility(View.VISIBLE);
                firstBar.getGraphicBarItemMonth().setText(month);
                firstBar.getGraphicBarItemAmount().setText(numberFormat.format(wearPointsPerMonth.getPoints()));
                firstBar.setPoints(wearPointsPerMonth.getPoints());
                firstBar.width(maxPoint);
                firstBarUse = true;
                continue;
            }

            if (!secondBarUse) {
                secondBar.setVisibility(View.VISIBLE);
                secondBar.getGraphicBarItemMonth().setText(month);
                secondBar.getGraphicBarItemAmount().setText(numberFormat.format(wearPointsPerMonth.getPoints()));
                secondBar.setPoints(wearPointsPerMonth.getPoints());
                secondBar.width(maxPoint);
                secondBarUse = true;
                continue;
            }

            if (!thirdBarUse) {
                thirdBar.setVisibility(View.VISIBLE);
                thirdBar.getGraphicBarItemMonth().setText(month);
                thirdBar.getGraphicBarItemAmount().setText(numberFormat.format(wearPointsPerMonth.getPoints()));
                thirdBar.setPoints(wearPointsPerMonth.getPoints());
                thirdBar.width(maxPoint);
                thirdBarUse = true;
                continue;
            }
        }
    }

    private int getMaxPoints(WearPoints wearPoints) {
        int maxPoints = 0;

        for (int i = wearPoints.getWearPointsPerMonthList().size() - 1; i >= 0; i--) {
            WearPointsPerMonth wearPointsPerMonth = wearPoints.getWearPointsPerMonthList().get(i);
            if (maxPoints <= wearPointsPerMonth.getPoints()) {
                maxPoints = wearPointsPerMonth.getPoints();
            }
        }

        return maxPoints;
    }

}
