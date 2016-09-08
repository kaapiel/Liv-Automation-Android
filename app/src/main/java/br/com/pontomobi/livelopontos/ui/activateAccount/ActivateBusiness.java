package br.com.pontomobi.livelopontos.ui.activateAccount;

import android.content.Context;

import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.activate.model.ActivateRequest;
import br.com.pontomobi.livelopontos.service.livelo.activate.model.ActivateUserResponse;

public class ActivateBusiness {

    private Context mContext;
    private OnServiceActivateListener mOnServiceActivateListener;

    public ActivateBusiness(Context context, OnServiceActivateListener onServiceActivateListener) {
        mContext = context;
        mOnServiceActivateListener = onServiceActivateListener;
    }

    public void callServiceActivate(String cpf, String code) {
        LiveloRepository.with(mContext).activateUser(mContext, new ActivateRequest(cpf, code), getOnServiceActivateListener());
    }


    private OnServiceActivateListener getOnServiceActivateListener(){
        return new OnServiceActivateListener() {
            @Override
            public void onActivateSuccess(ActivateUserResponse response) {
                mOnServiceActivateListener.onActivateSuccess(response);
            }

            @Override
            public void onLoginFail(LiveloException exception) {
                mOnServiceActivateListener.onLoginFail(exception);
            }
        };
    }


    public interface OnServiceActivateListener {
        void onActivateSuccess(ActivateUserResponse response);

        void onLoginFail(LiveloException exception);
    }
}
