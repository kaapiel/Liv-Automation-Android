package br.com.aguido.livautomation.service.livautomation.amountpoints;

import br.com.aguido.livautomation.service.livautomation.amountpoints.model.AmountPointsResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by selem.gomes on 29/10/15.
 */
public interface AmountPointsService {

    @GET("/api/customer/points/balance")
    void pointsBalance(@Header("Authorization") String authorization,
                       Callback<AmountPointsResponse> cb);
}
