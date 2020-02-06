package br.com.aguido.livautomation.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import br.com.aguido.livautomation.R;
import br.com.aguido.livautomation.model.Alert;
import br.com.aguido.livautomation.ui.dialog.DialogCustomAlert;

public class HorizontalBarChartItem extends ChartItem implements OnChartValueSelectedListener {

    private final Context context;
    private Typeface mTf;
    private SpannableString mCenterText;
    private ArrayList<Teste> testes;

    public HorizontalBarChartItem(ChartData<?> cd, Context c, ArrayList<Teste> testes) {
        super(cd);
        this.context = c;
        this.testes = testes;
        mTf = Typeface.createFromAsset(c.getAssets(), "fonts/MuseoSans_500.otf");

    }

    @Override
    public int getItemType() {
        return TYPE_HORIZONTALBARCHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_horizontalbarchart, null);
            holder.chart = (HorizontalBarChart) convertView.findViewById(R.id.horizontalbarchart_list_item);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.chart.setOnChartValueSelectedListener(this);
        holder.chart.setDrawBarShadow(false);
        holder.chart.setDrawValueAboveBar(true);
        ////holder.chart.setDescription("");
        holder.chart.setMaxVisibleValueCount(testes.size());
        holder.chart.setPinchZoom(false);
        holder.chart.setDrawGridBackground(false);

        XAxis xl = holder.chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(mTf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(20);

        YAxis yl = holder.chart.getAxisLeft();
        yl.setTypeface(mTf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0);
        yl.setGranularity(0);

        YAxis yr = holder.chart.getAxisRight();
        yr.setTypeface(mTf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();

        for (int i = 0; i < testes.size(); i++) {

            try {

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
                Date dataInicio = formatter.parse(testes.get(i).getHoraInicio());
                Date dataFim = formatter.parse(testes.get(i).getHoraFim());
                yVals1.add(new BarEntry(i * 10f, dataFim.getTime() - dataInicio.getTime()));

            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
        }

        BarDataSet set1;

        if (holder.chart.getData() != null && holder.chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) holder.chart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);

            holder.chart.getData().notifyDataChanged();
            holder.chart.notifyDataSetChanged();
        } else {

            set1 = new BarDataSet(yVals1, "OK");
            set1.setColor(c.getResources().getColor(R.color.verde));
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTf);
            data.setBarWidth(9f);
            holder.chart.setData(data);
        }

        int[] colors = {R.color.verde, R.color.vermelho};
        set1.setColors(colors);

        holder.chart.setFitBars(true);
        holder.chart.animateY(2500);

        Legend l = holder.chart.getLegend();
        //l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        return convertView;
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        for (Teste t : testes) {

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            Date dataInicio = null;
            Date dataFim = null;
            try {
                dataInicio = formatter.parse(t.getHoraInicio());
                dataFim = formatter.parse(t.getHoraFim());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            long tempoTeste = dataFim.getTime() - dataInicio.getTime();

            if(e.getY() == (float) tempoTeste){

                final DialogCustomAlert dialogCustom = new DialogCustomAlert();

                Alert alert = new Alert();
                alert.setTitle("Nome do teste:\n"+t.getNome());
                alert.setDescription("\n\nTempo do teste: "+tempoTeste/1000+" segundos\n\n"
                        +"Resultado: "+t.getResultado()+"\n\n"
                        +"Trace: "+t.getMensagem());
                alert.setImage(context.getResources().getResourceEntryName(R.drawable.erro_site));
                alert.setPositiveButton("OK");

                    dialogCustom.showCustomDialog(context, alert, true,
                            new DialogCustomAlert.AlertDialogClickListener() {
                                @Override
                                public void onPositiveClick() {

                                }

                                @Override
                                public void onNegativeClick() {

                                }

                                @Override
                                public void onBackPressedInDialog() {

                                }
                            });
            }
        }

    }

    @Override
    public void onNothingSelected() {

    }

    private static class ViewHolder {
        HorizontalBarChart chart;
    }

}
