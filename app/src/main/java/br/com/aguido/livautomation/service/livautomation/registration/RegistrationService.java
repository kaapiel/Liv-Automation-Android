package br.com.aguido.livautomation.service.livautomation.registration;

import br.com.aguido.livautomation.service.livautomation.LivautomationResponse;
import br.com.aguido.livautomation.service.livautomation.registration.model.RegisterUserRequest;
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
                      Callback<LivautomationResponse> cb);

}
