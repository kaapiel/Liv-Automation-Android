package br.com.pontomobi.livelowear.ui.mypointsbalance;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableHeaderTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.otto.Subscribe;

import br.com.pontomobi.livelowear.LiveloWearApp;
import br.com.pontomobi.livelowear.R;
import br.com.pontomobi.livelowear.service.model.WearPoints;
import br.com.pontomobi.livelowear.utils.DateUtil;

/**
 * Created by selem.gomes on 14/01/16.
 */
public class MyPointsBalanceFragment extends Fragment {
    private WearableHeaderTextView myPointsBalanceTitle;
    private WearableHeaderTextView myPointsBalancePoints;
    private WearableHeaderTextView myPointsBalanceLastUpdate;
    private LinearLayout myPointsContent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_my_points_balance, container, false);
        LiveloWearApp.getInstance().getBus().register(this);
        final WatchViewStub stub = (WatchViewStub) view.findViewById(R.id.my_points_balance_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                myPointsBalanceTitle = (WearableHeaderTextView) stub.findViewById(R.id.my_points_balance_title);
                myPointsBalancePoints = (WearableHeaderTextView) stub.findViewById(R.id.my_points_balance_points);
                myPointsBalanceLastUpdate = (WearableHeaderTextView) stub.findViewById(R.id.my_points_balance_last_update);
                myPointsContent = (LinearLayout) stub.findViewById(R.id.my_points_content);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onResponseAmountPointsBalance(LiveloWearApp.getInstance().getWearPoints());
    }

    @Override
    public void onDestroy() {
        LiveloWearApp.getInstance().getBus().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onResponseAmountPointsBalance(final WearPoints wearPoints) {
        if (wearPoints == null) {
            return;
        }

        if (myPointsContent == null) {
            return;
        }

        myPointsContent.post(new Runnable() {
            @Override
            public void run() {

                String points = (TextUtils.isEmpty(wearPoints.getAmountPoints())) ? "0" : wearPoints.getAmountPoints();

                myPointsBalancePoints.setText(points);
                myPointsBalanceLastUpdate.setText(DateUtil.getLastUpdate(wearPoints.getLastUpdateData()));
            }
        });

    }
}
