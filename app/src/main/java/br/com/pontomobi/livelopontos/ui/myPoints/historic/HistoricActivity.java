package br.com.pontomobi.livelopontos.ui.myPoints.historic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.model.HistoricExtract;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.TransactionLineItem;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionsResponse;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.myPoints.historicdetail.HistoricDetailActivity;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by selem.gomes on 25/09/15.
 */
public class HistoricActivity extends LiveloPontosActivity {
    public static final int FILTER_BY_ALL = 0;
    public static final int FILTER_BY_PARTNER = 1;
    public static final int FILTER_BY_POINTS_ACCUMULATED = 2;
    public static final int FILTER_BY_POINTS_RESCUED = 3;
    public static final int FILTER_BY_POINTS_EXPIRED = 4;

    @Bind(R.id.recycle_view_historic)
    RecyclerView recycleViewHistoric;
    @Bind(R.id.historic_backgound)
    View historicBackgound;
    @Bind(R.id.historic_top)
    ImageView historicTop;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.historic_order_by_title)
    TextView historicOrderByTitle;
    @Bind(R.id.historic_filter_by_title)
    TextView historicFilterByTitle;
    @Bind(R.id.historic_filter_title)
    LinearLayout historicFilterTitle;
    @Bind(R.id.historic_order_by_most_recent)
    TextView historicOrderByMostRecent;
    @Bind(R.id.historic_order_by_most_old)
    TextView historicOrderByMostOld;
    @Bind(R.id.historic_filter_by_all)
    TextView historicFilterByAll;
    @Bind(R.id.historic_filter_by_partner_name)
    TextView historicFilterByPartnerName;
    @Bind(R.id.historic_filter_by_points_accumulated)
    TextView historicFilterByPointsAccumulated;
    @Bind(R.id.historic_filter_by_points_rescued)
    TextView historicFilterByPointsRescued;
    @Bind(R.id.historic_filter_by_points_expired)
    TextView historicFilterByPointsExpired;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;
    @Bind(R.id.historic_nothing_to_show)
    LinearLayout historicNothingToShow;

    private boolean showOrderBy = true;
    private boolean showFilterBy = true;
    private HistoricBusiness historicBusiness;
    private UserTransactionsResponse userTransactions;
    private int filterBy = FILTER_BY_ALL;
    private boolean orderMostRecent = true;
    private List<HistoricExtract> currentHistoricExtractList;
    private boolean activityEnable = false;

    private int idFilterSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);
        ButterKnife.bind(this);
        configureActionBar();
        initRecyclerView();
        activityEnable = true;
        historicBusiness = new HistoricBusiness(getOnHistoricBusinessListener(), getBaseContext());
        setDrawableOnFilter(historicFilterByAll);
        setDrawableOnOrder(historicOrderByMostRecent);
        callService();

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
        toolbarTitle.setText(getString(R.string.historic_title_toolbar));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
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
        recycleViewHistoric.setLayoutManager(layoutManager);
    }

    @OnClick(R.id.historic_order_by_title)
    public void onClickOrderByTitle() {
        idFilterSelected = R.id.historic_order_by_title;
        if (!showFilterBy) {
            controlAnimationsFilterBy();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    controlAnimationsOrderBy();
                }
            }, 300);
        } else {
            controlAnimationsOrderBy();
        }
    }

    @OnClick(R.id.historic_order_by_most_old)
    public void onClickOrderByMostRecent() {
        setDrawableOnOrder(historicOrderByMostRecent);
        controlAnimationsOrderBy();

        if (!orderMostRecent) {
            Collections.reverse(currentHistoricExtractList);
            setListInfo(currentHistoricExtractList);
        }
        sendEventOrderBy(historicOrderByMostRecent.getText().toString());
        orderMostRecent = true;
    }

    @OnClick(R.id.historic_order_by_most_recent)
    public void onClickOrderByMostOld() {
        setDrawableOnOrder(historicOrderByMostOld);
        controlAnimationsOrderBy();
        if (orderMostRecent) {
            Collections.reverse(currentHistoricExtractList);
            setListInfo(currentHistoricExtractList);
        }
        sendEventOrderBy(historicOrderByMostOld.getText().toString());
        orderMostRecent = false;
    }

    private void sendEventOrderBy(String orderBy) {
        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_ORDER_BY,
                orderBy
        );
    }


    @OnClick(R.id.historic_filter_by_title)
    public void onClickFilterByTitle() {
        idFilterSelected = R.id.historic_filter_by_title;
        if (!showOrderBy) {
            controlAnimationsOrderBy();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    controlAnimationsFilterBy();
                }
            }, 300);
        } else {
            controlAnimationsFilterBy();
        }
    }

    @OnClick(R.id.historic_filter_by_all)
    public void onClickFilterByAll() {
        enableOrDisableOrderBy(true);
        marckFilterSelected(false);
        filterBy = FILTER_BY_ALL;
        setDrawableOnFilter(historicFilterByAll);
        controlAnimationsFilterBy();
        setListInfo(HistoricExtract.historicExtract(userTransactions.getOutput().getTransactionLineItemList(),
                "", orderMostRecent));
        sendEventFilterBy(historicFilterByAll.getText().toString());
    }

    @OnClick(R.id.historic_filter_by_partner_name)
    public void onClickFilterByPartnerName() {
        marckFilterSelected(true);
        enableOrDisableOrderBy(false);
        filterBy = FILTER_BY_PARTNER;
        setDrawableOnFilter(historicFilterByPartnerName);
        controlAnimationsFilterBy();

        setListInfo(HistoricExtract.historicExtractByPartner(userTransactions.getOutput().getTransactionLineItemList()));
        sendEventFilterBy(historicFilterByPartnerName.getText().toString());
    }


    @OnClick(R.id.historic_filter_by_points_accumulated)
    public void onClickFilterByAccumulated() {
        marckFilterSelected(true);
        enableOrDisableOrderBy(true);
        filterBy = FILTER_BY_POINTS_ACCUMULATED;
        setDrawableOnFilter(historicFilterByPointsAccumulated);
        controlAnimationsFilterBy();
        setListInfo(HistoricExtract.historicExtract(userTransactions.getOutput().getTransactionLineItemList(),
                UserTransactionsResponse.TRANSACTION_TYPE_ACCUMULATED, orderMostRecent));
        sendEventFilterBy(historicFilterByPointsAccumulated.getText().toString());
    }

    @OnClick(R.id.historic_filter_by_points_rescued)
    public void onClickFilterByRescued() {
        marckFilterSelected(true);
        enableOrDisableOrderBy(true);
        filterBy = FILTER_BY_POINTS_RESCUED;
        setDrawableOnFilter(historicFilterByPointsRescued);
        controlAnimationsFilterBy();
        setListInfo(HistoricExtract.historicExtract(userTransactions.getOutput().getTransactionLineItemList(),
                UserTransactionsResponse.TRANSACTION_TYPE_RESCUED, orderMostRecent));
        sendEventFilterBy(historicFilterByPointsRescued.getText().toString());
    }

    @OnClick(R.id.historic_filter_by_points_expired)
    public void onClickFilterByExpired() {
        marckFilterSelected(true);
        enableOrDisableOrderBy(true);
        filterBy = FILTER_BY_POINTS_EXPIRED;
        setDrawableOnFilter(historicFilterByPointsExpired);
        controlAnimationsFilterBy();
        setListInfo(HistoricExtract.historicExtract(userTransactions.getOutput().getTransactionLineItemList(),
                UserTransactionsResponse.TRANSACTION_TYPE_EXPIRE, orderMostRecent));

        sendEventFilterBy(historicFilterByPointsExpired.getText().toString());
    }

    @OnClick(R.id.historic_backgound)
    public void closeFilter(){
        switch (idFilterSelected){
            case R.id.historic_order_by_title:
                controlAnimationsOrderBy();
                break;

            case R.id.historic_filter_by_title:
                controlAnimationsFilterBy();
                break;
        }
    }

    private void marckFilterSelected(boolean selected) {
        if (selected) {
            historicFilterByTitle.setTextColor(getResources().getColor(R.color.filter_by_title_selected_text_color));
        } else {
            historicFilterByTitle.setTextColor(getResources().getColor(R.color.filter_by_title_text_color));
        }
    }

    private void sendEventFilterBy(String filterBy) {
        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_POINTS,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_FILTER_BY,
                filterBy
        );
    }

    private void restoreDrawableFilter() {
        historicFilterByAll.setCompoundDrawablesWithIntrinsicBounds(R.drawable.filtro_enviados_off, 0, 0, 0);
        historicFilterByPartnerName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.filtro_enviados_off, 0, 0, 0);
        historicFilterByPointsAccumulated.setCompoundDrawablesWithIntrinsicBounds(R.drawable.filtro_enviados_off, 0, 0, 0);
        historicFilterByPointsExpired.setCompoundDrawablesWithIntrinsicBounds(R.drawable.filtro_enviados_off, 0, 0, 0);
        historicFilterByPointsRescued.setCompoundDrawablesWithIntrinsicBounds(R.drawable.filtro_enviados_off, 0, 0, 0);
    }

    private void restoreDrawableOrder() {
        historicOrderByMostRecent.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_recentes, 0, 0, 0);
        historicOrderByMostOld.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_antigos, 0, 0, 0);
    }

    private void setDrawableOnFilter(TextView textView) {
        restoreDrawableFilter();
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.filtro_enviados_on, 0, 0, 0);
    }

    private void setDrawableOnOrder(TextView textView) {
        restoreDrawableOrder();
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.filtro_enviados_on, 0, 0, 0);
    }


    private void enableOrDisableOrderBy(boolean enable) {
        if (enable) {
            historicOrderByTitle.setEnabled(true);
            historicOrderByTitle.setTextColor(getResources().getColor(R.color.order_by_title_enable_text_color));
            historicOrderByTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_down, 0);
        } else {
            historicOrderByTitle.setEnabled(false);
            historicOrderByTitle.setTextColor(getResources().getColor(R.color.order_by_title_disable_text_color));
            historicOrderByTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_down_3, 0);
        }

    }

    private void controlAnimationsOrderBy() {
        if (showOrderBy) {
            showOrderBy = false;
            animationButtons(historicTop, 0, getSizeOrder(), true);
            historicOrderByTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_up, 0);
        } else {
            showOrderBy = true;
            animationCloseButtons(historicTop, true);
            historicOrderByTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_down, 0);
        }
    }

    private void controlAnimationsFilterBy() {
        if (showFilterBy) {
            showFilterBy = false;
            if (filterBy == FILTER_BY_ALL) {
                historicFilterByTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_up, 0);
            } else {
                historicFilterByTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_up_2, 0);
            }
            animationButtons(historicTop, 0, getSizeFilter(), false);
        } else {
            showFilterBy = true;
            if (filterBy == FILTER_BY_ALL) {
                historicFilterByTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_down, 0);
            } else {
                historicFilterByTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_down_2, 0);
            }
            animationCloseButtons(historicTop, false);
        }
    }

    private int getSizeOrder() {
        int density = getResources().getDisplayMetrics().densityDpi;

        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return -100;
            case DisplayMetrics.DENSITY_HIGH:
                return -180;
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


    private int getSizeFilter() {
        int density = getResources().getDisplayMetrics().densityDpi;

        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return -300;
            case DisplayMetrics.DENSITY_HIGH:
                return -440;
            case DisplayMetrics.DENSITY_XHIGH:
                return -595;
            case DisplayMetrics.DENSITY_XXHIGH:
                return -890;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return -1200;

            default:
                return -370;
        }
    }

    private void animationButtons(View view, int xDest, int yDest, final boolean isFromOrder) {

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, (xDest * -1)),
                ObjectAnimator.ofFloat(view, "translationY", 0, (yDest * -1))
        );

        set.setDuration(500).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                historicBackgound.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn).duration(400).playOn(findViewById(R.id.historic_backgound));

                if (isFromOrder) {
                    showOptionsOrderBy();
                } else {
                    showOptionsFilterBy();
                }
            }
        }, 200);

    }

    private void animationCloseButtons(final View view, final boolean isFromOrder) {

        final AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0f),
                ObjectAnimator.ofFloat(view, "translationY", 1.0f)
        );

        YoYo.with(Techniques.FadeOut).duration(400).playOn(findViewById(R.id.historic_backgound));
        if (isFromOrder) {
            hideOptionsOrderBy();
        } else {
            hideOptionsFilterBy();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                historicBackgound.setVisibility(View.INVISIBLE);

                if (isFromOrder) {
                    historicOrderByMostRecent.setVisibility(View.INVISIBLE);
                    historicOrderByMostOld.setVisibility(View.INVISIBLE);
                } else {
                    historicFilterByAll.setVisibility(View.INVISIBLE);
                    historicFilterByPartnerName.setVisibility(View.INVISIBLE);
                    historicFilterByPointsAccumulated.setVisibility(View.INVISIBLE);
                    historicFilterByPointsExpired.setVisibility(View.INVISIBLE);
                    historicFilterByPointsRescued.setVisibility(View.INVISIBLE);
                }
            }
        }, 200);

        set.setDuration(500).start();
    }


    private void showOptionsOrderBy() {
        historicOrderByMostRecent.setVisibility(View.VISIBLE);
        historicOrderByMostOld.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.historic_order_by_most_recent));
        YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.historic_order_by_most_old));
    }

    private void hideOptionsOrderBy() {
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.historic_order_by_most_recent));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.historic_order_by_most_old));
    }

    private void showOptionsFilterBy() {
        historicFilterByAll.setVisibility(View.VISIBLE);
        historicFilterByPartnerName.setVisibility(View.VISIBLE);
        historicFilterByPointsAccumulated.setVisibility(View.VISIBLE);
        historicFilterByPointsExpired.setVisibility(View.VISIBLE);
        historicFilterByPointsRescued.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.historic_filter_by_all));
        YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.historic_filter_by_partner_name));
        YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.historic_filter_by_points_accumulated));
        YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.historic_filter_by_points_expired));
        YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.historic_filter_by_points_rescued));
    }

    private void hideOptionsFilterBy() {
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.historic_filter_by_all));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.historic_filter_by_partner_name));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.historic_filter_by_points_accumulated));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.historic_filter_by_points_expired));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.historic_filter_by_points_rescued));
    }


    private void callService() {
        loadingContent.setVisibility(View.VISIBLE);
        historicBusiness.callServiceGetListUserTransactions();
    }

    private OnHistoricBusinessListener getOnHistoricBusinessListener() {
        OnHistoricBusinessListener onHistoricBusinessListener = new OnHistoricBusinessListener() {
            @Override
            public void onHistoricBusinessSuccess(UserTransactionsResponse response) {
                loadingContent.setVisibility(View.GONE);
                userTransactions = response;
                //setListInfo(HistoricExtract.historicExtract(response.getOutput().getTransactionLineItemList(), "", orderMostRecent));

                List<TransactionLineItem> transactionLineItems = userTransactions.getOutput().getTransactionLineItemList();

                userTransactions.orderListByDate();
                Collections.reverse(transactionLineItems);

                setListInfo(HistoricExtract.historicExtract(transactionLineItems, "", orderMostRecent));
            }

            @Override
            public void onHistoricBusinessFail(final LiveloException exception) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!activityEnable) {
                            return;
                        }

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

        return onHistoricBusinessListener;
    }

    private void setListInfo(List<HistoricExtract> historicExtractList) {
        currentHistoricExtractList = historicExtractList;
        if (historicExtractList.size() > 0) {
            recycleViewHistoric.setVisibility(View.VISIBLE);
            historicNothingToShow.setVisibility(View.GONE);
            recycleViewHistoric.setAdapter(new HistoricAdapter(getBaseContext(), historicExtractList, getOnHistoricClickListener(), filterBy));
        } else {
            recycleViewHistoric.setVisibility(View.GONE);
            historicNothingToShow.setVisibility(View.VISIBLE);
        }
    }

    private void sessionExpired() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(this, LiveloPontosApp.getInstance().getExpiredSession(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            LoginUtil.clearLogin(getBaseContext());
                            Intent intent = new Intent(HistoricActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
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

    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(HistoricActivity.this, alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callService();
                        }

                        @Override
                        public void onNegativeClick() {

                        }

                        @Override
                        public void onBackPressedInDialog() {
                            onBackPressed();
                        }
                    });
        }
    }

    private OnHistoricClickListener getOnHistoricClickListener() {
        OnHistoricClickListener onHistoricClickListener = new OnHistoricClickListener() {
            @Override
            public void onItemClick(int position) {
                if (filterBy != FILTER_BY_PARTNER) {
                    return;
                }
                Intent intent = new Intent(HistoricActivity.this, HistoricDetailActivity.class);
                intent.putExtra(HistoricDetailActivity.KEY_HISTORIC_EXTRACT_ITEM, currentHistoricExtractList.get(position));
                startActivity(intent);

                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_EXTRACT_HOSTORY,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_EXTRACT_HOSTORY,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_PARTNER_DETAIL,
                        ""
                );

            }
        };

        return onHistoricClickListener;

    }


    public interface OnHistoricClickListener {
        void onItemClick(int position);
    }

    public interface OnHistoricBusinessListener {
        void onHistoricBusinessSuccess(UserTransactionsResponse response);

        void onHistoricBusinessFail(LiveloException exception);
    }
}
