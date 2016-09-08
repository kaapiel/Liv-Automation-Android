package br.com.pontomobi.livelopontos.service.livelo.cart;

import br.com.pontomobi.livelopontos.service.livelo.cart.model.CartResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by vilmar.filho on 5/19/16.
 */
public interface CartService {

    @GET("/api/shoppingcart/items")
    void getCart(@Header("Authorization") String authorization,
                 @Header("Cookie") String jSessionId,
                 Callback<CartResponse> cb);


    @POST("/api/shoppingcart/items")
    void addProduct(@Header("Authorization") String authorization,
                    Callback<CartResponse> cb);


    @DELETE("/api/shoppingcart/items/{productId}")
    void deleteProduct(@Header("Authorization") String authorization,
                       @Path("productId") String productId,
                       Callback<Object> cb);


    @PUT("/api/shoppingcart/items/{productId}")
    void updateProduct(@Header("Authorization") String authorization,
                       @Path("productId") String productId,
                       @Body UpdateResquest updateResquest,
                       Callback<Object> cb);

    @POST("/api/promotions/coupon")
    void claimCoupon(@Header("Authorization") String authorization,
                     @Body CoupomRequest coupomRequest,
                     Callback<LiveloResponse> cb);
}
