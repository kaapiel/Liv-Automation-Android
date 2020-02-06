package br.com.aguido.livautomation.service.livautomation.checkout;

import br.com.aguido.livautomation.service.livautomation.checkout.model.CheckoutAddressRequest;
import br.com.aguido.livautomation.service.livautomation.checkout.model.CheckoutAddressResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by vilmar on 5/21/16.
 */
public interface CheckoutService {

    @Headers("Content-Type: application/json")
    @POST("/api/shoppingcart/purchase")
    void moveToCheckout(@Header("Authorization") String authorization,
                        @Header("Cookie") String jSessionId,
                        @Body Object body,
                        Callback<Object> cb);

    @GET("/api/purchase/shipping/address")
    void getAdrress(@Header("Authorization") String authorization,
                    @Header("Cookie") String jSessionId,
                    @Query("shippingGroupTypes") String shippingGroupTypes,
                    @Query("init") boolean init,
                    Callback<CheckoutAddressResponse> cb);

    @POST("/api/purchase/shipping/address")
    void setAdrress(@Header("Authorization") String authorization,
                    @Body CheckoutAddressRequest address,
                    Callback<Object> cb);

    @POST("/api/purchase/order/confirmation")
    void confirmation(@Header("Authorization") String authorization,
                      @Body Object body,
                      Callback<Object> cb);

    @POST("/api/purchase/order/submission")
    void finishOrder(@Header("Authorization") String authorization,
                     @Body Object body,
                     Callback<Object> cb);
}
