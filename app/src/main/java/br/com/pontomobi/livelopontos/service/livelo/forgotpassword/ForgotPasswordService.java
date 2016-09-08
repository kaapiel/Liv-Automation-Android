package br.com.pontomobi.livelopontos.service.livelo.forgotpassword;

import br.com.pontomobi.livelopontos.service.livelo.forgotpassword.model.ForgotPasswordRequest;
import br.com.pontomobi.livelopontos.service.livelo.forgotpassword.model.ForgotPasswordResponse;
import br.com.pontomobi.livelopontos.service.livelo.login.model.LoginResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface ForgotPasswordService {

    @POST("/api/user/authentication/passwordreset")
    @Headers("Content-Type:application/json")
    void recoverPassword(@Body ForgotPasswordRequest request,
                         Callback<ForgotPasswordResponse> cb);

}
