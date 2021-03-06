package br.com.aguido.livautomation.ui.home;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.ArrayList;

import br.com.aguido.livautomation.LivAutomationFragment;
import br.com.aguido.livautomation.R;
import br.com.aguido.livautomation.ui.dialog.DialogCartConfirmation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vilmar.filho on 12/4/15.
 */
public class ExecucaoTestesFragment extends LivAutomationFragment implements OnChartGestureListener {

    @BindView(R.id.botaoExecutarTestes)
    AppCompatButton botaoEnviar;

    @BindView(R.id.spinner_ambiente)
    AppCompatSpinner spinnerAmbiente;

    @BindView(R.id.spinner_maquina)
    AppCompatSpinner spinnerMaquina;

    @BindView(R.id.spinner_sprint)
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
        listMaquinas.add("SILK 192.168.0.1"); listMaquinas.add("SELENIUM 192.168.0.2");

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
