package br.com.pontomobi.livelopontos.ui.orderHistory;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.service.livelo.orders.model.CommerceItem;
import br.com.pontomobi.livelopontos.service.livelo.orders.model.Order;
import br.com.pontomobi.livelopontos.ui.widget.ItemOrderProductDescriptionView;
import br.com.pontomobi.livelopontos.ui.widget.ItemProductView;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vilmar.filho on 12/7/15.
 */
public class OrderDetailActivity extends LiveloPontosActivity {

    public static final String BUNDLE_ORDER = "bundle_order";

    @Bind(R.id.toolbar_title)
    TextView mToolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.tv_order_date)
    TextView mDateOrder;
    @Bind(R.id.tv_order_number)
    TextView mNumberOrder;
    @Bind(R.id.tv_order_receiver)
    TextView mReceiverOrder;
    @Bind(R.id.tv_order_total)
    TextView mTotalOrder;
    @Bind(R.id.tv_status)
    TextView mStatusOrder;
    @Bind(R.id.tv_order_address)
    TextView mAddressOrder;
    @Bind(R.id.tv_order_payment)
    TextView mPaymentOrder;
    @Bind(R.id.container_product)
    LinearLayout mContainerProduct;
    @Bind(R.id.total_order_description)
    TextView mTotalOrderFinal;
    @Bind(R.id.order_border)
    View mOrderBorder;

    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mToolbarTitle.setText(R.string.product_detail_title);

        customizeToolbarWithButtonBack();

        if (getIntent() != null) {
            mOrder = (Order) getIntent().getSerializableExtra(BUNDLE_ORDER);
        }

        fillViews();
    }

    private void fillViews() {
        String date = DateUtil.getFormatDateToShowJustDate(new Timestamp(mOrder.getSubmittedDate().getTime()));
        mDateOrder.setText(date);

        mNumberOrder.setText(mOrder.getId());

        String fullName = (mOrder.getCustomerData() != null) ? mOrder.getCustomerData().getFullName() : "";
        mReceiverOrder.setText(fullName);

        EnumStatus enumOrder = getEnumStatus(mOrder.getStateAsString());
        mStatusOrder.setText(enumOrder.getLabel());
        mStatusOrder.setPadding(10, 10, 10, 10);
        mStatusOrder.setBackgroundDrawable(getResources().getDrawable(enumOrder.getDrawable()));

        mOrderBorder.setBackgroundColor(ContextCompat.getColor(this, getEnumStatus(mOrder.getStateAsString()).getColor()));


        if (mOrder.getShippingGroups() != null && mOrder.getShippingGroups().size() > 0 && mOrder.getShippingGroups().get(0).getShippingAddress() != null) {

            StringBuilder sbAddresOrder = new StringBuilder();
            sbAddresOrder.append(mOrder.getCustomerData() != null ? mOrder.getCustomerData().getFullName() + "," : "");
            sbAddresOrder.append(mOrder.getShippingGroups().get(0).getShippingAddress().getAllAddress());
            mAddressOrder.setText(sbAddresOrder.toString());
        }


        if (mOrder.getPaymentGroups() != null && mOrder.getPaymentGroups().size() > 1) {
            String payment = "";
            for (int i = 0; i < mOrder.getPaymentGroups().size(); i++) {
                if (mOrder.getPaymentGroups().get(i).getCreditCardType() != null && mOrder.getPaymentGroups().get(i).getCreditCardNumber() != null) {
                    payment = mOrder.getPaymentGroups().get(i).getCreditCardType() +
                            " \n" + "xxxx.xxxx.xxxx." + mOrder.getPaymentGroups().get(i).getCreditCardNumber() + " "  + StringUtil.formatCash(mOrder.getPriceInfo().getCashAmount());
                }
            }
            mPaymentOrder.setText(payment);
        }

        String cash;
        if (mOrder.getPriceInfo().getCashAmount() > 0) {
            cash = StringUtil.formatPoints(mOrder.getPriceInfo().getAmountInPoints()) + " + " + StringUtil.formatCash(mOrder.getPriceInfo().getCashAmount());
        } else {
            cash = StringUtil.formatPoints(mOrder.getPriceInfo().getAmount());
        }

        mTotalOrder.setText(cash);
        mTotalOrderFinal.setText(cash);

        createProducts();


    }

    private void createProducts() {
        StringBuilder sb = new StringBuilder();
        HashMap<String, List<CommerceItem>> organizedItem = getOrganizedOrder(mOrder.getCommerceItems());

        for (String partner : organizedItem.keySet()) {
            ItemProductView item = new ItemProductView(this, partner);
            for (CommerceItem commerceItem : organizedItem.get(partner)){
                //Ate que o status do produto esteja voltando no servico;
                commerceItem.setStatusProduct(getResources().getString(getEnumStatus(mOrder.getStateAsString()).getLabel()));

                item.getContainer().addView(new ItemOrderProductDescriptionView(this, commerceItem, getEnumStatus(mOrder.getStateAsString())));

                sb.append(" ** ");

                //Partner name
                if (!TextUtils.isEmpty(commerceItem.getPartnerDisplayName()))
                    sb.append(commerceItem.getPartnerDisplayName());

                //Product name
                if (!TextUtils.isEmpty(commerceItem.getProductName())) {
                    sb.append(" ** ");
                    sb.append(commerceItem.getProductName());
                }

                //Value Product
                String value = StringUtil.formatPoints(commerceItem.getCommercePriceInfo().getAmount());
                sb.append(" ** ");
                sb.append(value);

                //Quantity Product
                int qty = commerceItem.getQuantity();
                sb.append(" ** ");
                sb.append(qty);
            }
            mContainerProduct.addView(item);
        }

        String label = mOrder.getId() + " ** " + sb.toString();
        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_RESCUE_HISTORY,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_RESCUE_HISTORY,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_RESCUE, label);

    }

    private HashMap<String, List<CommerceItem>> getOrganizedOrder(List<CommerceItem> commerceItemList) {
        HashMap<String, List<CommerceItem>> mOrderOrganized = new HashMap<>();

        for (int i = 0; i < commerceItemList.size(); i++){
            if ( mOrderOrganized.get(commerceItemList.get(i).getPartnerDisplayName()) == null){
                mOrderOrganized.put(commerceItemList.get(i).getPartnerDisplayName(), new ArrayList<CommerceItem>());
            }
            mOrderOrganized.get(commerceItemList.get(i).getPartnerDisplayName()).add(commerceItemList.get(i));
        }

        return mOrderOrganized;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            OrderDetailActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private EnumStatus getEnumStatus(String status){

        EnumStatus enumOrder = EnumStatus.PROCESS;

        switch (status){

            case Constants.StatusOrder.PROCESSING_STATUS:
            case Constants.StatusOrder.PENDING_CUSTOMER_RETURN_STATUS:
            case Constants.StatusOrder.PENDING_CUSTOMER_ACTION_STATUS:
            case Constants.StatusOrder.PENDING_MERCHANT_ACTION_STATUS:
                enumOrder = EnumStatus.PROCESS;
                break;


            case Constants.StatusOrder.SUBMITTED_STATUS:
                enumOrder = EnumStatus.SEND;
                break;



            case Constants.StatusOrder.NO_PENDING_ACTION_STATUS:
                enumOrder = EnumStatus.FINALIZED;
                break;


            case Constants.StatusOrder.PENDING_REMOVE_STATUS:
            case Constants.StatusOrder.REMOVED_STATUS:
                enumOrder = EnumStatus.CANCELED;
                break;
        }

        return enumOrder;

    }

}
