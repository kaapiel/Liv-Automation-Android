package br.com.pontomobi.livelopontos.ui.myPoints.extract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.model.Extract;
import br.com.pontomobi.livelopontos.model.MonthsExtract;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.myPoints.historic.HistoricActivity;
import br.com.pontomobi.livelopontos.ui.widget.ExpandableList;
import br.com.pontomobi.livelopontos.ui.widget.ExtractFooter;
import br.com.pontomobi.livelopontos.ui.widget.LastUpdate;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import br.com.pontomobi.livelopontos.util.Util;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by selem.gomes on 21/09/15.
 */
public class ExtractFragment extends LiveloPontoFragment {

    private static final int QTDE_MONTHS_HISTORY_TO_SHOW = 11;
    @Bind(R.id.extract_last_update)
    LastUpdate extractLastUpdate;
    @Bind(R.id.extract_list_transactions)
    ExpandableList extractListTransactions;
    @Bind(R.id.extract_footer_accumulated)
    ExtractFooter extractFooterAccumulated;
    @Bind(R.id.extract_footer_rescued)
    ExtractFooter extractFooterRescued;
    @Bind(R.id.extract_footer_expire)
    ExtractFooter extractFooterExpire;
    @Bind(R.id.extract_footer_download)
    ExtractFooter extractFooterDownload;
    @Bind(R.id.extract_footer_chargebacks)
    ExtractFooter extractFooterChargebacks;
    @Bind(R.id.extract_footer)
    RelativeLayout extractFooter;
    @Bind(R.id.extract_nothing_to_show)
    LinearLayout extractNothingToShow;
    @Bind(R.id.extract_scroll)
    ScrollView extractScroll;
    @Bind(R.id.extract_main_content)
    RelativeLayout extractMainContent;
    @Bind(R.id.list_months)
    RecyclerView listMonths;
    @Bind(R.id.img_month)
    TextView imgMonth;

    private ExtractBusiness extractBusiness;
    private boolean loadigDataFromChache = false;
    private String dtInicial = "";
    private String dtEnd = "";
    private boolean updateInformations = false;
    private int oldTabSelected = 0;
    private boolean isVisible = false;
    private List<MonthsExtract> monthsExtractList;

    private String currentMonth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_extract, container, false);
        ButterKnife.bind(this, view);

        loadigDataFromChache = false;
        extractBusiness = new ExtractBusiness(getContext(), getOnExtractBusinessListener());

        getListMonths();
        initListMonth();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractLastUpdate.setOnUpdateListener(new LastUpdate.OnUpdateListener() {
            @Override
            public void onUpdateData() {
                if (!TextUtils.isEmpty(currentMonth))
                    callServiceGetListTransactions(getPositionMonthInList(currentMonth));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                isVisible = false;
            } else {
                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_POINTS,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_POINTS,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_EXTRACT,
                        ""
                );
                isVisible = true;
            }
        }
    }

    @OnClick(R.id.extract_show_historic)
    public void onShowHistoricClick() {
        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_HISTORY,
                ""
        );

        Intent intent = new Intent(getActivity(), HistoricActivity.class);
        startActivity(intent);
    }

    private void getListMonths() {

        monthsExtractList = new ArrayList<MonthsExtract>();
        for (int i = QTDE_MONTHS_HISTORY_TO_SHOW; i >= 0; i--) {
            MonthsExtract monthsExtract = new MonthsExtract();
            Date date = DateUtil.getDateBeforeMonths(i);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String month = DateUtil.getMonthName(cal.get(Calendar.MONTH) + 1);
            int year = cal.get(Calendar.YEAR);
            String yearStr = year + "";


            monthsExtract.setDateFrom(DateUtil.getFirstDateInMonth(cal.get(Calendar.MONTH), year));
            monthsExtract.setDateTo(DateUtil.getLastDateInMonth(cal.get(Calendar.MONTH), year));
            monthsExtract.setDateToShow(month + " / " + yearStr.substring(2));
            monthsExtractList.add(monthsExtract);
        }
    }

    private void initListMonth() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        listMonths.setLayoutManager(lm);
        listMonths.setAdapter(new ExtractMonthAdapter(monthsExtractList, getOnClickMonthListener()));

        final int itemWidth = (int) getResources().getDimension(R.dimen.item_extract_month_widht);

        imgMonth.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int center = (imgMonth.getLeft() + imgMonth.getRight()) / 2;

                int padding = center - itemWidth / 2; //Assuming both left and right padding needed are the same

                listMonths.setPadding(padding, 0, padding, 0);
                listMonths.setOnScrollListener(new CenterLockListener(center, getContext(), getOnMonthSelectListener()));
            }
        });

        //last month
        int startPosition = 11;
        listMonths.smoothScrollToPosition(startPosition);
    }

    private OnMonthSelectListener getOnMonthSelectListener() {
        return new OnMonthSelectListener() {
            @Override
            public void onMonthSelect(String month) {
                currentMonth = month;
                callServiceGetListTransactions(getPositionMonthInList(month));
            }
        };
    }

    private int getPositionMonthInList(String month) {
        for (int i = 0; i < monthsExtractList.size(); i++) {
            if (monthsExtractList.get(i).getDateToShow().equalsIgnoreCase(month)) {
                return i;
            }
        }

        return 11;
    }

    private OnClickMonthListener getOnClickMonthListener() {
        return new OnClickMonthListener() {
            @Override
            public void onClick(int position) {
                listMonths.smoothScrollToPosition(position);
            }
        };
    }

    private void callServiceGetListTransactions(int postion) {
        if (Util.showSnackbarUpdate(extractMainContent, getContext(), updateInformations)) {
            listMonths.smoothScrollToPosition(oldTabSelected);
            return;
        }

        oldTabSelected = postion;
        extractLastUpdate.getLastUpdateLoading().setVisibility(View.VISIBLE);
        extractLastUpdate.getLastUpdateContent().setVisibility(View.GONE);

        dtInicial = monthsExtractList.get(postion).getDateFrom();
        dtEnd = monthsExtractList.get(postion).getDateTo();
        updateInformations = true;
        extractBusiness.callServiceGetListUserTransactions(dtInicial, dtEnd);
    }

    private OnExtractBusinessListener getOnExtractBusinessListener() {
        OnExtractBusinessListener extractBusinessListener = new OnExtractBusinessListener() {
            @Override
            public void onExtractBusinessSuccess(Extract extract) {
                setInfoScreen(extract);
                updateInformations = false;
                loadigDataFromChache = true;
                LiveloPontosApp.getInstance().setServiceCallExtractOk(true);
            }

            @Override
            public void onExtractBusinessFail(LiveloException exception) {
                if (!loadigDataFromChache) {
                    setInfoScreen(new Extract());
                }
                errorServiceReturn(exception);
                updateInformations = false;
            }
        };

        return extractBusinessListener;
    }

    private void errorServiceReturn(final LiveloException exception) {
        if (!isVisible) {
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (exception.getErrorCode() == LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR) {
                    sessionExpired();
                } else {
                    showDialogError(exception.getAlertToShow(getContext()));
                }
            }
        }, 500);
    }

    private void sessionExpired() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(getActivity(), LiveloPontosApp.getInstance().getExpiredSession(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            LoginUtil.clearLogin(getContext());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        public void onNegativeClick() {
                            Log.d("DIALOG", "NEGATIVE");
                        }

                        @Override
                        public void onBackPressedInDialog() {

                        }
                    });
        }
    }

    private void showDialogError(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(getActivity(), alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            extractBusiness.callServiceGetListUserTransactions(dtInicial, dtEnd);
                        }

                        @Override
                        public void onNegativeClick() {
                        }

                        @Override
                        public void onBackPressedInDialog() {
                            setInfoScreen(new Extract());
                        }
                    });
        }
    }


    private void setInfoScreen(Extract extract) {
        if (getContext() == null) {
            return;
        }

        extractListTransactions.setAdapter(new ExtractAdapter(getContext(), extract.getTransactionLineItemList()));
        extractListTransactions.setExpanded(true);

        if (extract.getTransactionLineItemList() != null && extract.getTransactionLineItemList().size() > 0) {
            extractFooter.setVisibility(View.VISIBLE);
            extractNothingToShow.setVisibility(View.GONE);

            extractFooterAccumulated.setAmountPoints(checkPoints(extract.getAmountPointsAccumulated()));
            extractFooterRescued.setAmountPoints(checkPoints(extract.getAmountPointsRescued()));
            extractFooterExpire.setAmountPoints(checkPoints(extract.getAmountPointsExpire()));
            extractFooterChargebacks.setAmountPoints(checkPoints(extract.getAmountPointsChargebacks()));
            extractFooterDownload.setAmountPoints(checkPoints(extract.getAmountPointsDownload()));
        } else {
            extractFooter.setVisibility(View.GONE);
            extractNothingToShow.setVisibility(View.VISIBLE);
        }

        if (extract.getLastUpdate() == null) {
            extractLastUpdate.setLastUpdateDateHour("...");
        } else {
            extractLastUpdate.setLastUpdateDateHour(DateUtil.getFormatDateToShow(extract.getLastUpdate()));
        }
    }

    private String checkPoints(String points) {
        return TextUtils.isEmpty(points) ? "0" : points;
    }

    public interface OnExtractBusinessListener {
        void onExtractBusinessSuccess(Extract extract);

        void onExtractBusinessFail(LiveloException exception);
    }

    public interface OnClickMonthListener {
        void onClick(int position);
    }

    public interface OnMonthSelectListener {
        void onMonthSelect(String month);
    }
}
