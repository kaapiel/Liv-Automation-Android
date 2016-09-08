package br.com.pontomobi.livelopontos.service.livelo.activatedevice;

import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.activationmobiletoken.ActivationTokenRequest;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.activationmobiletoken.ActivationTokenResponse;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.retrievemobile.RetrieveDeviceResponse;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.transactiontoken.TransactionTokenRequest;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.transactiontoken.TransactionTokenResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by vilmar.filho on 1/25/16.
 */
public interface ActivateDeviceService {

    @GET("/api/token/mobiletoken/devices")
    void retrieveMobile(@Header("Authorization") String authorization,
                        Callback<RetrieveDeviceResponse> cb);


    @POST("/api/token/transactiontoken")
    void requestCodeSMS(@Header("Authorization") String authorization,
                        @Body TransactionTokenRequest transactionTokenRequest,
                        Callback<TransactionTokenResponse> cb);

    @POST("/api/token/mobiletoken/activation")
    void activationMobileToken(@Header("Authorization") String authorization,
                        @Body ActivationTokenRequest activationTokenRequest,
                        Callback<ActivationTokenResponse> cb);
}
