package br.com.pontomobi.livelopontos.ui.myPoints.summaryExpired;

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

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.ListHistogram;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.PointsExpiredResponse;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 21/09/15.
 */
public class SummaryExpiredActivity extends LiveloPontosActivity implements View.OnClickListener {
    public static final String KEY_LIST_EXPIRED = "key_list_expired";

    @Bind(R.id.nothing_to_show_subtitle)
    TextView nothingToShowSubtitle;
    @Bind(R.id.summary_month_nothing_to_show)
    RelativeLayout summaryMonthNothingToShow;
    @Bind(R.id.recycle_view_summary_expired)
    RecyclerView recycleViewSummaryExpired;
    @Bind(R.id.summary_expired_backgound)
    View summaryExpiredBackgound;
    @Bind(R.id.summary_topo_month)
    ImageView summaryTopoExpired;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.summary_expired_first_option)
    TextView summaryExpiredFirstOption;
    @Bind(R.id.summary_expired_second_option)
    TextView summaryExpiredSecondOption;
    @Bind(R.id.summary_expired_third_option)
    TextView summaryExpiredThirdOption;
    @Bind(R.id.summary_expired_fourt_option)
    TextView summaryExpiredFourtOption;

    boolean show = true;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;
    private PointsExpiredResponse pointsExpired;
    private SummaryExpiredBusiness summaryExpiredBusiness;
    private boolean activityEnable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_expired);
        ButterKnife.bind(this);
        configureActionBar();
        init();
        initRecyclerView();
        activityEnable = true;
        summaryExpiredBusiness = new SummaryExpiredBusiness(getBaseContext(), getOnSummaryExpiredBusiness());

        if (getIntent().getExtras().getSerializable(KEY_LIST_EXPIRED) == null) {
            callService();
        } else {
            pointsExpired = (PointsExpiredResponse) getIntent().getExtras().getSerializable(KEY_LIST_EXPIRED);
            summaryExpiredBusiness.orderListHistogram(pointsExpired.getHistogramViewMap());
            setInfo(pointsExpired.getHistogramViewMap());
        }
    }

    @Override
    protected void onStop() {
        activityEnable = false;
        super.onStop();
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(toolbar);
        super.configureActionBar();
        toolbarTitle.setText(getResources().getString(R.string.summary_month_expire_title_toolbar));
        nothingToShowSubtitle.setText(getString(R.string.no_points_to_show_subtitle_expired));
    }

    private void callService() {
        loadingContent.setVisibility(View.VISIBLE);
        summaryExpiredBusiness.callServiceGetPointsExpired();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewSummaryExpired.setLayoutManager(layoutManager);
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
                summaryExpiredBackgound.setVisibility(View.VISIBLE);
                summaryExpiredSecondOption.setVisibility(View.VISIBLE);
                summaryExpiredThirdOption.setVisibility(View.VISIBLE);
                summaryExpiredFourtOption.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn).duration(400).playOn(findViewById(R.id.summary_expired_backgound));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.summary_expired_second_option));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.summary_expired_third_option));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.summary_expired_fourt_option));
            }
        }, 200);

    }

    private void animationCloseButtons(final View view) {

        final AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0f),
                ObjectAnimator.ofFloat(view, "translationY", 1.0f)
        );

        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.summary_expired_second_option));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.summary_expired_third_option));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.summary_expired_fourt_option));
        YoYo.with(Techniques.FadeOut).duration(400).playOn(findViewById(R.id.summary_expired_backgound));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                summaryExpiredSecondOption.setVisibility(View.INVISIBLE);
                summaryExpiredThirdOption.setVisibility(View.INVISIBLE);
                summaryExpiredFourtOption.setVisibility(View.INVISIBLE);
                summaryExpiredBackgound.setVisibility(View.INVISIBLE);
            }
        }, 200);

        set.setDuration(500).start();

    }

    private void init() {
        summaryExpiredFirstOption.setOnClickListener(this);
        summaryExpiredSecondOption.setOnClickListener(this);
        summaryExpiredThirdOption.setOnClickListener(this);
        summaryExpiredFourtOption.setOnClickListener(this);
        summaryExpiredBackgound.setOnClickListener(this);
    }

    private void controlAnimations() {
        if (show) {
            show = false;
            animationButtons(summaryTopoExpired, 0, getSize());
            summaryExpiredFirstOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_up, 0);
        } else {
            show = true;
            summaryExpiredFirstOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_down, 0);
            animationCloseButtons(summaryTopoExpired);
        }
    }

    private int getSize() {
        int density = getResources().getDisplayMetrics().densityDpi;

        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return -200;
            case DisplayMetrics.DENSITY_HIGH:
                return -270;
            case DisplayMetrics.DENSITY_XHIGH:
                return -340;
            case DisplayMetrics.DENSITY_XXHIGH:
                return -520;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return -670;
            default:
                return -370;
        }
    }

    @Override
    public void onClick(View v) {
        int qtdeMeses;
        switch (v.getId()) {
            case R.id.summary_expired_first_option:
                controlAnimations();
                qtdeMeses = checkQtdeMonths(summaryExpiredFirstOption.getText().toString());
                setInfo(pointsExpired.filterByDate(qtdeMeses));
                break;

            case R.id.summary_expired_second_option:
                controlAnimations();

                qtdeMeses = checkQtdeMonths(summaryExpiredSecondOption.getText().toString());
                setInfo(pointsExpired.filterByDate(qtdeMeses));


                summaryExpiredFirstOption.setText(summaryExpiredSecondOption.getText().toString());
                changeOptionsPosition(summaryExpiredSecondOption.getText().toString());
                break;

            case R.id.summary_expired_third_option:
                controlAnimations();

                qtdeMeses = checkQtdeMonths(summaryExpiredThirdOption.getText().toString());
                setInfo(pointsExpired.filterByDate(qtdeMeses));

                summaryExpiredFirstOption.setText(summaryExpiredThirdOption.getText().toString());
                changeOptionsPosition(summaryExpiredThirdOption.getText().toString());
                break;

            case R.id.summary_expired_fourt_option:
                controlAnimations();

                qtdeMeses = checkQtdeMonths(summaryExpiredFourtOption.getText().toString());
                setInfo(pointsExpired.filterByDate(qtdeMeses));

                summaryExpiredFirstOption.setText(summaryExpiredFourtOption.getText().toString());
                changeOptionsPosition(summaryExpiredFourtOption.getText().toString());
                break;

            case R.id.summary_expired_backgound:
                show = false;
                controlAnimations();
                break;
        }
    }

    private void changeOptionsPosition(String optionChosen) {
        if (optionChosen.equals(getResources().getString(R.string.summay_expired_filter_all))) {
            summaryExpiredSecondOption.setText(getString(R.string.summay_expired_filter_next_month));
            summaryExpiredThirdOption.setText(getString(R.string.summay_expired_filter_next_three_month));
            summaryExpiredFourtOption.setText(getString(R.string.summay_expired_filter_next_six_month));

        } else if (optionChosen.equals(getResources().getString(R.string.summay_expired_filter_next_month))) {
            summaryExpiredSecondOption.setText(getString(R.string.summay_expired_filter_all));
            summaryExpiredThirdOption.setText(getString(R.string.summay_expired_filter_next_three_month));
            summaryExpiredFourtOption.setText(getString(R.string.summay_expired_filter_next_six_month));

        } else if (optionChosen.equals(getResources().getString(R.string.summay_expired_filter_next_three_month))) {
            summaryExpiredSecondOption.setText(getString(R.string.summay_expired_filter_all));
            summaryExpiredThirdOption.setText(getString(R.string.summay_expired_filter_next_month));
            summaryExpiredFourtOption.setText(getString(R.string.summay_expired_filter_next_six_month));

        } else {
            summaryExpiredSecondOption.setText(getString(R.string.summay_expired_filter_all));
            summaryExpiredThirdOption.setText(getString(R.string.summay_expired_filter_next_month));
            summaryExpiredFourtOption.setText(getString(R.string.summay_expired_filter_next_three_month));
        }


        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_POINTS_EXPIRE,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_POINTS_EXPIRE,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_FILTER,
                optionChosen
        );
    }

    private int checkQtdeMonths(String text) {
        if (text.equals(getResources().getString(R.string.summay_expired_filter_all))) {
            return 0;
        } else if (text.equals(getResources().getString(R.string.summay_expired_filter_next_month))) {
            return 1;
        } else if (text.equals(getResources().getString(R.string.summay_expired_filter_next_three_month))) {
            return 3;
        } else {
            return 6;
        }

    }

    private void setInfo(ListHistogram listHistogram) {

        if (listHistogram.size() > 0) {
            recycleViewSummaryExpired.setVisibility(View.VISIBLE);
            summaryMonthNothingToShow.setVisibility(View.GONE);
            recycleViewSummaryExpired.setAdapter(new SummaryExpiredAdapter(getBaseContext(), listHistogram));
        } else {
            recycleViewSummaryExpired.setVisibility(View.GONE);
            summaryMonthNothingToShow.setVisibility(View.VISIBLE);
        }
    }

    private OnSummaryExpiredBusiness getOnSummaryExpiredBusiness() {
        OnSummaryExpiredBusiness onSummaryExpiredBusiness = new OnSummaryExpiredBusiness() {
            @Override
            public void onGetExpiredBusinessSuccess(PointsExpiredResponse pointsExpiredResponse) {
                loadingContent.setVisibility(View.GONE);
                pointsExpired = pointsExpiredResponse;
                setInfo(pointsExpired.getHistogramViewMap());
            }

            @Override
            public void onGetExpiredBusinessFail(final LiveloException exception) {
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

        return onSummaryExpiredBusiness;
    }

    private void sessionExpired() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(this, LiveloPontosApp.getInstance().getExpiredSession(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            LoginUtil.clearLogin(getBaseContext());
                            Intent intent = new Intent(SummaryExpiredActivity.this, LoginActivity.class);
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
            dialogCustom.showCustomDialog(SummaryExpiredActivity.this, alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            Log.d("DIALOG", "POSITIVE");
                            callService();
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

    public interface OnSummaryExpiredBusiness {
        void onGetExpiredBusinessSuccess(PointsExpiredResponse pointsExpiredResponse);

        void onGetExpiredBusinessFail(LiveloException exception);
    }
}
