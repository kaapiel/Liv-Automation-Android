package br.com.aguido.livautomation.service.livautomation.login;

import br.com.aguido.livautomation.service.livautomation.login.model.LoginResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by selem.gomes on 29/10/15.
 */
public interface LoginService {

    @FormUrlEncoded
    @POST("/api/oauth2/tokens")
    void checkLogin(@Header("Authorization") String authorization,
                    @Field("grant_type") String grantType,
                    @Field("username") String username,
                    @Field("password") String password,
                    @Field("scope") String scope,
                    Callback<LoginResponse> cb);


    @FormUrlEncoded
    @POST("/api/oauth2/tokens")
    void refreshToken(@Header("Authorization") String authorization,
                      @Field("grant_type") String grantType,
                      @Field("refresh_token") String refreshRoken,
                      Callback<LoginResponse> cb);

}
