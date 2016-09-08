package br.com.pontomobi.livelopontos.ui.myPoints.historic;

import android.content.Context;

import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionRequest;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionsResponse;
import br.com.pontomobi.livelopontos.ui.myPoints.summary.SummaryBusiness;
import br.com.pontomobi.livelopontos.util.DateUtil;

/**
 * Created by selem.gomes on 10/11/15.
 */
public class HistoricBusiness {
    private HistoricActivity.OnHistoricBusinessListener onHistoricBusinessListener;
    private Context context;

    public HistoricBusiness(HistoricActivity.OnHistoricBusinessListener onHistoricBusinessListener, Context context) {
        this.onHistoricBusinessListener = onHistoricBusinessListener;
        this.context = context;
    }

    public void callServiceGetListUserTransactions() {
        UserTransactionRequest request = new UserTransactionRequest();
        request.setAuthorization(LiveloPontosApp.getInstance().getLogin().getAccessToken());

        request.setDateFrom(DateUtil.formatDateForService(DateUtil.getDateBeforeMonths(12)));
        request.setDateTo(DateUtil.formatDateForService(DateUtil.getCurrentDate()));

        LiveloRepository.with(context).getListUserTransactions(context, request, getOnServiceUserTransactionListener());
    }

    private SummaryBusiness.OnServiceUserTransactionListener getOnServiceUserTransactionListener() {
        final SummaryBusiness.OnServiceUserTransactionListener onServiceUserTransactionListener = new SummaryBusiness.OnServiceUserTransactionListener() {
            @Override
            public void onGetUserTransactionSuccess(UserTransactionsResponse response) {
                onHistoricBusinessListener.onHistoricBusinessSuccess(response);
            }

            @Override
            public void onGetUserTransactionFail(LiveloException exception) {
                onHistoricBusinessListener.onHistoricBusinessFail(exception);
            }
        };

        return onServiceUserTransactionListener;
    }
}

