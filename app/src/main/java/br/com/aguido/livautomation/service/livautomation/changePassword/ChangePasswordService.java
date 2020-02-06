package br.com.aguido.livautomation.service.livautomation.changePassword;

import br.com.aguido.livautomation.service.livautomation.changePassword.model.ChangePasswordReponse;
import br.com.aguido.livautomation.service.livautomation.changePassword.model.ChangePasswordRequest;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.PUT;

public interface ChangePasswordService {

    @PUT("/api/user/authentication/passwordchange")
    @Headers("Content-Type:application/json")
    void changePassword(@Header("Authorization") String authorization,
                        @Body ChangePasswordRequest request,
                        Callback<ChangePasswordReponse> cb);

}
