package br.com.pontomobi.livelopontos.ui.forgotPassword;

import android.content.Context;

import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.forgotpassword.model.ForgotPasswordRequest;

/**
 * Created by selemafonso on 19/04/16.
 */
public class ForgotPasswordBusiness {
    private Context context;
    private ForgotPasswordActivity.OnForgotPasswordBusinessListener onForgotPasswordBusinessListener;

    public ForgotPasswordBusiness(Context context, ForgotPasswordActivity.OnForgotPasswordBusinessListener onForgotPasswordBusinessListener) {
        this.context = context;
        this.onForgotPasswordBusinessListener = onForgotPasswordBusinessListener;
    }

    public void callService(String cpf) {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setCustomerId(cpf);
        LiveloRepository.with(context).forgotPassword(context, request, getOnRecoverCallListener());
    }

    private OnRecoverCallListener getOnRecoverCallListener() {
        return new OnRecoverCallListener() {
            @Override
            public void onRecoverCallSuccess() {
                onForgotPasswordBusinessListener.onRecoverSuccess();
            }

            @Override
            public void onRecoverCallFail(LiveloException liveloException) {
                onForgotPasswordBusinessListener.onRecoverFail(liveloException);
            }
        };
    }

    public interface OnRecoverCallListener {
        void onRecoverCallSuccess();
        void onRecoverCallFail(LiveloException liveloException);
    }
}
