package br.com.aguido.livautomation.service.livautomation;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import br.com.aguido.livautomation.Constants;
import br.com.aguido.livautomation.LivAutomationApp;
import br.com.aguido.livautomation.helper.NetworkHelper;
import br.com.aguido.livautomation.service.SetCookieInterceptor;
import br.com.aguido.livautomation.service.TLSSocketFactory;
import br.com.aguido.livautomation.service.livautomation.brandingbank.BrandingBankService;
import br.com.aguido.livautomation.service.livautomation.brandingbank.model.BrandingBank;
import br.com.aguido.livautomation.service.livautomation.checkout.model.CheckoutAddressResponse;
import br.com.aguido.livautomation.service.livautomation.login.LoginService;
import br.com.aguido.livautomation.service.livautomation.login.model.LoginRequest;
import br.com.aguido.livautomation.service.livautomation.login.model.LoginResponse;
import br.com.aguido.livautomation.service.livautomation.pointsexpired.model.ListHistogram;
import br.com.aguido.livautomation.service.livautomation.pointsexpired.model.ListHistogramAdapter;
import br.com.aguido.livautomation.service.livautomation.userprofile.UserProfileService;
import br.com.aguido.livautomation.service.livautomation.userprofile.model.ListUserAddress;
import br.com.aguido.livautomation.service.livautomation.userprofile.model.ListUserAddressAdapter;
import br.com.aguido.livautomation.service.livautomation.userprofile.model.ProfileResponse;
import br.com.aguido.livautomation.ui.login.LoginBusiness;
import br.com.aguido.livautomation.ui.myInfo.MyInfoBusiness;
import br.com.aguido.livautomation.util.LoginUtil;
import br.com.aguido.livautomation.util.Util;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by selem.gomes on 29/10/15.
 */
public class LivautomationRepository {
    private static final String LIVAUTOMATION_REPOSITORY_TAG = "LivautRepositoryTag";

    private static LivautomationRepository singleton;
    private static Context context;
    private static RestAdapter restAdapter;
    private static OkHttpClient okHttpClient;

    public LivautomationRepository(Context context) {
        this.context = context;
    }

    public static LivautomationRepository with(Context context) {
        if (singleton == null) {
            synchronized (LivautomationRepository.class) {
                if (singleton == null) {
                    singleton = new LivautomationRepository(context);
                }
            }
        }
        return singleton;
    }

    protected static synchronized RestAdapter connect() {
        return connect(null);
    }

    protected static synchronized RestAdapter connect(Map<String,String> parameters) {
        if (restAdapter == null) {
            enableRetrofitConnection();
        }

        okHttpClient.networkInterceptors().clear();
        if(parameters != null){
            okHttpClient.networkInterceptors().add(new SetCookieInterceptor(parameters));
        }

        return restAdapter;
    }

    private static void enableRetrofitConnection() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        try {
            okHttpClient.setSslSocketFactory(new TLSSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ListHistogram.class, new ListHistogramAdapter());
        gsonBuilder.registerTypeAdapter(ListUserAddress.class, new ListUserAddressAdapter());
        gsonBuilder.registerTypeAdapter(CheckoutAddressResponse.class, new CustomDeserializer());

        Gson gson = gsonBuilder.create();

        RestAdapter.Builder restBuilder = new RestAdapter.Builder()
                //.setEndpoint(BuildConfig.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("DEVICE_ID", LivAutomationApp.getInstance().getDeviceId());
                        request.addHeader("APP_VERSION", LivAutomationApp.getInstance().getVersionNumber());
                        request.addHeader("OS", "ANDROID");
                    }
                })
                .setClient(new OkClient(okHttpClient));

        restAdapter = restBuilder.build();
    }

    /**
     * Tenta realizar login com
     * os parametros repassados
     * pelo usuário.
     */
    public void tryLogin(Context context, LoginRequest loginRequest, final LoginBusiness.OnServiceLoginListener onServiceLoginListener) {
        if (!NetworkHelper.isOnline(context)) {
            onServiceLoginListener.onLoginFail(new LivautomationException(LivautomationException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }
        connect().create(LoginService.class).checkLogin(LoginUtil.generateAutorizationBase64(),
                loginRequest.getGrantType(),
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                loginRequest.getScope(), new Callback<LoginResponse>() {
                    @Override
                    public void success(LoginResponse loginResponse, Response response) {
                        Log.d(LIVAUTOMATION_REPOSITORY_TAG, "Sucesso ao realizar o login");
                        onServiceLoginListener.onLoginSuccess(loginResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LIVAUTOMATION_REPOSITORY_TAG, "Falha ao realizar o login");
                        onServiceLoginListener.onLoginFail(new LivautomationException(LivautomationException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                    }
                });
    }


    /**
     * Refresh do token caso tenha expirado
     */
    public void getNewToken(final Context context, final OnRefreshTokenListener onRefreshTokenListener) {
        if (!NetworkHelper.isOnline(context)) {
            return;
        }

        connect().create(LoginService.class).refreshToken(LoginUtil.generateAutorizationBase64(),
                LoginRequest.GRANT_TYPE_REFRESH_TOKEN,
                LivAutomationApp.getInstance().getLogin().getRefreshToken(),
                new Callback<LoginResponse>() {
                    @Override
                    public void success(LoginResponse loginResponse, Response response) {
                        Log.d(LIVAUTOMATION_REPOSITORY_TAG, "Sucesso ao atualizar token");
                        LivAutomationApp.getInstance().setLogin(loginResponse);
                        LoginUtil.saveInfoAboutLogin(context, loginResponse);
                        onRefreshTokenListener.onRefreshTokenSucess();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LIVAUTOMATION_REPOSITORY_TAG, "Falha ao atualizar token");
                        onRefreshTokenListener.onRefreshTokenFail();
                    }
                });
    }

    /**
     * Recupera os dados de profile do usuário.
     */
    public void getUserProfile(final Context context,
                               final MyInfoBusiness.OnServiceUserProfileListener onServiceUserProfileListener) {
        if (!NetworkHelper.isOnline(context)) {
            onServiceUserProfileListener.onGetUserProfileFail(new LivautomationException(LivautomationException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }
        connect().create(UserProfileService.class).userProfile(LivAutomationApp.getInstance().getSessionId(),
                LivAutomationApp.getInstance().getLogin().getAccessToken(), new Callback<ProfileResponse>() {
                    @Override
                    public void success(ProfileResponse profileResponse, Response response) {
                        if (profileResponse == null) {
                            Log.d(LIVAUTOMATION_REPOSITORY_TAG, "Falha ao recuperar profile do usuário");
                            onServiceUserProfileListener.onGetUserProfileFail(new LivautomationException(LivautomationException.EXCEPTION_SERVICE_ERROR, ""));
                            return;
                        }

                        saveSessionIdRecoverInProfile(response);

                        Log.d(LIVAUTOMATION_REPOSITORY_TAG, "Sucesso ao recuperar profile do usuário");
                        onServiceUserProfileListener.onGetUserProfileSuccess(profileResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LIVAUTOMATION_REPOSITORY_TAG, "Falha ao recuperar profile do usuário");

                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {

                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    getUserProfile(context, onServiceUserProfileListener);
                                }

                                @Override
                                public void onRefreshTokenFail() {
                                    onServiceUserProfileListener.onGetUserProfileFail(new LivautomationException(LivautomationException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            onServiceUserProfileListener.onGetUserProfileFail(new LivautomationException(LivautomationException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }
                });
    }

    private void saveSessionIdRecoverInProfile(Response response) {
        String jSessionId = getCookieJSessionID(response);
        Util.saveSessioId(context, jSessionId);
        LivAutomationApp.getInstance().setSessionId(jSessionId);
    }

    /**
     * Recupera o banco parceiro do usuário.
     */
    public void getBrandingBanks(final Context context,
                                 final MyInfoBusiness.OnServiceBrandingBankListener listener) {
        if (!NetworkHelper.isOnline(context)) {
            listener.onFail(new LivautomationException(LivautomationException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(BrandingBankService.class).getPartners(LivAutomationApp.getInstance().getSessionId(),
                LivAutomationApp.getInstance().getLogin().getAccessToken(),
                new Callback<BrandingBank>() {
                    @Override
                    public void success(BrandingBank brandingBank, Response response) {
                        Log.v("TESTSE", "teste");
                        listener.onGetBrandingBankSuccess(brandingBank);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {

                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    getBrandingBanks(context, listener);
                                }

                                @Override
                                public void onRefreshTokenFail() {
                                    listener.onFail(new LivautomationException(LivautomationException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            listener.onFail(new LivautomationException(LivautomationException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }
                });
    }

    public interface OnRefreshTokenListener {
        void onRefreshTokenSucess();

        void onRefreshTokenFail();
    }

    private String getCookieJSessionID(Response response) {
        for (Header header : response.getHeaders()) {
            if (header.getName().equalsIgnoreCase("set-cookie")) {
                String[] values = header.getValue().split(";");
                return values[0].replace("JSESSIONID=", "");
            }
        }

        return "";
    }

    private Map<String,String> getHeaderSession(String sessionId) {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("JSESSIONID", sessionId);
        return parameters;
    }

}
