package br.com.pontomobi.livelopontos.ui.orderHistory;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.listener.OnItemClickListener;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.orders.model.Order;
import br.com.pontomobi.livelopontos.service.livelo.orders.model.OrderResponse;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.home.HomeActivity;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.widget.SimpleDividerItemDecoration;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import br.com.pontomobi.livelopontos.util.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vilmar.filho on 12/4/15.
 */
public class RescueHistoryActivity extends LiveloPontosActivity implements View.OnClickListener, OnItemClickListener {

    public static final String FROM_CONFIRMATION = "from_confirmation";

    @Bind(R.id.toolbar_title)
    TextView mToolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rescue_topo_filter)
    ImageView rescueTopoFilter;
    @Bind(R.id.rescue_backgound)
    View rescueBackgound;
    @Bind(R.id.rescue_filter_first_option)
    TextView rescueFilterFirstOption;
    @Bind(R.id.rescue_filter_second_option)
    TextView rescueFilterSecondOption;
    @Bind(R.id.rescue_filter_third_option)
    TextView rescueFilterThirdOption;
    @Bind(R.id.rescue_filter_fourth_option)
    TextView rescueFilterFourthOption;
    @Bind(R.id.rescue_filter_fifth_option)
    TextView rescueFilterFifthOption;
    @Bind(R.id.rescue_filter_sixth_option)
    TextView rescueFilterSixthOption;
    @Bind(R.id.recycle_view_rescue_history)
    RecyclerView recycleViewRescueHistory;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;
    @Bind(R.id.rescue_nothing_to_show)
    RelativeLayout rescueNothingToShow;
    @Bind(R.id.container_filter)
    LinearLayout containerFilter;
    //@Bind(R.id.btn_go_catalog)
    //AppCompatButton btnGoCatalog;


    @Bind(R.id.nothing_to_show_title) TextView nothingShowTitle;
    @Bind(R.id.nothing_to_show_subtitle) TextView nothingShowSubtitle;

    private RescueHistoryAdapter adapter;
    private OrderBusiness mOrderBusiness;

    private boolean show = true;
    private boolean loadigDataFromChache = false;
    private boolean errorShow = false;

    private List<Order> mListOrdersAll;
    private List<Order> mListOrders;

    private boolean fromConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue_history);

        ButterKnife.bind(this);

        loadigDataFromChache = false;

        mListOrders = new ArrayList<>();
        mListOrdersAll = new ArrayList<>();

        setSupportActionBar(mToolbar);

        mToolbarTitle.setText(R.string.rescue_history_title);

        if(getIntent().getExtras() != null) {
            fromConfirmation = getIntent().getExtras().getBoolean(FROM_CONFIRMATION, false);
        }

        customizeToolbarWithButtonBack();

        init();
        initRecyclerView();

        mOrderBusiness = new OrderBusiness(this, getOnServiceOrdersListener());
        callServices();

        //setInfo();
    }

    private void init() {
        rescueFilterFirstOption.setOnClickListener(this);
        rescueFilterSecondOption.setOnClickListener(this);
        rescueFilterThirdOption.setOnClickListener(this);
        rescueFilterFourthOption.setOnClickListener(this);
        rescueFilterFifthOption.setOnClickListener(this);
        rescueFilterSixthOption.setOnClickListener(this);
        rescueFilterSixthOption.setSelected(true);
        rescueBackgound.setOnClickListener(this);
    }

    private void callServices() {
        loadingContent.setVisibility(View.VISIBLE);
        mOrderBusiness.callServiceOrders();
        errorShow = false;
    }

    private void initRecyclerView() {
        recycleViewRescueHistory.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewRescueHistory.setLayoutManager(layoutManager);
    }

    private void setInfo(boolean isFilter) {
        if(loadingContent != null) loadingContent.setVisibility(View.GONE);

        if (mListOrders.size() > 0) {
            recycleViewRescueHistory.setVisibility(View.VISIBLE);
            rescueNothingToShow.setVisibility(View.GONE);
            adapter = new RescueHistoryAdapter(this, mListOrders);
            adapter.setOnItemClickListener(this);
            recycleViewRescueHistory.setAdapter(adapter);
        } else {
            recycleViewRescueHistory.setVisibility(View.GONE);
            rescueNothingToShow.setVisibility(View.VISIBLE);

            if(isFilter){
                nothingShowTitle.setText(R.string.rescue_history_nothing_to_show_filter);
                nothingShowSubtitle.setVisibility(View.GONE);
            } else {
                nothingShowTitle.setText(R.string.rescue_history_no_rescue_to_show_title);
                nothingShowSubtitle.setVisibility(View.VISIBLE);
            }
        }

    }

    @OnClick(R.id.btn_go_catalog)
    public void goCatalog(){
        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_RESCUE_HISTORY,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_RESCUE_HISTORY,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_PRODUCTS_CATALOG,
                ""
        );

        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra(HomeActivity.GO_TO_CATALOG, true);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (fromConfirmation) {
                startActivity(new Intent(RescueHistoryActivity.this, HomeActivity.class));
            }

            RescueHistoryActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.rescue_filter_first_option && view.getId() != R.id.rescue_backgound)
            clearStateDrawableFilter();

        switch (view.getId()) {
            case R.id.rescue_filter_first_option: //FILTRAR POR
                //filterByStatus("");
                controlAnimations();
                break;

            case R.id.rescue_filter_second_option: //Pedidos em processamento
                LiveloPontosApp.getInstance().sendTrackerEvent(Constants.GoogleAnalytisEvents.EVENT_SCREEN_RESCUE_HISTORY, Constants.GoogleAnalytisEvents.EVENT_CATEGORY_RESCUE_HISTORY,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_FILTER, "Pedidos em processamento");

                filterByStatus(Constants.StatusOrder.PROCESSING_STATUS,
                        Constants.StatusOrder.PENDING_CUSTOMER_RETURN_STATUS,
                        Constants.StatusOrder.PENDING_CUSTOMER_ACTION_STATUS,
                        Constants.StatusOrder.PENDING_MERCHANT_ACTION_STATUS);

                controlAnimations();
                rescueFilterSecondOption.setSelected(true);
                break;

            case R.id.rescue_filter_third_option: //Pedidos enviados
                LiveloPontosApp.getInstance().sendTrackerEvent(Constants.GoogleAnalytisEvents.EVENT_SCREEN_RESCUE_HISTORY, Constants.GoogleAnalytisEvents.EVENT_CATEGORY_RESCUE_HISTORY,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_FILTER, "Pedidos enviados");

                filterByStatus(Constants.StatusOrder.SUBMITTED_STATUS);

                controlAnimations();
                rescueFilterThirdOption.setSelected(true);
                break;

            case R.id.rescue_filter_fourth_option: //Pedidos finalizados
                LiveloPontosApp.getInstance().sendTrackerEvent(Constants.GoogleAnalytisEvents.EVENT_SCREEN_RESCUE_HISTORY, Constants.GoogleAnalytisEvents.EVENT_CATEGORY_RESCUE_HISTORY,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_FILTER, "Pedidos finalizados");

                filterByStatus(Constants.StatusOrder.NO_PENDING_ACTION_STATUS);

                controlAnimations();
                rescueFilterFourthOption.setSelected(true);
                break;

            case R.id.rescue_filter_fifth_option: //Pedidos cancelados
                LiveloPontosApp.getInstance().sendTrackerEvent(Constants.GoogleAnalytisEvents.EVENT_SCREEN_RESCUE_HISTORY, Constants.GoogleAnalytisEvents.EVENT_CATEGORY_RESCUE_HISTORY,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_FILTER, "Pedidos cancelados");

                filterByStatus(Constants.StatusOrder.PENDING_REMOVE_STATUS,
                        Constants.StatusOrder.REMOVED_STATUS);

                controlAnimations();
                rescueFilterFifthOption.setSelected(true);
                break;

            case R.id.rescue_filter_sixth_option: //Todos
                LiveloPontosApp.getInstance().sendTrackerEvent(Constants.GoogleAnalytisEvents.EVENT_SCREEN_RESCUE_HISTORY, Constants.GoogleAnalytisEvents.EVENT_CATEGORY_RESCUE_HISTORY,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_FILTER, "Todos");

                filterByStatus(Constants.StatusOrder.ALL_STATUS);
                controlAnimations();
                rescueFilterSixthOption.setSelected(true);
                break;

            case R.id.rescue_backgound:
                show = false;
                controlAnimations();
                break;
        }

    }

    private void filterByStatus(String... status) {
        mListOrders = mOrderBusiness.filterListByStatus(mListOrdersAll, status);
        setInfo(true);
    }

    private void clearStateDrawableFilter() {
        rescueFilterSecondOption.setSelected(false);
        rescueFilterThirdOption.setSelected(false);
        rescueFilterFourthOption.setSelected(false);
        rescueFilterFifthOption.setSelected(false);
        rescueFilterSixthOption.setSelected(false);
    }

    private void controlAnimations() {
        if (show) {
            show = false;
            animationButtons(rescueTopoFilter, 0, getSize());
            rescueFilterFirstOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_up, 0);
        } else {
            show = true;
            rescueFilterFirstOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drop_down, 0);
            animationCloseButtons(rescueTopoFilter);
        }
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
                rescueBackgound.setVisibility(View.VISIBLE);
                rescueFilterSecondOption.setVisibility(View.VISIBLE);
                rescueFilterThirdOption.setVisibility(View.VISIBLE);
                rescueFilterFourthOption.setVisibility(View.VISIBLE);
                rescueFilterFifthOption.setVisibility(View.VISIBLE);
                rescueFilterSixthOption.setVisibility(View.VISIBLE);

                YoYo.with(Techniques.FadeIn).duration(400).playOn(findViewById(R.id.rescue_backgound));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.rescue_filter_second_option));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.rescue_filter_third_option));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.rescue_filter_fourth_option));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.rescue_filter_fifth_option));
                YoYo.with(Techniques.SlideInDown).duration(400).playOn(findViewById(R.id.rescue_filter_sixth_option));
            }
        }, 200);

    }

    private void animationCloseButtons(final View view) {

        final AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0f),
                ObjectAnimator.ofFloat(view, "translationY", 1.0f)
        );

        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.rescue_filter_second_option));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.rescue_filter_third_option));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.rescue_filter_fourth_option));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.rescue_filter_fifth_option));
        YoYo.with(Techniques.SlideOutUp).duration(400).playOn(findViewById(R.id.rescue_filter_sixth_option));
        YoYo.with(Techniques.FadeOut).duration(400).playOn(findViewById(R.id.rescue_backgound));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rescueFilterSecondOption.setVisibility(View.INVISIBLE);
                rescueFilterThirdOption.setVisibility(View.INVISIBLE);
                rescueFilterFourthOption.setVisibility(View.INVISIBLE);
                rescueFilterFifthOption.setVisibility(View.INVISIBLE);
                rescueFilterSixthOption.setVisibility(View.INVISIBLE);
                rescueBackgound.setVisibility(View.INVISIBLE);
            }
        }, 200);

        set.setDuration(500).start();

    }

    private int getSize() {
        int density = getResources().getDisplayMetrics().densityDpi;

        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return -450; //old value -200
            case DisplayMetrics.DENSITY_HIGH:
                return -470; //old value -270
            case DisplayMetrics.DENSITY_XHIGH:
                return -590; //old value -340
            case DisplayMetrics.DENSITY_XXHIGH:
                return -800; //old value -520
            case DisplayMetrics.DENSITY_XXXHIGH:
                return -1050; //old value -520

            default:
                return -620; //old value -370
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(this, OrderDetailActivity.class);
        i.putExtra(OrderDetailActivity.BUNDLE_ORDER, mListOrders.get(position));
        startActivity(i);
    }

    private OrderBusiness.OnServiceOrdersListener getOnServiceOrdersListener() {
        return new OrderBusiness.OnServiceOrdersListener() {

            @Override
            public void onGetOrdersSuccess(OrderResponse orderResponse) {
                if (orderResponse != null) {
                    for (int i = 0; i < orderResponse.getOrders().size(); i++) {
                        mListOrders.add(orderResponse.getOrders().get(i));
                        mListOrdersAll.add(orderResponse.getOrders().get(i));
                    }
                }
                setInfo(false);
            }

            @Override
            public void onGetOrdersFail(LiveloException exception) {
                errorServiceReturn(exception);
            }
        };
    }

    private void errorServiceReturn(final LiveloException exception) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingContent.setVisibility(View.GONE);
                if (exception.getErrorCode() == LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR) {
                    sessionExpired();
                } else {
                    if (!loadigDataFromChache) {

                        if (!errorShow) {
                            errorShow = true;
                            showDialogError(exception.getAlertToShow(RescueHistoryActivity.this));
                        }
                    }
                }
            }
        }, 500);
    }

    private void showDialogError(Alert alert) {
        if (RescueHistoryActivity.this == null) {
            return;
        }

        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(RescueHistoryActivity.this, alert, true,
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

    private void sessionExpired() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(RescueHistoryActivity.this, LiveloPontosApp.getInstance().getExpiredSession(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            LoginUtil.clearLogin(RescueHistoryActivity.this);
                            Intent intent = new Intent(RescueHistoryActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            RescueHistoryActivity.this.finish();
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

    private void maKeAnalytics(Order order) {
        //Total
        long cashAmoutCard = getCardCashAmount(order);
        String total = StringUtil.formatPoints(order.getPriceInfo().getAmountInPoints()) + " + " + StringUtil.formatCash(cashAmoutCard);

        //Date
        String date = DateUtil.getFormatDateToShowJustDate(new Timestamp(order.getCreationTime()));

        //Status
        String status = getStatusOrder(order.getStateAsString());

        String label = order.getId() + " ** " + date + " ** " + status + " ** " + total;

        LiveloPontosApp.getInstance().sendTrackerEvent(Constants.GoogleAnalytisEvents.EVENT_SCREEN_RESCUE_HISTORY, Constants.GoogleAnalytisEvents.EVENT_CATEGORY_RESCUE_HISTORY,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_RESCUE, label);
    }

    private long getCardCashAmount(Order o){
        if (o.getPaymentGroups() != null && o.getPaymentGroups().size() > 1) {

            for (int i = 0; i < o.getPaymentGroups().size(); i++) {
                if (o.getPaymentGroups().get(i).getCreditCardType() != null && o.getPaymentGroups().get(i).getCreditCardNumber() != null) {
                    return o.getPaymentGroups().get(i).getAmount();
                }
            }
        }

        return 0;
    }

    private String getStatusOrder(String status){
        switch (status){
            case Constants.StatusOrder.PROCESSING_STATUS:
            case Constants.StatusOrder.PENDING_CUSTOMER_RETURN_STATUS:
            case Constants.StatusOrder.PENDING_CUSTOMER_ACTION_STATUS:
            case Constants.StatusOrder.PENDING_MERCHANT_ACTION_STATUS:
                return getString(EnumStatus.PROCESS.getLabel());

            case Constants.StatusOrder.SUBMITTED_STATUS:
                return getString(EnumStatus.SEND.getLabel());

            case Constants.StatusOrder.NO_PENDING_ACTION_STATUS:
                return getString(EnumStatus.FINALIZED.getLabel());

            case Constants.StatusOrder.PENDING_REMOVE_STATUS:
            case Constants.StatusOrder.REMOVED_STATUS:
                return getString(EnumStatus.CANCELED.getLabel());

            default:
                return "";
        }
    }
}
