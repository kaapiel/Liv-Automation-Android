package br.com.pontomobi.livelopontos.ui.myPoints.summary;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.Date;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.model.Summary;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.amountpoints.model.AmountPointsResponse;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.PointsExpiredResponse;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionRequest;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionsResponse;
import br.com.pontomobi.livelopontos.util.DateUtil;

/**
 * Created by selem.gomes on 09/11/15.
 */
public class SummaryBusiness {
    private SummaryFragment.OnSummaryBusinessListener onSummaryBusinessListener;
    private Context context;
    private Summary summary;
    private NumberFormat numberFormat;

    private Date mDateRequestService;

    public SummaryBusiness(Context context, SummaryFragment.OnSummaryBusinessListener onSummaryBusinessListener) {
        this.onSummaryBusinessListener = onSummaryBusinessListener;
        this.context = context;
        summary = new Summary();
        numberFormat = NumberFormat.getNumberInstance();

        mDateRequestService = DateUtil.getDateBeforeMonths(6);
    }

    public void callServices() {
        callServiceGetAmountPoints();
    }

    private void setLastUpdate() {
        summary.setLastUpdateData(DateUtil.getCurrentDateInMillis());
    }

    private void callServiceGetAmountPoints() {
        LiveloRepository.with(context).getAmountPoints(context, getOnServiceAmointPointsListener());
    }

    private OnServiceAmountPointsListener getOnServiceAmointPointsListener() {
        OnServiceAmountPointsListener onServiceAmountPointsListener = new OnServiceAmountPointsListener() {
            @Override
            public void onGetPointsSuccess(AmountPointsResponse response) {
                summary.setAmountPoints(numberFormat.format(response.getOutput().getResult()));
                onSummaryBusinessListener.onGetAmountPointsSuccess(summary);
                setLastUpdate();
                callServiceGetListUserTransactions();
                saveInfoSummaryInShared(summary);

            }

            @Override
            public void onGetPontsFail(LiveloException exception) {
                onSummaryBusinessListener.onGetAmountPointsFail(exception);

                if (exception.getErrorCode() != LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR) {
                    callServiceGetListUserTransactions();
                }
            }
        };

        return onServiceAmountPointsListener;
    }


    private void callServiceGetListUserTransactions() {
        UserTransactionRequest request = new UserTransactionRequest();
        request.setAuthorization(LiveloPontosApp.getInstance().getLogin().getAccessToken());

        request.setDateFrom(DateUtil.formatDateForService(mDateRequestService));
        request.setDateTo(DateUtil.formatDateForService(DateUtil.getCurrentDate()));

        LiveloRepository.with(context).getListUserTransactions(context, request, getOnServiceUserTransactionListener());
    }


    private OnServiceUserTransactionListener getOnServiceUserTransactionListener() {
        OnServiceUserTransactionListener onServiceUserTransactionListener = new OnServiceUserTransactionListener() {
            @Override
            public void onGetUserTransactionSuccess(UserTransactionsResponse response) {
                response.totalizePoints();
                setLastUpdate();
                summary.setAmountPointsAccumulated(numberFormat.format(response.getAmountPointsAccumulated()));
                summary.setAmountPointsRescued(numberFormat.format(response.getAmountPointsRescued()));
                summary.setPointsPerMonthList(response.totalizePointsPerMonth(UserTransactionsResponse.TRANSACTION_TYPE_ACCUMULATED, mDateRequestService, new Date()));
                onSummaryBusinessListener.onGetPointsAccumulatedAndRescuedSuccess(summary);
                saveInfoSummaryInShared(summary);
                callServiceGetPointsExpired();
            }

            @Override
            public void onGetUserTransactionFail(LiveloException exception) {
                onSummaryBusinessListener.onGetPointsAccumulatedAndRescuedFail(exception);
                callServiceGetPointsExpired();
            }
        };

        return onServiceUserTransactionListener;
    }


    private void callServiceGetPointsExpired() {
        LiveloRepository.with(context).getPointsExpired(context, getOnServicePointsExpiredListener());
    }


    private OnServicePointsExpiredListener getOnServicePointsExpiredListener() {
        OnServicePointsExpiredListener onServicePointsExpiredListener = new OnServicePointsExpiredListener() {
            @Override
            public void onGetPointsExpiredSuccess(PointsExpiredResponse response) {
                setLastUpdate();
                summary.setAmountPointsExpire(numberFormat.format(response.getPointsSum()));
                onSummaryBusinessListener.onGetPointsExpiredSuccess(summary);
                onSummaryBusinessListener.onGetPointsSuccess(summary);
                summary.setPointsExpiredResponse(response);

                saveInfoSummaryInShared(summary);
            }

            @Override
            public void onGetPointsExpiredFail(LiveloException exception) {
                onSummaryBusinessListener.onGetPointsExpiredFail(exception);
                onSummaryBusinessListener.onGetPointsFail(exception);
            }
        };

        return onServicePointsExpiredListener;
    }


    private void saveInfoSummaryInShared(Summary summary) {
        Gson gson = new Gson();
        String json = gson.toJson(summary);
        SharedPreferencesHelper.write(context, Constants.SharedPrefsKeys.SHARED_PREF_SUMMARY_NAME, Constants.SharedPrefsKeys.KEY_SUMMARY, json);
    }

    public Summary loadInfoAboutSummary() {
        String jsonSummary = SharedPreferencesHelper.read(context, Constants.SharedPrefsKeys.SHARED_PREF_SUMMARY_NAME, Constants.SharedPrefsKeys.KEY_SUMMARY, null);
        Summary summary = null;
        if (!TextUtils.isEmpty(jsonSummary)) {

            Gson gson = new Gson();

            summary = gson.fromJson(jsonSummary, Summary.class);
        }

        return summary;
    }


    /**
     * Listerners usados para as chamadas dos serviços.
     */
    public interface OnServiceAmountPointsListener {
        void onGetPointsSuccess(AmountPointsResponse response);

        void onGetPontsFail(LiveloException exception);
    }

    public interface OnServiceUserTransactionListener {
        void onGetUserTransactionSuccess(UserTransactionsResponse response);

        void onGetUserTransactionFail(LiveloException exception);
    }

    public interface OnServicePointsExpiredListener {
        void onGetPointsExpiredSuccess(PointsExpiredResponse response);

        void onGetPointsExpiredFail(LiveloException exception);
    }
}
