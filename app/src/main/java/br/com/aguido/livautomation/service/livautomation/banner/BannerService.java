package br.com.aguido.livautomation.service.livautomation.banner;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Streaming;

public interface BannerService {

    @GET("https://static.empresa.com.br/content/img/app/{filename}.gif")
    @Streaming
    void getBanner(@Path("filename") String filename,
    Callback<Response> callback);
}
