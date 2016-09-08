package br.com.pontomobi.livelowear.ui.mypointsaccumulated;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableHeaderTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.com.pontomobi.livelowear.LiveloWearApp;
import br.com.pontomobi.livelowear.R;
import br.com.pontomobi.livelowear.service.model.WearPoints;
import br.com.pontomobi.livelowear.utils.DateUtil;

/**
 * Created by selem.gomes on 14/01/16.
 */
public class MyPointsAccumulatedFragment extends Fragment {
    private WearableHeaderTextView myPointsAccumulatedPoints;
    private WearableHeaderTextView myPointsAccumulatedLastUpdate;
    private LinearLayout myPointsAccumulatedContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_my_points_accumulated, container, false);

        final WatchViewStub stub = (WatchViewStub) view.findViewById(R.id.my_points_accumulated_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                myPointsAccumulatedPoints = (WearableHeaderTextView) stub.findViewById(R.id.my_points_accumulated_points);
                myPointsAccumulatedLastUpdate = (WearableHeaderTextView) stub.findViewById(R.id.my_points_accumulated_last_update);
                myPointsAccumulatedContent = (LinearLayout) stub.findViewById(R.id.my_points_accumulated_content);

                setAmountPointsAccumulated();
            }
        });

        return view;
    }

    public void setAmountPointsAccumulated() {
        WearPoints wearPoints = LiveloWearApp.getInstance().getWearPoints();
        if (wearPoints == null) {
            return;
        }

        String points;

        if(wearPoints.getAmountPointsAccumulated() == null || wearPoints.getAmountPointsAccumulated().equals("0")){
            points = "Você ainda não\npossui pontos.";
            myPointsAccumulatedPoints.setTextSize(12);
        } else {
            points = wearPoints.getAmountPointsAccumulated();
            //myPointsAccumulatedPoints.setTextSize(getResources().getDimension(R.dimen.current_balance_points_text_size));
        }

        myPointsAccumulatedPoints.setText(points);

        myPointsAccumulatedLastUpdate.setText(DateUtil.getLastUpdate(wearPoints.getLastUpdateData()));
    }


}
