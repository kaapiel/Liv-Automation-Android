package br.com.pontomobi.livelopontos.service;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

import br.com.pontomobi.livelopontos.LiveloPontosApp;

public class SetCookieInterceptor implements Interceptor {

    private Map<String,String> parameters;

    public SetCookieInterceptor(Map<String, String> parameters) {
        super();
        this.parameters = parameters;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        if(parameters != null){
            String cookie = "";
            for(String cookieKey : this.parameters.keySet()){
                cookie += cookieKey + "=" + this.parameters.get(cookieKey) + ";";
            }
            builder.header("Set-Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }
}
