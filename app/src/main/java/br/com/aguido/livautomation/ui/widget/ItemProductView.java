package br.com.aguido.livautomation.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.aguido.livautomation.R;

public class ItemProductView extends LinearLayout{

    private TextView mNamePartner;
    private LinearLayout mContainer;
    private String partner;

    public ItemProductView(Context context, String partner) {
        super(context);
        this.partner = partner;

        LayoutInflater.from(context).inflate(R.layout.item_product_order_datail, this, true);

        initViews();
        fillViews();
    }

    public void initViews(){
        mNamePartner = (TextView) findViewById(R.id.tv_name_partner);
        mContainer = (LinearLayout) findViewById(R.id.item_product_order_detail_description_container);
    }

    public void fillViews(){
        mNamePartner.setText(this.partner);
    }

    public LinearLayout getContainer() {
        return mContainer;
    }
}
