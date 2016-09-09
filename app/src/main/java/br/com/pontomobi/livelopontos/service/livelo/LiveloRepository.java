package br.com.pontomobi.livelopontos.service.livelo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import br.com.pontomobi.livelopontos.BuildConfig;
import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.helper.NetworkHelper;
import br.com.pontomobi.livelopontos.service.SetCookieInterceptor;
import br.com.pontomobi.livelopontos.service.TLSSocketFactory;
import br.com.pontomobi.livelopontos.service.livelo.activate.ActivateService;
import br.com.pontomobi.livelopontos.service.livelo.activate.model.ActivateRequest;
import br.com.pontomobi.livelopontos.service.livelo.activate.model.ActivateUserResponse;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.ActivateDeviceService;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.activationmobiletoken.ActivationTokenRequest;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.activationmobiletoken.ActivationTokenResponse;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.retrievemobile.RetrieveDeviceResponse;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.transactiontoken.TransactionTokenRequest;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.transactiontoken.TransactionTokenResponse;
import br.com.pontomobi.livelopontos.service.livelo.amountpoints.AmountPointsService;
import br.com.pontomobi.livelopontos.service.livelo.amountpoints.model.AmountPointsResponse;
import br.com.pontomobi.livelopontos.service.livelo.brandingbank.BrandingBankService;
import br.com.pontomobi.livelopontos.service.livelo.brandingbank.model.BrandingBank;
import br.com.pontomobi.livelopontos.service.livelo.cart.CartService;
import br.com.pontomobi.livelopontos.service.livelo.cart.CoupomRequest;
import br.com.pontomobi.livelopontos.service.livelo.cart.UpdateResquest;
import br.com.pontomobi.livelopontos.service.livelo.cart.model.CartResponse;
import br.com.pontomobi.livelopontos.service.livelo.changePassword.ChangePasswordService;
import br.com.pontomobi.livelopontos.service.livelo.changePassword.model.ChangePasswordReponse;
import br.com.pontomobi.livelopontos.service.livelo.changePassword.model.ChangePasswordRequest;
import br.com.pontomobi.livelopontos.service.livelo.checkout.CheckoutService;
import br.com.pontomobi.livelopontos.service.livelo.checkout.model.CheckoutAddressRequest;
import br.com.pontomobi.livelopontos.service.livelo.checkout.model.CheckoutAddressResponse;
import br.com.pontomobi.livelopontos.service.livelo.forgotpassword.ForgotPasswordService;
import br.com.pontomobi.livelopontos.service.livelo.forgotpassword.model.ForgotPasswordRequest;
import br.com.pontomobi.livelopontos.service.livelo.forgotpassword.model.ForgotPasswordResponse;
import br.com.pontomobi.livelopontos.service.livelo.login.LoginService;
import br.com.pontomobi.livelopontos.service.livelo.login.model.LoginRequest;
import br.com.pontomobi.livelopontos.service.livelo.login.model.LoginResponse;
import br.com.pontomobi.livelopontos.service.livelo.orders.OrderService;
import br.com.pontomobi.livelopontos.service.livelo.orders.model.OrderResponse;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.PointsExpireService;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.ListHistogram;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.ListHistogramAdapter;
import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.PointsExpiredOutputResponse;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.ProductCatalogService;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.category.model.Categories;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.product.AddItemToCartRequest;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.product.ProductRequest;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.product.ProductResponse;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.search.model.ResultSearchResponse;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.SubcategoryRequest;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.model.Subcategory;
import br.com.pontomobi.livelopontos.service.livelo.registration.RegistrationService;
import br.com.pontomobi.livelopontos.service.livelo.registration.model.RegisterUserRequest;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.UserProfileService;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ListUserAddress;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ListUserAddressAdapter;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ProfileResponse;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.UserTransactionsService;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionRequest;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.UserTransactionsResponse;
import br.com.pontomobi.livelopontos.ui.login.LoginBusiness;
import br.com.pontomobi.livelopontos.ui.myInfo.MyInfoBusiness;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import br.com.pontomobi.livelopontos.util.Util;
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
public class LiveloRepository {
    private static final String LIVELO_REPOSITORY_TAG = "LiveloRepositoryTag";

    private static LiveloRepository singleton;
    private static Context context;
    private static RestAdapter restAdapter;
    private static OkHttpClient okHttpClient;

    public LiveloRepository(Context context) {
        this.context = context;
    }

    public static LiveloRepository with(Context context) {
        if (singleton == null) {
            synchronized (LiveloRepository.class) {
                if (singleton == null) {
                    singleton = new LiveloRepository(context);
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
                .setEndpoint(BuildConfig.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("DEVICE_ID", LiveloPontosApp.getInstance().getDeviceId());
                        request.addHeader("APP_VERSION", LiveloPontosApp.getInstance().getVersionNumber());
                        request.addHeader("OS", "ANDROID");
                    }
                })
                .setClient(new OkClient(okHttpClient));

        if(BuildConfig.ENABLE_RETROFIT_LOG ){
            restBuilder.setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    public void log(String msg) {
                        Log.i("retrofit_livelo", msg);
                    }
                });
        }

        restAdapter = restBuilder.build();
    }

    /**
     * Tenta realizar login com
     * os parametros repassados
     * pelo usuário.
     */
    public void tryLogin(Context context, LoginRequest loginRequest, final LoginBusiness.OnServiceLoginListener onServiceLoginListener) {
        if (!NetworkHelper.isOnline(context)) {
            onServiceLoginListener.onLoginFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }
        connect().create(LoginService.class).checkLogin(LoginUtil.generateAutorizationBase64(),
                loginRequest.getGrantType(),
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                loginRequest.getScope(), new Callback<LoginResponse>() {
                    @Override
                    public void success(LoginResponse loginResponse, Response response) {
                        Log.d(LIVELO_REPOSITORY_TAG, "Sucesso ao realizar o login");
                        onServiceLoginListener.onLoginSuccess(loginResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LIVELO_REPOSITORY_TAG, "Falha ao realizar o login");
                        onServiceLoginListener.onLoginFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
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
                LiveloPontosApp.getInstance().getLogin().getRefreshToken(),
                new Callback<LoginResponse>() {
                    @Override
                    public void success(LoginResponse loginResponse, Response response) {
                        Log.d(LIVELO_REPOSITORY_TAG, "Sucesso ao atualizar token");
                        LiveloPontosApp.getInstance().setLogin(loginResponse);
                        LoginUtil.saveInfoAboutLogin(context, loginResponse);
                        onRefreshTokenListener.onRefreshTokenSucess();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LIVELO_REPOSITORY_TAG, "Falha ao atualizar token");
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
            onServiceUserProfileListener.onGetUserProfileFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }
        connect().create(UserProfileService.class).userProfile(LiveloPontosApp.getInstance().getSessionId(),
                LiveloPontosApp.getInstance().getLogin().getAccessToken(), new Callback<ProfileResponse>() {
                    @Override
                    public void success(ProfileResponse profileResponse, Response response) {
                        if (profileResponse == null) {
                            Log.d(LIVELO_REPOSITORY_TAG, "Falha ao recuperar profile do usuário");
                            onServiceUserProfileListener.onGetUserProfileFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                            return;
                        }

                        saveSessionIdRecoverInProfile(response);

                        Log.d(LIVELO_REPOSITORY_TAG, "Sucesso ao recuperar profile do usuário");
                        onServiceUserProfileListener.onGetUserProfileSuccess(profileResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LIVELO_REPOSITORY_TAG, "Falha ao recuperar profile do usuário");

                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {

                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    getUserProfile(context, onServiceUserProfileListener);
                                }

                                @Override
                                public void onRefreshTokenFail() {
                                    onServiceUserProfileListener.onGetUserProfileFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            onServiceUserProfileListener.onGetUserProfileFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }
                });
    }

    private void saveSessionIdRecoverInProfile(Response response) {
        String jSessionId = getCookieJSessionID(response);
        Util.saveSessioId(context, jSessionId);
        LiveloPontosApp.getInstance().setSessionId(jSessionId);
    }

    /**
     * Recupera o banco parceiro do usuário.
     */
    public void getBrandingBanks(final Context context,
                                 final MyInfoBusiness.OnServiceBrandingBankListener listener) {
        if (!NetworkHelper.isOnline(context)) {
            listener.onFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(BrandingBankService.class).getPartners(LiveloPontosApp.getInstance().getSessionId(),
                LiveloPontosApp.getInstance().getLogin().getAccessToken(),
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
                                    listener.onFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            listener.onFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
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
