package br.com.pontomobi.livelopontos.service.livelo.registration;

import br.com.pontomobi.livelopontos.service.livelo.registration.model.RegisterUserRequest;
import br.com.pontomobi.livelopontos.service.livelo.LiveloResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by vilmar.filho on 1/22/16.
 */
public interface RegistrationService {

    @Headers("Cache-Control: no-cache")
    @POST("/api/customer/registration")
    void registerUser(@Body RegisterUserRequest registerUserRequest,
                      Callback<LiveloResponse> cb);

}
