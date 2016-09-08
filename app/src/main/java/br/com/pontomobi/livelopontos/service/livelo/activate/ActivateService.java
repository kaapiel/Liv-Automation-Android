package br.com.pontomobi.livelopontos.service.livelo.activate;

import br.com.pontomobi.livelopontos.service.livelo.activate.model.ActivateRequest;
import br.com.pontomobi.livelopontos.service.livelo.activate.model.ActivateUserResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by vilmar.filho on 1/20/16.
 */
public interface ActivateService {

    @POST("/api/customer/activation")
    void activateUser(@Body ActivateRequest activateRequest,
                      Callback<ActivateUserResponse> cb);

}
