package br.com.pontomobi.livelopontos.service.livelo.userprofile;

import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ProfileResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;

/**
 * Created by selem.gomes on 29/10/15.
 */
public interface UserProfileService {

    @Headers("Cache-Control: no-cache")
    @GET("/api/customer/profile")
    void userProfile(@Header("Cookie") String cookie, @Header("Authorization") String authorization,
                     Callback<ProfileResponse> cb);

}
