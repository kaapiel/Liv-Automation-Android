package br.com.pontomobi.livelopontos.ui.changePassword;

import android.content.Context;

import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.changePassword.model.ChangePasswordRequest;

/**
 * Created by selemafonso on 20/04/16.
 */
public class ChangePasswordBusiness {
    private Context context;
    private ChangePasswordActivity.OnChangePasswordBusinessListener listener;


    public ChangePasswordBusiness(Context context, ChangePasswordActivity.OnChangePasswordBusinessListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void callService(String oldPassword, String newPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest(oldPassword, newPassword);

        LiveloRepository.with(context).changePassword(context, request, getOnChangeCallServiceListener());
    }

    private OnChangeCallServiceListener getOnChangeCallServiceListener() {
        return new OnChangeCallServiceListener() {
            @Override
            public void onSuccess() {
                listener.onChangeSuccess();
            }

            @Override
            public void onFail(LiveloException exception) {
                listener.onChangeFail(exception);
            }
        };
    }

    public interface OnChangeCallServiceListener {
        void onSuccess();

        void onFail(LiveloException exception);
    }
}
