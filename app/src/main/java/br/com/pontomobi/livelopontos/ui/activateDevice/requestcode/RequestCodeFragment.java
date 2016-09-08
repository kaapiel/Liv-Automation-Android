package br.com.pontomobi.livelopontos.ui.activateDevice.requestcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.ui.activateDevice.ActivateDeviceActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vilmar.filho on 12/10/15.
 */
public class RequestCodeFragment extends LiveloPontoFragment {

    public static RequestCodeFragment newInstance(){
        return new RequestCodeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_code, container, false);

        ButterKnife.bind(this, v);

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @OnClick(R.id.request_activation)
    public void requestCode(){
        ((ActivateDeviceActivity) getActivity()).goTo(ActivateDeviceActivity.ACTIVATE_CODE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
