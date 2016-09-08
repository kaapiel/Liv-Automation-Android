package br.com.pontomobi.livelopontos.ui.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.model.Summary;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.myPoints.summary.SummaryBusiness;
import br.com.pontomobi.livelopontos.ui.myPoints.summary.SummaryFragment;
import br.com.pontomobi.livelopontos.ui.widget.LastUpdate;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import br.com.pontomobi.livelopontos.util.Util;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vilmar.filho on 12/4/15.
 */
public class HomeFragment extends LiveloPontoFragment implements OnChartGestureListener {

    private BarChart mChart;
    private Typeface tf;
    private ArrayList<String> percents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_simple_bar, container, false);
        ButterKnife.bind(this, view);

        getBundles();

        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MuseoSans_500.otf");
        // create a new chart object
        mChart = new BarChart(getContext());
        mChart.setDescription("");
        mChart.setOnChartGestureListener(this);

        MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv);

        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        mChart.setData(generateBarData(1, percents, 6));

        Legend l = mChart.getLegend();
        l.setTypeface(tf);

        mChart.animateY(2500);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);

        // programatically add the chart
        FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);
        parent.addView(mChart);

        return view;
    }

    private void getBundles() {
        Bundle args = getArguments();
        percents = new ArrayList<>();
        percents.add((String) args.get("percentual_regressivo"));
        percents.add((String) args.get("percentual_regressivoneg"));
        percents.add((String) args.get("percentual_smoke"));
        percents.add((String) args.get("percentual_bvt"));
        percents.add((String) args.get("percentual_servicos"));
        percents.add((String) args.get("percentual_performance"));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        //callServices();
        setMenuSelected(R.id.menu_home);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }
}
