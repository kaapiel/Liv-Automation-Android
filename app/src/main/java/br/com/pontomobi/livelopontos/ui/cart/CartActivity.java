package br.com.pontomobi.livelopontos.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Cart;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.cart.model.CommerceItem;
import br.com.pontomobi.livelopontos.ui.cart.adapter.CartAdapter;
import br.com.pontomobi.livelopontos.ui.cart.adapter.FooterViewHolder;
import br.com.pontomobi.livelopontos.ui.cart.adapter.ProductViewHolder;
import br.com.pontomobi.livelopontos.ui.dialog.DialogLoading;
import br.com.pontomobi.livelopontos.ui.home.HomeActivity;
import br.com.pontomobi.livelopontos.ui.widget.BannerError;
import br.com.pontomobi.livelopontos.ui.widget.SimpleDividerItemDecoration;
import br.com.pontomobi.livelopontos.util.CartDownloadImage;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vilmar.filho on 5/18/16.
 */
public class CartActivity extends LiveloPontosActivity {

    @Bind(R.id.toolbar_title) TextView toolbarTitle;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.rv_cart) RecyclerView recyclerViewCart;

    @Bind(R.id.cart_menu) LinearLayout cartContainerMenu;
    @Bind(R.id.cart_menu_image) ImageView cartMenuIcon;
    @Bind(R.id.cart_menu_items) TextView menuCartTotal;

    @Bind(R.id.banner_error) BannerError bannerError;
    @Bind(R.id.container_empty_cart) RelativeLayout containerEmptyCart;

    @Bind(R.id.cart_go_catalog) AppCompatButton goToCatalog;

    private CartBusiness cartBusiness;

    private DialogLoading dialogLoading;

    private CartAdapter cartAdapter;
    private Cart cartCurrent;

    private int positionInProcess;
    private int amountInProcess;

    private CartDownloadImage cartDownloadImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);
        dialogLoading = new DialogLoading(this);

        cartDownloadImage = new CartDownloadImage(this, getOnCartDownload());

        configureActionBar();
        initRecyclerView();

        dialogLoading.show();
        cartBusiness.getCart();
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(toolbar);
        toolbarTitle.setText(R.string.cart_title_toolbar);

        super.configureActionBar();
    }

    private void initRecyclerView() {
        recyclerViewCart.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCart.setLayoutManager(layoutManager);
    }

    private void setInfo() {
        cartAdapter = new CartAdapter(
                this,
                cartCurrent.getPointsUser(),
                cartCurrent.getCartOrderUser().getLastModifiedTime(),
                cartCurrent.getCartOrderUser().getCartPriceInfo(),
                cartCurrent.getCartOrderUser().getCommerceItems());

        if (cartCurrent.getCartOrderUser().getCommerceItems() != null
                && cartCurrent.getCartOrderUser().getCommerceItems().size() > 0) {

            recyclerViewCart.setVisibility(View.VISIBLE);
            containerEmptyCart.setVisibility(View.GONE);
        } else {
            recyclerViewCart.setVisibility(View.GONE);
            containerEmptyCart.setVisibility(View.VISIBLE);
        }

        LiveloPontosApp.getInstance().setCartUser(cartCurrent);

        int amountProduct = cartCurrent.getCartOrderUser().getTotalCommerceItemCount();
        setupCartMenu(amountProduct);

        cartAdapter.setCartProductListener(getCartProductListener());
        cartAdapter.setCartFooterListener(getCartFooterListener());

        recyclerViewCart.setAdapter(cartAdapter);
    }

    private void setupCartMenu(int amount) {
        cartContainerMenu.setVisibility(View.VISIBLE);

        if (amount > 0) {
            cartMenuIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_carrinho_cheio));
            menuCartTotal.setVisibility(View.VISIBLE);
        } else {
            cartMenuIcon.setImageDrawable(getResources().getDrawable(R.drawable.add_carrinho));
            menuCartTotal.setVisibility(View.GONE);
        }

        menuCartTotal.setText(String.valueOf(amount));
    }

    @OnClick(R.id.cart_go_catalog)
    public void goToCatalog() {
        LiveloPontosApp.getInstance().sendTrackerEvent(
                Constants.GoogleAnalytisEvents.EVENT_SCREEN_CART,
                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_CART,
                Constants.GoogleAnalytisEvents.EVENT_ACTION_PRODUCTS_CATALOG,
                ""
        );

        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra(HomeActivity.GO_TO_CATALOG, true);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private FooterViewHolder.CartFooterListener getCartFooterListener() {
        return new FooterViewHolder.CartFooterListener() {

            @Override
            public void checkoutOrder() {
                String total = String.valueOf((cartCurrent.getPointsUser() - cartCurrent.getCartOrderUser().getCartPriceInfo().getAmount()));
                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_CART,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_CART,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_CART_CONCLUDE,
                        total
                );

                dialogLoading.show();

            }

            @Override
            public void addCoupon(String coupon) {
                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_CART,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_CART,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_CART_COUPON,
                        coupon
                );

                dialogLoading.show();
                cartBusiness.claimCoupon(coupon);
            }
        };
    }

    private ProductViewHolder.CartProductListener getCartProductListener(){
        return new ProductViewHolder.CartProductListener() {
            @Override
            public void deleteProduct(int pos) {
                CommerceItem commerceItem = cartCurrent.getCartOrderUser().getCommerceItems().get(pos);

                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_CART,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_CART,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_CART_DELETE,
                        commerceItem.getProductDisplayName()
                );

                dialogLoading.show();
                cartBusiness.deleteProductCart(commerceItem.getId());
            }

            @Override
            public void productDecrease(int amount, int pos) {
                CommerceItem commerceItem = cartCurrent.getCartOrderUser().getCommerceItems().get(pos);

                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_CART,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_CART,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_CART_DECREASE_PRODUCT,
                        commerceItem.getProductDisplayName()
                );

                startUpdateAmountProduct(amount, pos);
            }

            @Override
            public void productIncrease(int amount, int pos) {
                CommerceItem commerceItem = cartCurrent.getCartOrderUser().getCommerceItems().get(pos);

                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_CART,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_CART,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_CART_INCREASE_PRODUCT,
                        commerceItem.getProductDisplayName()
                );

                startUpdateAmountProduct(amount, pos);
            }
        };
    }

    private CartDownloadImage.OnCartDownload getOnCartDownload() {
        return new CartDownloadImage.OnCartDownload() {
            @Override
            public void processFinished() {
                setInfo();
                dialogLoading.hide();
            }
        };
    }

    private void startUpdateAmountProduct(int amount, int pos) {
        dialogLoading.show();

        positionInProcess = pos;
        amountInProcess = amount;

        CommerceItem commerceItem = cartCurrent.getCartOrderUser().getCommerceItems().get(pos);

        cartBusiness.updateProductCart(commerceItem.getId(), commerceItem.getCatalogRefId(), String.valueOf(amount));
    }

    private void finishUpdateAmountProduct(boolean success) {
        if (success) {
            cartAdapter.updateAmountProduct(positionInProcess, amountInProcess);
        } else {
            cartAdapter.updateListItem(positionInProcess);
        }

        positionInProcess = -1;
        amountInProcess = 0;
    }

    private void showBannerErro(String msg) {
        bannerError.setBannerText(msg);
        bannerError.setVisibility(View.VISIBLE);
        bannerError.showAndAnimBannerError();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bannerError.hideAndAnimBannerError();
            }
        }, 3000);
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

}
