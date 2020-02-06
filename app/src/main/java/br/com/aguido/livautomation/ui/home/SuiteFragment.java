package br.com.aguido.livautomation.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import br.com.aguido.livautomation.LivAutomationFragment;
import br.com.aguido.livautomation.R;
import butterknife.ButterKnife;

/**
 * Created by vilmar.filho on 12/4/15.
 */
public class SuiteFragment extends LivAutomationFragment implements OnChartValueSelectedListener {

    private HorizontalBarChart mHchart;
    private PieChart mPchart;
    private String pacote, qtdTestesOK, qtdTestesNOK;
    private ArrayList<Teste> testes;
    private Typeface tf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, view);
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MuseoSans_500.otf");

        getBundles();

        ListView lv = (ListView) view.findViewById(R.id.listView1);
        ArrayList<ChartItem> list = new ArrayList<ChartItem>();

        list.add(new PieChartItem(generatePieData(qtdTestesOK, qtdTestesNOK, pacote), getContext()));
        list.add(new HorizontalBarChartItem(null, getContext(), testes));

        ChartDataAdapter cda = new ChartDataAdapter(getContext(), list);
        lv.setAdapter(cda);

        return view;
    }

    private void getBundles() {
        Bundle args = getArguments();

        if(args.get("pacote_regressivo") != null) {
            pacote = (String) args.get("pacote_regressivo");
            qtdTestesOK = (String) args.get("qtdTestesOK_regressivo");
            qtdTestesNOK = (String) args.get("qtdTestesNOK_regressivo");
            testes = (ArrayList<Teste>) args.get("testes_regressivo");
            return;
        }

        if(args.get("pacote_regressivoneg") != null) {
            pacote = (String) args.get("pacote_regressivoneg");
            qtdTestesOK = (String) args.get("qtdTestesOK_regressivoneg");
            qtdTestesNOK = (String) args.get("qtdTestesNOK_regressivoneg");
            testes = (ArrayList<Teste>) args.get("testes_regressivoneg");
            return;
        }

        if(args.get("pacote_smoke") != null) {
            pacote = (String) args.get("pacote_smoke");
            qtdTestesOK = (String) args.get("qtdTestesOK_smoke");
            qtdTestesNOK = (String) args.get("qtdTestesNOK_smoke");
            testes = (ArrayList<Teste>) args.get("testes_smoke");
            return;
        }

        if(args.get("pacote_bvt") != null) {
            pacote = (String) args.get("pacote_bvt");
            qtdTestesOK = (String) args.get("qtdTestesOK_bvt");
            qtdTestesNOK = (String) args.get("qtdTestesNOK_bvt");
            testes = (ArrayList<Teste>) args.get("testes_bvt");
            return;
        }

        if(args.get("pacote_servicos") != null) {
            pacote = (String) args.get("pacote_servicos");
            qtdTestesOK = (String) args.get("qtdTestesOK_servicos");
            qtdTestesNOK = (String) args.get("qtdTestesNOK_servicos");
            testes = (ArrayList<Teste>) args.get("testes_servicos");
            return;
        }

        if(args.get("pacote_performance") != null) {
            pacote = (String) args.get("pacote_performance");
            qtdTestesOK = (String) args.get("qtdTestesOK_performance");
            qtdTestesNOK = (String) args.get("qtdTestesNOK_performance");
            testes = (ArrayList<Teste>) args.get("testes_performance");
            return;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        setMenuSelected(R.id.menu_home);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            return getItem(position).getItemType();
        }

        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }

}
