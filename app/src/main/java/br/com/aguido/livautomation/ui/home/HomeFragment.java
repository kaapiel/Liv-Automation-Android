package br.com.aguido.livautomation.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.ArrayList;

import br.com.aguido.livautomation.LivAutomationFragment;
import br.com.aguido.livautomation.R;
import butterknife.ButterKnife;

/**
 * Created by vilmar.filho on 12/4/15.
 */
public class HomeFragment extends LivAutomationFragment implements OnChartGestureListener {

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
        //mChart.setDescription("");
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
