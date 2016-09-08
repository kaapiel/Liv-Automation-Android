package br.com.pontomobi.livelopontos.ui.myPoints.summary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.model.Summary;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.myPoints.summaryExpired.SummaryExpiredActivity;
import br.com.pontomobi.livelopontos.ui.myPoints.summaryMonth.SummaryMonthActivity;
import br.com.pontomobi.livelopontos.ui.widget.ItemSummary;
import br.com.pontomobi.livelopontos.ui.widget.LastUpdate;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import br.com.pontomobi.livelopontos.util.Util;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by selemafonso on 19/09/15.
 */
public class SummaryFragment extends LiveloPontoFragment {


    @Bind(R.id.summary_top_points)
    TextView summaryTopPoints;
    @Bind(R.id.summary_last_update)
    LastUpdate summaryLastUpdate;
    @Bind(R.id.summary_accumulated_points)
    ItemSummary summaryAccumulatedPoints;
    @Bind(R.id.summary_expire_points)
    ItemSummary summaryExpirePoints;
    @Bind(R.id.summary_rescued_points)
    ItemSummary summaryRescuedPoints;
    @Bind(R.id.summary_main_content)
    ScrollView summaryMainContent;

    private SummaryBusiness summaryBusiness;
    private Summary currentSummary;
    private boolean loadigDataFromChache = false;
    private boolean updateInformations = false;
    private boolean activityEnable = false;
    private boolean errorShow = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_summary, container, false);
        ButterKnife.bind(this, view);
        loadigDataFromChache = false;
        summaryBusiness = new SummaryBusiness(getContext(), getOnSummaryBusinessListener());
        getInfoInChache();
        callServices();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        summaryLastUpdate.setOnUpdateListener(new LastUpdate.OnUpdateListener() {
            @Override
            public void onUpdateData() {
                callServices();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void callServices() {
        summaryLastUpdate.startAnimationRefresh();
        summaryLastUpdate.getLastUpdateLoading().setVisibility(View.VISIBLE);
        summaryLastUpdate.getLastUpdateContent().setVisibility(View.GONE);

        //if (!LiveloPontosApp.getInstance().isServiceCallSummaryOk()) {
            summaryBusiness.callServices();
            updateInformations = true;
            errorShow = false;
        //
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getInfoInChache() {
        currentSummary = summaryBusiness.loadInfoAboutSummary();
        if (currentSummary != null) {
            loadigDataFromChache = true;
            setSummaryTopPoints(currentSummary);
            setPointsAccumulatedAndRescued(currentSummary);
            setPontsExpired(currentSummary);
            setLastUpdate(currentSummary);
        }
    }

    @OnClick(R.id.summary_accumulated_points)
    public void onClickAccumulatedPoints() {
        if (Util.showSnackbarUpdate(summaryMainContent, getContext(), updateInformations)) {
            return;
        }

        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_POINTS_ACCUMULATED,
                ""
        );

        Intent intent = new Intent(getActivity(), SummaryMonthActivity.class);
        intent.putExtra(Constants.KEY_SUMMARY_TYPE, Constants.SUMMARY_ACCUMULATED);
        startActivity(intent);
    }

    @OnClick(R.id.summary_expire_points)
    public void onClickExpirePoints() {
        if (Util.showSnackbarUpdate(summaryMainContent, getContext(), updateInformations)) {
            return;
        }

        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_POINTS_EXPIRE,
                ""
        );

        Intent intent = new Intent(getActivity(), SummaryExpiredActivity.class);
        intent.putExtra(SummaryExpiredActivity.KEY_LIST_EXPIRED, currentSummary.getPointsExpiredResponse());
        startActivity(intent);
    }

    @OnClick(R.id.summary_rescued_points)
    public void onClickRescuedPoints() {
        if (Util.showSnackbarUpdate(summaryMainContent, getContext(), updateInformations)) {
            return;
        }

        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_POINTS_RESCUED,
                ""
        );

        Intent intent = new Intent(getActivity(), SummaryMonthActivity.class);
        intent.putExtra(Constants.KEY_SUMMARY_TYPE, Constants.SUMMARY_RESCUED);
        startActivity(intent);
    }

    private OnSummaryBusinessListener getOnSummaryBusinessListener() {
        OnSummaryBusinessListener onSummaryBusinessListener = new OnSummaryBusinessListener() {
            @Override
            public void onGetAmountPointsSuccess(Summary summary) {
                currentSummary = summary;
                setSummaryTopPoints(summary);
            }

            @Override
            public void onGetAmountPointsFail(LiveloException exception) {
                summaryLastUpdate.stopAnimationRefresh();
                errorServiceReturn(exception);
            }

            @Override
            public void onGetPointsAccumulatedAndRescuedSuccess(Summary summary) {
                currentSummary = summary;
                setPointsAccumulatedAndRescued(summary);
            }

            @Override
            public void onGetPointsAccumulatedAndRescuedFail(LiveloException exception) {
                summaryLastUpdate.stopAnimationRefresh();
                errorServiceReturn(exception);
            }

            @Override
            public void onGetPointsExpiredSuccess(Summary summary) {
                currentSummary = summary;
                setPontsExpired(summary);
            }

            @Override
            public void onGetPointsExpiredFail(LiveloException exception) {
                summaryLastUpdate.stopAnimationRefresh();
                errorServiceReturn(exception);
            }

            @Override
            public void onGetPointsSuccess(Summary summary) {
                LiveloPontosApp.getInstance().setServiceCallSummaryOk(true);
                currentSummary = summary;
                updateInformations = false;
                setLastUpdate(summary);
            }

            @Override
            public void onGetPointsFail(LiveloException exception) {
                summaryLastUpdate.stopAnimationRefresh();
                Log.d("UPDATE", "FAIL");
                setLastUpdate(currentSummary);
                updateInformations = false;
                errorServiceReturn(exception);
            }
        };

        return onSummaryBusinessListener;
    }

    private void errorServiceReturn(final LiveloException exception) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (exception.getErrorCode() == LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR) {
                    sessionExpired();
                } else {
                    if (!loadigDataFromChache) {

                        if(!errorShow) {
                            errorShow = true;
                            showDialogError(exception.getAlertToShow(getContext()));
                        }
                    }
                }
            }
        }, 500);
    }

    private void sessionExpired() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
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
        if (getActivity() == null) {
            return;
        }

        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(getActivity(), alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callServices();
                        }

                        @Override
                        public void onNegativeClick() {
                        }

                        @Override
                        public void onBackPressedInDialog() {

                        }
                    });
        }
    }

    private void setPointsAccumulatedAndRescued(Summary summary) {
        if (getActivity() == null) {
            return;
        }

        summaryAccumulatedPoints.setAmountPoints(TextUtils.isEmpty(summary.getAmountPointsAccumulated()) ? "0" : summary.getAmountPointsAccumulated());
        summaryRescuedPoints.setAmountPoints(TextUtils.isEmpty(summary.getAmountPointsRescued()) ? "0" : summary.getAmountPointsRescued().replace("-", ""));
    }

    private void setPontsExpired(Summary summary) {
        if (getActivity() == null) {
            return;
        }
        summaryExpirePoints.setAmountPoints(TextUtils.isEmpty(summary.getAmountPointsExpire()) ? "0" : summary.getAmountPointsExpire().replace("-", ""));
    }

    private void setSummaryTopPoints(Summary summary) {
        if (getActivity() == null) {
            return;
        }
        summaryTopPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, Util.getTextSizePoints(getContext(), summary.getAmountPoints()));

        summaryTopPoints.setText(TextUtils.isEmpty(summary.getAmountPoints()) ? "0" : summary.getAmountPoints());
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);
        summaryTopPoints.startAnimation(fadeIn);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            if (isVisibleToUser) {
                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_POINTS,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_POINTS,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_SUMMARY,
                        ""
                );
            }
        }
    }

    private void setLastUpdate(Summary summary) {
        if (getActivity() == null) {
            return;
        }

        if (summary == null) {
            summaryLastUpdate.setLastUpdateDateHour("...");
            return;
        }

        summaryLastUpdate.setLastUpdateDateHour(DateUtil.getFormatDateToShow(summary.getLastUpdateData()));
        Log.d("DATA_LIVELO", DateUtil.getFormatDateToShow(summary.getLastUpdateData()));
    }

    public interface OnSummaryBusinessListener {
        void onGetAmountPointsSuccess(Summary summary);

        void onGetAmountPointsFail(LiveloException exception);

        void onGetPointsAccumulatedAndRescuedSuccess(Summary summary);

        void onGetPointsAccumulatedAndRescuedFail(LiveloException exception);

        void onGetPointsExpiredSuccess(Summary summary);

        void onGetPointsExpiredFail(LiveloException exception);

        void onGetPointsSuccess(Summary summary);

        void onGetPointsFail(LiveloException exception);
    }
}
