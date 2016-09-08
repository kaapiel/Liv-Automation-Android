package br.com.pontomobi.livelopontos.service.livelo.changePassword;

import br.com.pontomobi.livelopontos.service.livelo.changePassword.model.ChangePasswordReponse;
import br.com.pontomobi.livelopontos.service.livelo.changePassword.model.ChangePasswordRequest;
import br.com.pontomobi.livelopontos.service.livelo.forgotpassword.model.ForgotPasswordResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

public interface ChangePasswordService {

    @PUT("/api/user/authentication/passwordchange")
    @Headers("Content-Type:application/json")
    void changePassword(@Header("Authorization") String authorization,
                        @Body ChangePasswordRequest request,
                        Callback<ChangePasswordReponse> cb);

}
