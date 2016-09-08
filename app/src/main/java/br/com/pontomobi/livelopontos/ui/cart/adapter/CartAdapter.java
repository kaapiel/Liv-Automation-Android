package br.com.pontomobi.livelopontos.ui.cart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.service.livelo.cart.model.CartPriceInfo;
import br.com.pontomobi.livelopontos.service.livelo.cart.model.CommerceItem;
import br.com.pontomobi.livelopontos.util.StringUtil;

/**
 * Created by vilmar.filho on 5/19/16.
 */
public class CartAdapter extends RecyclerView.Adapter{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private static final String PATTERN_POINTS = "###,###.###";

    private FooterViewHolder.CartFooterListener cartFooterListener;
    private ProductViewHolder.CartProductListener cartProductListener;

    private Context context;

    private long lastModifiedTime;
    private int pointsUser;
    private CartPriceInfo cartPriceInfo;
    private List<CommerceItem> commerceItems;

    public CartAdapter(Context context, int points, long lastModifiedTime, CartPriceInfo cartPriceInfo, List<CommerceItem> commerceItems) {
        this.context = context;

        this.pointsUser = points;
        this.lastModifiedTime = lastModifiedTime;
        this.cartPriceInfo = cartPriceInfo;
        this.commerceItems = commerceItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_cart_header, parent, false);
            return new HeaderViewHolder(v);

        } else if(viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_cart_footer, parent, false);
            FooterViewHolder fvh = new FooterViewHolder(context, v);
            fvh.setListener(cartFooterListener);
            return fvh;

        } else if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_cart_product, parent, false);
            ProductViewHolder pvh = new ProductViewHolder(v);
            pvh.setListener(cartProductListener);
            return pvh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            bindHeader(headerHolder);

        } else if(holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            bindFooter(footerHolder);

        } else if(holder instanceof ProductViewHolder) {
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            bindProduct(productViewHolder, (position - 1));
        }
    }

    private void bindHeader(HeaderViewHolder hvh) {
        Date date = new Date(lastModifiedTime);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH'h'mm");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        String messagePoints = String.format(context.getResources().getString(R.string.cart_update_points), formatValue(PATTERN_POINTS, pointsUser));
        hvh.amountPoints.setText(messagePoints);

        String timeUpdate = String.format(context.getResources().getString(R.string.cart_update_time), sdf.format(date));
        hvh.updateTime.setText(timeUpdate);
    }

    private void bindFooter(FooterViewHolder fvh) {
        fvh.subtotalPoints.setText(formatValue(PATTERN_POINTS, cartPriceInfo.getCashQualifiedSubTotal()));
        fvh.totalPoints.setText(formatValue(PATTERN_POINTS, cartPriceInfo.getAmount()));

        if (cartPriceInfo.getDiscountAmount() > 0) {
            fvh.discountContainer.setVisibility(View.VISIBLE);
        } else {
            fvh.discountContainer.setVisibility(View.GONE);
        }

        fvh.discountPoints.setText(formatValue(PATTERN_POINTS, cartPriceInfo.getDiscountAmount()));


        int total = pointsUser - cartPriceInfo.getAmount();

        if (total < 0) {
            fvh.remainingContainer.setBackgroundColor(context.getResources().getColor(R.color.red_cb3e3e));
            fvh.btnSendOrder.setVisibility(View.GONE);
            fvh.bannerInfo.setVisibility(View.VISIBLE);
        } else {
            fvh.remainingContainer.setBackgroundColor(context.getResources().getColor(R.color.blue_53529a));
            fvh.btnSendOrder.setVisibility(View.VISIBLE);
            fvh.bannerInfo.setVisibility(View.GONE);
        }

        fvh.remainingPoints.setText(formatValue(PATTERN_POINTS, total));
    }

    public void setCartFooterListener(FooterViewHolder.CartFooterListener cartFooterListener) {
        this.cartFooterListener = cartFooterListener;
    }

    public void setCartProductListener(ProductViewHolder.CartProductListener cartProductListener) {
        this.cartProductListener = cartProductListener;
    }

    private void bindProduct(ProductViewHolder pvh, int pos) {
        CommerceItem commerceItem = commerceItems.get(pos);

        /**
         * SOLUÇÃO TEMPORÁRIA
         *
         * Servico do cliente não possui atributo para trazer imagem dos produtos
         * Olhar classe CartDownloadImage.java
         */
        if (!TextUtils.isEmpty(commerceItem.getUrlImage())) {
            Picasso.with(context)
                    .load(commerceItem.getUrlImage())
                    .placeholder(R.drawable.placeholder)
                    .into(pvh.productImage);
        }

        pvh.productName.setText(commerceItem.getProductDisplayName());

        pvh.setProductsAmount(commerceItem.getQuantity());
        pvh.productAmount.setText(StringUtil.formatNumber(commerceItem.getQuantity()));

        int valueForm = commerceItem.getItemPriceInfo().getListPrice(); //De
        int valueTo = commerceItem.getItemPriceInfo().getSalePrice(); //Por

        if (valueForm == valueTo) {
            pvh.productTo.setText(
                    String.format(context.getResources().getString(R.string.cart_value_to), formatValue(PATTERN_POINTS, valueTo))
            );

        } else {
            if (valueForm > 0) {
                pvh.productFrom.setVisibility(View.VISIBLE);

                String s = String.format(context.getResources().getString(R.string.cart_value_from), formatValue(PATTERN_POINTS, valueForm));

                pvh.productFrom.setText(s, TextView.BufferType.SPANNABLE);
                Spannable spannable = (Spannable) pvh.productFrom.getText();
                spannable.setSpan(new StrikethroughSpan(), 3, (s.length() - 4), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            pvh.productTo.setText(String.format(
                    context.getResources().getString(R.string.cart_value_to), formatValue(PATTERN_POINTS, valueTo)));
        }
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionHeader (position)) {
            return TYPE_HEADER;
        } else if(isPositionFooter (position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader (int position) {
        return position == 0;
    }

    private boolean isPositionFooter (int position) {
        return position == this.commerceItems.size () + 1;
    }

    @Override
    public int getItemCount() {
        return this.commerceItems.size() + 2;
    }

    public void updateAmountProduct(int position, int amount) {
        commerceItems.get(position).setQuantity(amount);
        notifyItemChanged(position);
    }

    public void updateListItem(int pos) {
        notifyItemChanged(pos + 1);
    }

    public void removeListItem(int position){
        int pos = position + 1;
        commerceItems.remove(pos);
        notifyItemRemoved(position);
    }

    private String formatValue(String pattern, double value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(value);
    }
}
