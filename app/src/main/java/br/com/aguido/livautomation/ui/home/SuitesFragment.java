package br.com.aguido.livautomation.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import br.com.aguido.livautomation.LivAutomationApp;
import br.com.aguido.livautomation.LivAutomationFragment;
import br.com.aguido.livautomation.R;
import br.com.aguido.livautomation.model.Summary;
import br.com.aguido.livautomation.ui.dialog.DialogCustomAlert;
import br.com.aguido.livautomation.ui.login.LoginActivity;
import br.com.aguido.livautomation.util.LoginUtil;
import butterknife.ButterKnife;

/**
 * Created by vilmar.filho on 12/4/15.
 */
public class SuitesFragment extends LivAutomationFragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {


    private Summary currentSummary;
    private boolean loadigDataFromChache = false;
    private boolean errorShow = false;
    private PieChart mPchart;
    private BarChart mBchart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, view);

        mPchart = (PieChart) view.findViewById(R.id.pieChart1);
        //mChart.setDescription("");

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MuseoSans_500.otf");

        mPchart.setCenterTextTypeface(tf);
        mPchart.setCenterText(generateCenterText());
        mPchart.setCenterTextSize(10f);
        mPchart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        mPchart.setHoleRadius(45f);
        mPchart.setTransparentCircleRadius(50f);

        mPchart.setData(generatePieData("teste", "teste", "pacoteTeste"));

        // mChart.setDrawLegend(false);

        return view;
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

    /*@OnClick(R.id.container_my_points)
    public void openMyPoints(){
        LivAutomationApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_HOME,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_HOME,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_GENERATE_CODE,
                ""
        );

        startActivity(new Intent(getContext(), MyPointsActivity.class));
    }*/



    private void sessionExpired() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();
        dialogCustom.showCustomDialog(getActivity(), LivAutomationApp.getInstance().getExpiredSession(), false,
                new DialogCustomAlert.AlertDialogClickListener() {
                    @Override
                    public void onPositiveClick() {
                        LoginUtil.clearLogin(getContext());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onNegativeClick() {
                        Log.d("DIALOG", "NEGATIVE");
                    }

                    @Override
                    public void onBackPressedInDialog() {

                    }
                });
    }


    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Revenues\nQuarters 2015");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
