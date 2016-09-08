package br.com.pontomobi.livelopontos.service.livelo.orders;

import br.com.pontomobi.livelopontos.service.livelo.orders.model.OrderResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

public interface OrderService {

    @GET("/api/orders")
    void pointsExpired(@Header("Authorization") String authorization,
                       Callback<OrderResponse> cb);

}
