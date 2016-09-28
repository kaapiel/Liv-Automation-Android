package br.com.pontomobi.livelopontos.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.ArrayList;

import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCartConfirmation;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vilmar.filho on 12/4/15.
 */
public class ExecucaoTestesFragment extends LiveloPontoFragment implements OnChartGestureListener {

    @Bind(R.id.botaoExecutarTestes)
    AppCompatButton botaoEnviar;

    @Bind(R.id.spinner_ambiente)
    AppCompatSpinner spinnerAmbiente;

    @Bind(R.id.spinner_maquina)
    AppCompatSpinner spinnerMaquina;

    @Bind(R.id.spinner_sprint)
    AppCompatSpinner spinnerSprint;

    private ArrayList<String> listAmbiente, listMaquinas, listSprint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_execucao_testes, container, false);
        ButterKnife.bind(this, view);

        botaoEnviar.setEnabled(true);

        listAmbiente = new ArrayList<>();
        listAmbiente.add("DEV"); listAmbiente.add("SIT"); listAmbiente.add("UAT"); listAmbiente.add("PROD");

        listMaquinas = new ArrayList<>();
        listMaquinas.add("SILK 10.54.106.12"); listMaquinas.add("SELENIUM 10.42.119.124");

        listSprint = new ArrayList<>();
        listSprint.add("18"); listSprint.add("19"); listSprint.add("20"); listSprint.add("21"); listSprint.add("22");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), R.layout.item_spinner, listAmbiente);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.item_spinner, listMaquinas);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), R.layout.item_spinner, listSprint);

        spinnerAmbiente.setAdapter(adapter1);
        spinnerMaquina.setAdapter(adapter2);
        spinnerSprint.setAdapter(adapter3);

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

    @OnClick(R.id.botaoExecutarTestes)
    public void openConfirmDialog(){

        StringBuilder sb = new StringBuilder();

        sb.append("Ambiente: ").append(spinnerAmbiente.getSelectedItem().toString()).append("\n");
        sb.append("Máquina: ").append(spinnerMaquina.getSelectedItem().toString()).append("\n");
        sb.append("Sprint: ").append(spinnerSprint.getSelectedItem().toString());

        DialogCartConfirmation dcc = new DialogCartConfirmation().newInstance(sb.toString());
        dcc.show(getFragmentManager(), "ABRE O MODAL DE BUGS");

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
