package br.com.pontomobi.livelopontos.ui.myPoints.summaryMonth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.model.PointsPerMonth;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionsResponse;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.myPoints.summaryMonthDetail.SummaryMonthDetailActivity;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 21/09/15.
 */
public class SummaryMonthActivity extends LiveloPontosActivity implements View.OnClickListener {


    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.summary_topo_month)
    ImageView summaryTopoMonth;
    @Bind(R.id.summary_month_first_option)
    TextView summaryMonthFirstOption;
    @Bind(R.id.summary_month_second_option)
    TextView summaryMonthSecondOption;
    @Bind(R.id.summary_month_third_option)
    TextView summaryMonthThirdOption;
    @Bind(R.id.summary_month_backgound)
    View summaryMonthBackgound;
    @Bind(R.id.nothing_to_show_image)
    ImageView nothingToShowImage;
    @Bind(R.id.nothing_to_show_title)
    TextView nothingToShowTitle;
    @Bind(R.id.nothing_to_show_subtitle)
    TextView nothingToShowSubtitle;
    @Bind(R.id.summary_month_nothing_to_show)
    RelativeLayout summaryMonthNothingToShow;
    @Bind(R.id.recycle_view_summary_month)
    RecyclerView recycleViewSummaryMonth;

    boolean activityEnable = false;
    boolean show = true;
    int summaryType;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;
    private SummaryMonthBusiness summaryMonthBusiness;
    private String transactionType;
    private UserTransactionsResponse userTransactions;
    private List<PointsPerMonth> pointsPerMonthList;
    private Date dateLastMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_month);
        summaryType = getIntent().getExtras().getInt(Constants.KEY_SUMMARY_TYPE);
        ButterKnife.bind(this);

        loadingContent.setVisibility(View.VISIBLE);
        configureActionBar();
        init();
        initRecyclerView();

        dateLastMonth = DateUtil.getCurrentDate();
        activityEnable = true;
        summaryMonthBusiness = new SummaryMonthBusiness(getOnSummaryMonthBusinessListener(), getBaseContext());
        summaryMonthBusiness.callServiceGetListUserTransactions();
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityEnable = false;
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(toolbar);
        super.configureActionBar();
        switch (summaryType) {
            case Constants.SUMMARY_ACCUMULATED:
                toolbarTitle.setText(getResources().getString(R.string.summary_month_accumulated_title_toolbar));
                transactionType = UserTransactionsResponse.TRANSACTION_TYPE_ACCUMULATED;
                nothingToShowSubtitle.setText(getString(R.string.no_points_to_show_subtitle_accumulated));
                break;

            case Constants.SUMMARY_EXPIRE:
                toolbarTitle.setText(getResources().getString(R.string.summary_month_expire_title_toolbar));
                nothingToShowSubtitle.setText(getString(R.string.no_points_to_show_subtitle_expired));
                break;

            case Constants.SUMMARY_RESCUED:
                toolbarTitle.setText(getResources().getString(R.string.summary_month_rescued_title_toolbar));
                transactionType = UserTransactionsResponse.TRANSACTION_TYPE_RESCUED;
                nothingToShowSubtitle.setText(getString(R.string.no_points_to_show_subtitle_rescued));
                break;

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewSummaryMonth.setLayoutManager(layoutManager);
    }

    private void animationButtons(View view, int xDest, int yDest) {

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, (xDest * -1)),
                ObjectAnimator.ofFloat(view, "translationY", 0, (yDest * -1))
        );

        set.setDuration(500).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                summaryMonthBackgound.setVisibility(View.VISIBLE);
                summaryMonthSecondOption.setVisibility(View.VISIBLE);
                summaryMonthThirdOption.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn).duration(400).playOn(findViewById(R.id.summary_month_backgound));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.summary_month_second_option));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.summary_month_third_option));
            }
        }, 200);

    }

    private void animationCloseButtons(final View view) {

        final AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0f),
                ObjectAnimator.ofFloat(view, "translationY", 1.0f)
        );

        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.summary_month_second_option));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.summary_month_third_option));
        YoYo.with(Techniques.FadeOut).duration(400).playOn(findViewById(R.id.summary_month_backgound));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                summaryMonthSecondOption.setVisibility(View.INVISIBLE);
                summaryMonthThirdOption.setVisibility(View.INVISIBLE);
                summaryMonthBackgound.setVisibility(View.INVISIBLE);
            }
        }, 200);

        set.setDuration(500).start();

    }

    private void init() {
        summaryMonthFirstOption.setOnClickListener(this);
        summaryMonthSecondOption.setOnClickListener(this);
        summaryMonthThirdOption.setOnClickListener(this);
        summaryMonthBackgound.setOnClickListener(this);

    }

    private void controlAnimations() {
        if (show) {
            show = false;
            animationButtons(summaryTopoMonth, 0, getSize());
            summaryMonthFirstOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_up, 0);
        } else {
            show = true;
            summaryMonthFirstOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_down, 0);
            animationCloseButtons(summaryTopoMonth);
        }
    }

    private int getSize() {
        int density= getResources().getDisplayMetrics().densityDpi;

        switch(density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return -100;
            case DisplayMetrics.DENSITY_HIGH:
                return -190;
            case DisplayMetrics.DENSITY_XHIGH:
                return -240;
            case DisplayMetrics.DENSITY_XXHIGH:
                return -370;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return -510;

            default:
                return -370;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.summary_month_first_option:
                controlAnimations();
                break;

            case R.id.summary_month_second_option:
                controlAnimations();

                processTransactionsToShow(checkQtdeMonths(summaryMonthSecondOption.getText().toString()), dateLastMonth);
                summaryMonthFirstOption.setText(summaryMonthSecondOption.getText().toString());
                changeOptionsPosition(summaryMonthSecondOption.getText().toString());

                break;

            case R.id.summary_month_third_option:
                controlAnimations();

                processTransactionsToShow(checkQtdeMonths(summaryMonthThirdOption.getText().toString()), dateLastMonth);
                summaryMonthFirstOption.setText(summaryMonthThirdOption.getText().toString());
                changeOptionsPosition(summaryMonthThirdOption.getText().toString());
                break;

            case R.id.summary_month_backgound:
                show = false;
                controlAnimations();
                break;
        }
    }

    private void changeOptionsPosition(String optionChosen) {
        if (optionChosen.equals(getResources().getString(R.string.summary_month_filter_last_month))) {
            summaryMonthSecondOption.setText(getString(R.string.summary_month_filter_last_three_month));
            summaryMonthThirdOption.setText(getString(R.string.summary_month_filter_last_six_month));
        } else if (optionChosen.equals(getResources().getString(R.string.summary_month_filter_last_three_month))) {
            summaryMonthSecondOption.setText(getString(R.string.summary_month_filter_last_month));
            summaryMonthThirdOption.setText(getString(R.string.summary_month_filter_last_six_month));
        } else {
            summaryMonthSecondOption.setText(getString(R.string.summary_month_filter_last_month));
            summaryMonthThirdOption.setText(getString(R.string.summary_month_filter_last_three_month));
        }

        sendEventFilterBy(optionChosen);
    }

    private void sendEventFilterBy(String filter) {
        if (summaryType == Constants.SUMMARY_ACCUMULATED) {
            LiveloPontosApp.getInstance().sendTrackerEvent(
                    Constants.GoogleAnalytisEvents.EVENT_SCREEN_POINTS_ACCUMULATE,
                    Constants.GoogleAnalytisEvents.EVENT_CATEGORY_POINTS_ACCUMULATE,
                    Constants.GoogleAnalytisEvents.EVENT_ACTION_FILTER,
                    filter
            );
        } else if (summaryType == Constants.SUMMARY_RESCUED) {
            LiveloPontosApp.getInstance().sendTrackerEvent(
                    Constants.GoogleAnalytisEvents.EVENT_SCREEN_POINTS_RESCUED,
                    Constants.GoogleAnalytisEvents.EVENT_CATEGORY_POINTS_RESCUED,
                    Constants.GoogleAnalytisEvents.EVENT_ACTION_FILTER,
                    filter
            );
        }
    }

    private Date checkQtdeMonths(String text) {
        if (text.equals(getResources().getString(R.string.summary_month_filter_last_month))) {
            return DateUtil.getDateBefore30Days(30);
        } else if (text.equals(getResources().getString(R.string.summary_month_filter_last_three_month))) {
            return DateUtil.getDateBeforeMonths(2);
        } else {
            return DateUtil.getDateBeforeMonths(5);
        }
    }

    private OnSummaryMonthClickListener getOnSummaryMonthClickListener() {
        OnSummaryMonthClickListener onSummaryMonthClickListener = new OnSummaryMonthClickListener() {
            @Override
            public void onItemClick(int position) {
                PointsPerMonth pointsPerMonth = pointsPerMonthList.get(position);

                sendEventMonthDetail(DateUtil.getMonthName(pointsPerMonth.getMonth()) + "/" + pointsPerMonth.getYear());
                Intent intent = new Intent(getBaseContext(), SummaryMonthDetailActivity.class);
                intent.putExtra(SummaryMonthDetailActivity.KEY_MONTH_NAME, DateUtil.getMonthName(pointsPerMonth.getMonth()) + "/" + pointsPerMonth.getYear());
                intent.putExtra(SummaryMonthDetailActivity.KEY_MONTH_INFO, pointsPerMonth);
                startActivity(intent);


            }
        };

        return onSummaryMonthClickListener;
    }

    private void sendEventMonthDetail(String monthYear) {
        if (summaryType == Constants.SUMMARY_ACCUMULATED) {
            LiveloPontosApp.getInstance().sendTrackerEvent(
                    Constants.GoogleAnalytisEvents.EVENT_SCREEN_POINTS_ACCUMULATE,
                    Constants.GoogleAnalytisEvents.EVENT_CATEGORY_POINTS_ACCUMULATE,
                    Constants.GoogleAnalytisEvents.EVENT_ACTION_MONTH_DETAIL,
                    monthYear
            );
        } else if (summaryType == Constants.SUMMARY_RESCUED) {
            LiveloPontosApp.getInstance().sendTrackerEvent(
                    Constants.GoogleAnalytisEvents.EVENT_SCREEN_POINTS_RESCUED,
                    Constants.GoogleAnalytisEvents.EVENT_CATEGORY_POINTS_RESCUED,
                    Constants.GoogleAnalytisEvents.EVENT_ACTION_MONTH_DETAIL,
                    monthYear
            );
        }
    }

    private OnSummaryMonthBusinessListener getOnSummaryMonthBusinessListener() {
        OnSummaryMonthBusinessListener onSummaryMonthBusinessListener = new OnSummaryMonthBusinessListener() {
            @Override
            public void onSummaryMonthBusinessSuccess(UserTransactionsResponse response) {
                if(!activityEnable) {
                    return;
                }
                userTransactions = response;
                processTransactionsToShow(DateUtil.getDateBefore30Days(30), dateLastMonth);
            }

            @Override
            public void onSummaryMonthBusinessFail(final LiveloException exception) {
                if(!activityEnable) {
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingContent.setVisibility(View.GONE);
                        if (exception.getErrorCode() == LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR) {
                            sessionExpired();
                        } else {
                            showDialogNoConnection(exception.getAlertToShow(getBaseContext()));
                        }
                    }
                }, 500);
            }
        };

        return onSummaryMonthBusinessListener;
    }

    private void sessionExpired() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(this, LiveloPontosApp.getInstance().getExpiredSession(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            LoginUtil.clearLogin(getBaseContext());
                            Intent intent = new Intent(SummaryMonthActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
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

    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(SummaryMonthActivity.this, alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            loadingContent.setVisibility(View.VISIBLE);
                            summaryMonthBusiness.callServiceGetListUserTransactions();
                        }

                        @Override
                        public void onNegativeClick() {
                            Log.d("DIALOG", "NEGATIVE");
                        }

                        @Override
                        public void onBackPressedInDialog() {
                            onBackPressed();
                        }
                    });
        }
    }

    private void processTransactionsToShow(Date dateFrom, Date dateTo) {
        List<PointsPerMonth> pointsPerMonthList = new ArrayList<PointsPerMonth>();
        pointsPerMonthList = userTransactions.totalizePointsPerMonth(transactionType, dateFrom, dateTo);
        setInfo(pointsPerMonthList);
    }

    private void setInfo(List<PointsPerMonth> pointsPerMonthList) {
        loadingContent.setVisibility(View.GONE);

        this.pointsPerMonthList = pointsPerMonthList;
        if (pointsPerMonthList.size() > 0) {
            recycleViewSummaryMonth.setVisibility(View.VISIBLE);
            summaryMonthNothingToShow.setVisibility(View.GONE);
            recycleViewSummaryMonth.setAdapter(new SummaryMonthAdapter(getBaseContext(), pointsPerMonthList, summaryType, getOnSummaryMonthClickListener()));
        } else {
            recycleViewSummaryMonth.setVisibility(View.GONE);
            summaryMonthNothingToShow.setVisibility(View.VISIBLE);
        }
    }

    public interface OnSummaryMonthClickListener {
        void onItemClick(int position);
    }

    public interface OnSummaryMonthBusinessListener {
        void onSummaryMonthBusinessSuccess(UserTransactionsResponse response);

        void onSummaryMonthBusinessFail(LiveloException exception);
    }
}
