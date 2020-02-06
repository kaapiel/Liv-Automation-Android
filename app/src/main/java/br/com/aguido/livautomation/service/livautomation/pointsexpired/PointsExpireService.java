package br.com.aguido.livautomation.service.livautomation.pointsexpired;

import br.com.aguido.livautomation.service.livautomation.pointsexpired.model.PointsExpiredOutputResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by selem.gomes on 29/10/15.
 */
public interface PointsExpireService {

    @GET("/api/customer/points/expirationforecast")
    void pointsExpired(@Header("Authorization") String authorization,
                       Callback<PointsExpiredOutputResponse> cb);

}
