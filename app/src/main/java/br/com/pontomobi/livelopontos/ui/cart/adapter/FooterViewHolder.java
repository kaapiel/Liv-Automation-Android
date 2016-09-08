package br.com.pontomobi.livelopontos.ui.cart.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.EditText;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by vilmar.filho on 5/19/16.
 */
public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    EditText cartCoupon;
    AppCompatButton btnAddCoupon;
    TextView subtotalPoints;
    RelativeLayout discountContainer;
    TextView discountPoints;
    TextView totalPoints;
    RelativeLayout remainingContainer;
    TextView remainingPoints;
    AppCompatButton btnSendOrder;
    LinearLayout bannerInfo;

    private CartFooterListener listener;

    private Context context;

    public FooterViewHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        cartCoupon = (EditText) itemView.findViewById(R.id.cart_coupon);

        btnAddCoupon = (AppCompatButton) itemView.findViewById(R.id.cart_add_coupon);
        btnAddCoupon.setOnClickListener(this);

        subtotalPoints = (TextView) itemView.findViewById(R.id.cart_subtotal_points);
        discountContainer = (RelativeLayout) itemView.findViewById(R.id.container_discount);
        discountPoints = (TextView) itemView.findViewById(R.id.cart_discount_points);
        totalPoints = (TextView) itemView.findViewById(R.id.cart_total_points);
        remainingContainer = (RelativeLayout) itemView.findViewById(R.id.container_points_remaining);
        remainingPoints = (TextView) itemView.findViewById(R.id.cart_points_remaining);

        btnSendOrder = (AppCompatButton) itemView.findViewById(R.id.checkout_send_order);
        btnSendOrder.setOnClickListener(this);

        bannerInfo = (LinearLayout) itemView.findViewById(R.id.banner_info);

        setTypefaceInEditText();
        setupBehaviorFields();
    }

    private void setTypefaceInEditText() {
        Typeface museoSans = Typeface.createFromAsset(this.context.getAssets(), "fonts/MuseoSans_300.otf");
        cartCoupon.setTypeface(museoSans);
    }

    private void setupBehaviorFields() {
        cartCoupon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 5) {
                    btnAddCoupon.setTextColor(context.getResources().getColor(android.R.color.white));
                    btnAddCoupon.setEnabled(true);
                } else {
                    btnAddCoupon.setTextColor(context.getResources().getColor(R.color.gray_888888));
                    btnAddCoupon.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public void setListener(CartFooterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cart_add_coupon:
                addCoupon();
                break;

            case R.id.checkout_send_order:
                checkoutOrder();
                break;
        }
    }

    public void addCoupon() {
        String coupon = cartCoupon.getText().toString();

        if (listener != null)
            listener.addCoupon(coupon);
    }

    public void checkoutOrder() {
        listener.checkoutOrder();
    }

    public interface CartFooterListener {
        void checkoutOrder();
        void addCoupon(String coupon);
    }

}
