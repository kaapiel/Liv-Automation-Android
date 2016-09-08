package br.com.pontomobi.livelopontos.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.service.livelo.orders.model.CommerceItem;
import br.com.pontomobi.livelopontos.ui.orderHistory.EnumStatus;
import br.com.pontomobi.livelopontos.util.StringUtil;

public class ItemOrderProductDescriptionView extends LinearLayout{
    private TextView mNameProduct;
    private TextView mValuePoints;
    private TextView mAmount;
    private TextView mStatus;
    private View mBorderProduct;

    private CommerceItem mCommerceItem;

    private EnumStatus orderStatus;

    public ItemOrderProductDescriptionView(Context context, CommerceItem commerceItem, EnumStatus orderStatus) {
        super(context);
        mCommerceItem = commerceItem;
        LayoutInflater.from(context).inflate(R.layout.item_product_order_detail_description, this, true);

        this.orderStatus = orderStatus;
        initViews();
        fillViews();
    }

    public void initViews() {
        mNameProduct = (TextView) findViewById(R.id.tv_name_product);
        mValuePoints = (TextView) findViewById(R.id.tv_value_points);
        mAmount = (TextView) findViewById(R.id.product_qty);
        mStatus = (TextView) findViewById(R.id.tv_status);
        mBorderProduct = findViewById(R.id.product_border);
    }

    public void fillViews() {
        mNameProduct.setText(mCommerceItem.getProductName());

        String salePrice = StringUtil.formatPoints(mCommerceItem.getCommercePriceInfo().getSalePrice()); //Vr Unitario
        mValuePoints.setText(salePrice);

        String quantity = Integer.toString(mCommerceItem.getQuantity()); //Quantidade
        mAmount.setText(quantity);

        mStatus.setText(mCommerceItem.getStatusProduct());
        mStatus.setBackgroundDrawable(getResources().getDrawable(orderStatus.getDrawable()));
        mBorderProduct.setBackgroundColor(ContextCompat.getColor(getContext(), orderStatus.getColor()));
    }


}
