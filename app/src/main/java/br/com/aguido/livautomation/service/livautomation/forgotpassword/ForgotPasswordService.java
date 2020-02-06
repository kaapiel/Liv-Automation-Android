package br.com.aguido.livautomation.service.livautomation.forgotpassword;

import br.com.aguido.livautomation.service.livautomation.forgotpassword.model.ForgotPasswordRequest;
import br.com.aguido.livautomation.service.livautomation.forgotpassword.model.ForgotPasswordResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface ForgotPasswordService {

    @POST("/api/user/authentication/passwordreset")
    @Headers("Content-Type:application/json")
    void recoverPassword(@Body ForgotPasswordRequest request,
                         Callback<ForgotPasswordResponse> cb);

}
