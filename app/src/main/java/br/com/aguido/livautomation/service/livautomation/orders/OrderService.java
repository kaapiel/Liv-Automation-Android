package br.com.aguido.livautomation.service.livautomation.orders;

import br.com.aguido.livautomation.service.livautomation.orders.model.OrderResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

public interface OrderService {

    @GET("/api/orders")
    void pointsExpired(@Header("Authorization") String authorization,
                       Callback<OrderResponse> cb);

}
