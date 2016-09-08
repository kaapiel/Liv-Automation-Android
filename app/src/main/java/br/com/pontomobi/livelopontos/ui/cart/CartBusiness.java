package br.com.pontomobi.livelopontos.ui.cart;

import android.content.Context;

import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.model.Cart;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.amountpoints.model.AmountPointsResponse;
import br.com.pontomobi.livelopontos.service.livelo.cart.CoupomRequest;
import br.com.pontomobi.livelopontos.service.livelo.cart.UpdateResquest;
import br.com.pontomobi.livelopontos.service.livelo.cart.model.CartResponse;
import br.com.pontomobi.livelopontos.ui.myPoints.summary.SummaryBusiness;

/**
 * Created by vilmar.filho on 5/19/16.
 */
public class CartBusiness {

    private Context context;
    private Cart cart;

    private OnCartListener onCartListener;
    private OnUpdateCartListener onUpdateCartListener;
    private OnCouponListener onCouponListener;
    private OnDeleteListener onDeleteListener;

    public CartBusiness(Context context, OnCartListener onCartListener) {
        cart = new Cart();

        this.context = context;
        this.onCartListener = onCartListener;
    }

    public void getCart() {
        LiveloRepository.with(context).getAmountPoints(context, getOnServiceAmountPointsListener());
    }

    public void updateProductCart(String idProduct, String skuId, String qty) {
        LiveloRepository.with(context).updateProductCart(idProduct, new UpdateResquest(skuId, qty), getOnUpdateCartListener());
    }

    public void deleteProductCart(String idProduct) {
        LiveloRepository.with(context).deleteProductCart(idProduct, getOnServiceDeleteListener());
    }

    public void claimCoupon(String coupon) {
        LiveloRepository.with(context).claimCoupon(new CoupomRequest(coupon), getOnServiceCouponListener());
    }

    private SummaryBusiness.OnServiceAmountPointsListener getOnServiceAmountPointsListener() {
        return new SummaryBusiness.OnServiceAmountPointsListener() {
            @Override
            public void onGetPointsSuccess(AmountPointsResponse response) {
                cart.setPointsUser(response.getOutput().getResult());

                LiveloRepository.with(context).getCart(LiveloPontosApp.getInstance().getSessionId(), getOnServiceCartListener());
            }

            @Override
            public void onGetPontsFail(LiveloException exception) {
                onCartListener.onCartFail(exception);
            }
        };
    }

    private OnServiceCartListener getOnServiceCartListener() {
        return new OnServiceCartListener() {
            @Override
            public void onSuccess(CartResponse cartResponse) {
                cart.setCartOrderUser(cartResponse.getCartOrder());

                onCartListener.onCartSuccess(cart);
            }

            @Override
            public void onFail(LiveloException exception) {
                onCartListener.onCartFail(exception);
            }
        };
    }

    public void setUpdateListener(OnUpdateCartListener onUpdateCartListener) {
        this.onUpdateCartListener = onUpdateCartListener;
    }

    public void setOnCouponListener(OnCouponListener onCouponListener) {
        this.onCouponListener = onCouponListener;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    private OnServiceUpdateCartListener getOnUpdateCartListener() {
        return new OnServiceUpdateCartListener() {
            @Override
            public void onUpdateSuccess() {
                LiveloRepository.with(context).getCart(LiveloPontosApp.getInstance().getSessionId(), getOnServiceUpdateCartListener());
            }

            @Override
            public void onUpdateFail(LiveloException exception) {
                if (onUpdateCartListener != null)
                    onUpdateCartListener.onUpdateFail(exception);
            }
        };
    }

    private OnServiceCartListener getOnServiceUpdateCartListener() {
        return new OnServiceCartListener() {
            @Override
            public void onSuccess(CartResponse cartResponse) {
                cart.setCartOrderUser(cartResponse.getCartOrder());

                if (onUpdateCartListener != null)
                    onUpdateCartListener.onUpdateSuccess(cart);
            }

            @Override
            public void onFail(LiveloException exception) {
                onCartListener.onCartFail(exception);
            }
        };
    }

    private OnServiceDeleteListener getOnServiceDeleteListener() {
        return new OnServiceDeleteListener() {
            @Override
            public void onDeleteSuccess() {
                LiveloRepository.with(context).getCart(LiveloPontosApp.getInstance().getSessionId(), getOnDeleteCartListener());
            }

            @Override
            public void onDeleteFail(LiveloException exception) {
                if (onDeleteListener != null)
                    onDeleteListener.onFail(exception);
            }
        };
    }

    private OnServiceCouponListener getOnServiceCouponListener() {
        return new OnServiceCouponListener() {
            @Override
            public void onCouponSuccess() {
                LiveloRepository.with(context).getCart(LiveloPontosApp.getInstance().getSessionId(), getOnCouponCartListener());
            }

            @Override
            public void onCouponFail(LiveloException exception) {
                if (onCouponListener != null)
                    onCouponListener.onFail(exception);
            }
        };
    }

    private OnServiceCartListener getOnCouponCartListener() {
        return new OnServiceCartListener() {
            @Override
            public void onSuccess(CartResponse cartResponse) {
                cart.setCartOrderUser(cartResponse.getCartOrder());

                if (onCouponListener != null)
                    onCouponListener.onSuccess(cart);
            }

            @Override
            public void onFail(LiveloException exception) {
                onCouponListener.onFail(exception);
            }
        };
    }

    private OnServiceCartListener getOnDeleteCartListener() {
        return new OnServiceCartListener() {
            @Override
            public void onSuccess(CartResponse cartResponse) {
                cart.setCartOrderUser(cartResponse.getCartOrder());

                if (onDeleteListener != null)
                    onDeleteListener.onSuccess(cart);
            }

            @Override
            public void onFail(LiveloException exception) {
                onCouponListener.onFail(exception);
            }
        };
    }

    public interface OnServiceCartListener {
        void onSuccess(CartResponse cartResponse);
        void onFail(LiveloException exception);
    }

    public interface OnCartListener {
        void onCartSuccess(Cart cart);
        void onCartFail(LiveloException exception);
    }

    public interface OnServiceUpdateCartListener {
        void onUpdateSuccess();
        void onUpdateFail(LiveloException exception);
    }

    public interface OnUpdateCartListener {
        void onUpdateSuccess(Cart cart);
        void onUpdateFail(LiveloException exception);
    }

    public interface OnServiceCouponListener {
        void onCouponSuccess();
        void onCouponFail(LiveloException exception);
    }

    public interface OnCouponListener {
        void onSuccess(Cart cart);
        void onFail(LiveloException exception);
    }

    public interface OnServiceDeleteListener {
        void onDeleteSuccess();
        void onDeleteFail(LiveloException exception);
    }

    public interface OnDeleteListener {
        void onSuccess(Cart cart);
        void onFail(LiveloException exception);
    }
}
