package br.com.pontomobi.livelopontos.service.livelo.pointsexpired;

import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.PointsExpiredOutputResponse;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.PointsExpiredResponse;
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
