package br.com.pontomobi.livelopontos.ui.myPoints.extract;

import android.content.Context;

import java.text.NumberFormat;

import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.model.Extract;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionRequest;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionsResponse;
import br.com.pontomobi.livelopontos.ui.myPoints.summary.SummaryBusiness;
import br.com.pontomobi.livelopontos.util.DateUtil;

/**
 * Created by selem.gomes on 09/11/15.
 */
public class ExtractBusiness {

    private ExtractFragment.OnExtractBusinessListener onExtractBusinessListener;
    private Context context;
    private Extract extract;
    private int monthNumber;
    private NumberFormat numberFormat;

    public ExtractBusiness(Context context, ExtractFragment.OnExtractBusinessListener onExtractBusinessListener) {
        this.context = context;
        this.onExtractBusinessListener = onExtractBusinessListener;
        numberFormat = NumberFormat.getNumberInstance();
        extract = new Extract();
    }

    public void callServiceGetListUserTransactions(String dateInitial, String dateEnd) {
        UserTransactionRequest request = new UserTransactionRequest();
        request.setAuthorization(LiveloPontosApp.getInstance().getLogin().getAccessToken());

        request.setDateFrom(dateInitial);
        request.setDateTo(dateEnd);

        monthNumber = Integer.parseInt(dateInitial.split("/")[0]) - 1;

        LiveloRepository.with(context).getListUserTransactions(context, request, getOnServiceUserTransactionListener());
    }

    private SummaryBusiness.OnServiceUserTransactionListener getOnServiceUserTransactionListener() {
        final SummaryBusiness.OnServiceUserTransactionListener onServiceUserTransactionListener = new SummaryBusiness.OnServiceUserTransactionListener() {
            @Override
            public void onGetUserTransactionSuccess(UserTransactionsResponse response) {
                response.orderListByDate();
                response.totalizePointsExtract();

                extract.setAmountPoints(numberFormat.format(response.getAmountPoints()));
                extract.setAmountPointsAccumulated(numberFormat.format(response.getAmountPointsAccumulated()));
                extract.setAmountPointsRescued(numberFormat.format(response.getAmountPointsRescued()));
                extract.setAmountPointsDownload(numberFormat.format(response.getAmountPointsDownload()));
                extract.setAmountPointsChargebacks(numberFormat.format(response.getAmountPointsChargebacks()));
                if (response.getOutput() != null && response.getOutput().getTransactionLineItemList() != null)
                    extract.setTransactionLineItemList(response.getOutput().getTransactionLineItemList());
                extract.setLastUpdate(DateUtil.getCurrentDateInTimestamp());
                extract.setMonth(monthNumber);

//                saveInfoExtractInShared(extract);
                onExtractBusinessListener.onExtractBusinessSuccess(extract);
            }

            @Override
            public void onGetUserTransactionFail(LiveloException exception) {
                onExtractBusinessListener.onExtractBusinessFail(exception);
            }
        };

        return onServiceUserTransactionListener;
    }

//    private void saveInfoExtractInShared(Extract summary) {
//        Gson gson = new Gson();
//        String json = gson.toJson(summary);
//        SharedPreferencesHelper.write(context, Constants.SharedPrefsKeys.SHARED_PREF_EXTRACT_NAME, Constants.SharedPrefsKeys.KEY_EXTRACT, json);
//    }
//
//    public Extract loadInfoAboutExtract() {
//        String jsonSummary = SharedPreferencesHelper.read(context, Constants.SharedPrefsKeys.SHARED_PREF_EXTRACT_NAME, Constants.SharedPrefsKeys.KEY_EXTRACT, null);
//        Extract extract = null;
//        if (!TextUtils.isEmpty(jsonSummary)) {
//
//            Gson gson = new Gson();
//
//            extract = gson.fromJson(jsonSummary, Extract.class);
//        }
//
//        return extract;
//    }
}
