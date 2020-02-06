package br.com.aguido.livautomation.service.livautomation.brandingbank;

import br.com.aguido.livautomation.service.livautomation.brandingbank.model.BrandingBank;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;

/**
 * Created by selem.gomes on 02/05/16.
 */
public interface BrandingBankService {

    @Headers("Cache-Control: no-cache")
    @GET("/api/customer/partners")
    void getPartners(@Header("Cookie") String cookie, @Header("Authorization") String authorization,
                     Callback<BrandingBank> cb);
}
