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
import br.com.pontomobi.livelopontos.ui.activateAccount.ActivateBusiness;
import br.com.pontomobi.livelopontos.ui.activateDevice.ActivateDeviceBusiness;
import br.com.pontomobi.livelopontos.ui.activateDevice.activatecode.ActivateCodeBusiness;
import br.com.pontomobi.livelopontos.ui.cart.CartBusiness;
import br.com.pontomobi.livelopontos.ui.changePassword.ChangePasswordBusiness;
import br.com.pontomobi.livelopontos.ui.forgotPassword.ForgotPasswordBusiness;
import br.com.pontomobi.livelopontos.ui.login.LoginBusiness;
import br.com.pontomobi.livelopontos.ui.myInfo.MyInfoBusiness;
import br.com.pontomobi.livelopontos.ui.myPoints.summary.SummaryBusiness;
import br.com.pontomobi.livelopontos.ui.orderHistory.OrderBusiness;
import br.com.pontomobi.livelopontos.ui.registerUser.RegisterUserBusiness;
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
     * Recupera o total de pontos do usuário.
     */
    public void getAmountPoints(final Context context,
                                final SummaryBusiness.OnServiceAmountPointsListener onServiceAmountPointsListener) {
        if (!NetworkHelper.isOnline(context)) {
            onServiceAmountPointsListener.onGetPontsFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(AmountPointsService.class).pointsBalance(LiveloPontosApp.getInstance().getLogin().getAccessToken(), new Callback<AmountPointsResponse>() {
            @Override
            public void success(AmountPointsResponse amountPointsResponse, Response response) {
                if (amountPointsResponse == null) {
                    Log.d(LIVELO_REPOSITORY_TAG, "Falha ao recuperar pontos");
                    onServiceAmountPointsListener.onGetPontsFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                    return;
                }

                Log.d(LIVELO_REPOSITORY_TAG, "Sucesso ao recuperar pontos");
                onServiceAmountPointsListener.onGetPointsSuccess(amountPointsResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LIVELO_REPOSITORY_TAG, "Falha ao recuperar pontos");


                if (error.getResponse() != null &&
                        error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {
                    getNewToken(context, new OnRefreshTokenListener() {
                        @Override
                        public void onRefreshTokenSucess() {
                            getAmountPoints(context, onServiceAmountPointsListener);
                        }

                        @Override
                        public void onRefreshTokenFail() {
                            onServiceAmountPointsListener.onGetPontsFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                        }
                    });
                } else {
                    onServiceAmountPointsListener.onGetPontsFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                }
            }
        });
    }


    /**
     * Recupera a lista de transações do usuário.
     */
    public void getListUserTransactions(final Context context, final UserTransactionRequest userTransactionRequest,
                                        final SummaryBusiness.OnServiceUserTransactionListener onServiceUserTransactionListener) {
        if (!NetworkHelper.isOnline(context)) {
            onServiceUserTransactionListener.onGetUserTransactionFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }
        connect().create(UserTransactionsService.class).listUserTransactions(LiveloPontosApp.getInstance().getLogin().getAccessToken(),
                userTransactionRequest.getDateFrom(),
                userTransactionRequest.getDateTo(),
                new Callback<UserTransactionsResponse>() {
                    @Override
                    public void success(UserTransactionsResponse userTransactionsResponse, Response response) {
                        if (userTransactionsResponse == null) {
                            Log.d(LIVELO_REPOSITORY_TAG, "Falha ao recuperar lista de transações");
                            onServiceUserTransactionListener.onGetUserTransactionFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                            return;
                        }

                        Log.d(LIVELO_REPOSITORY_TAG, "Sucesso ao recuperar lista de transações");
                        onServiceUserTransactionListener.onGetUserTransactionSuccess(userTransactionsResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LIVELO_REPOSITORY_TAG, "Falha ao recuperar lista de transações");

                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {

                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    getListUserTransactions(context, userTransactionRequest, onServiceUserTransactionListener);
                                }

                                @Override
                                public void onRefreshTokenFail() {
                                    onServiceUserTransactionListener.onGetUserTransactionFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            onServiceUserTransactionListener.onGetUserTransactionFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }
                });
    }

    /**
     * Recupera o total de pontos do usuário.
     */
    public void getPointsExpired(final Context context,
                                 final SummaryBusiness.OnServicePointsExpiredListener onServicePointsExpiredListener) {
        if (!NetworkHelper.isOnline(context)) {
            onServicePointsExpiredListener.onGetPointsExpiredFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(PointsExpireService.class).pointsExpired(LiveloPontosApp.getInstance().getLogin().getAccessToken(), new Callback<PointsExpiredOutputResponse>() {
            @Override
            public void success(PointsExpiredOutputResponse pointsExpiredOutputResponse, Response response) {
                if (pointsExpiredOutputResponse == null
                        || pointsExpiredOutputResponse.getPointsExpiredResponse() == null) {
                    Log.d(LIVELO_REPOSITORY_TAG, "Falha ao recuperar pontos");
                    onServicePointsExpiredListener.onGetPointsExpiredFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                    return;
                }

                Log.d(LIVELO_REPOSITORY_TAG, "Sucesso ao recuperar pontos");
                onServicePointsExpiredListener.onGetPointsExpiredSuccess(pointsExpiredOutputResponse.getPointsExpiredResponse());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LIVELO_REPOSITORY_TAG, "Falha ao recuperar pontos");

                if (error.getResponse() != null &&
                        error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {
                    getNewToken(context, new OnRefreshTokenListener() {
                        @Override
                        public void onRefreshTokenSucess() {
                            getPointsExpired(context, onServicePointsExpiredListener);
                        }

                        @Override
                        public void onRefreshTokenFail() {
                            onServicePointsExpiredListener.onGetPointsExpiredFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                        }
                    });
                } else {
                    onServicePointsExpiredListener.onGetPointsExpiredFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                }
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

    public void getUserOrders(final Context context,
                              final OrderBusiness.OnServiceOrdersListener onServiceOrdersListener) {
        if (!NetworkHelper.isOnline(context)) {
            onServiceOrdersListener.onGetOrdersFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(OrderService.class).pointsExpired(LiveloPontosApp.getInstance().getLogin().getAccessToken(), new Callback<OrderResponse>() {
            @Override
            public void success(OrderResponse orderResponse, Response response) {
                if (orderResponse == null) {
                    onServiceOrdersListener.onGetOrdersFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                    return;
                }

                onServiceOrdersListener.onGetOrdersSuccess(orderResponse);
            }

            @Override
            public void failure(RetrofitError error) {

                if (error.getResponse() != null &&
                        error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {
                    getNewToken(context, new OnRefreshTokenListener() {
                        @Override
                        public void onRefreshTokenSucess() {
                            getUserOrders(context, onServiceOrdersListener);
                        }

                        @Override
                        public void onRefreshTokenFail() {
                            onServiceOrdersListener.onGetOrdersFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                        }
                    });
                } else {
                    onServiceOrdersListener.onGetOrdersFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                }
            }
        });
    }


    public void activateUser(final Context context, final ActivateRequest activateRequest, final ActivateBusiness.OnServiceActivateListener onServiceActivateListener) {
        if (!NetworkHelper.isOnline(context)) {
            onServiceActivateListener.onLoginFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(ActivateService.class).activateUser(activateRequest, new Callback<ActivateUserResponse>() {
            @Override
            public void success(ActivateUserResponse activateUserResponse, Response response) {

                String jSessionId = getCookieJSessionID(response);

                if (activateUserResponse == null || activateUserResponse.getOutput() == null) {
                    onServiceActivateListener.onLoginFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                    return;
                }

                activateUserResponse.setjSessionId(jSessionId);
                onServiceActivateListener.onActivateSuccess(activateUserResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getResponse() != null &&
                        error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {
                    getNewToken(context, new OnRefreshTokenListener() {
                        @Override
                        public void onRefreshTokenSucess() {
                            activateUser(context, activateRequest, onServiceActivateListener);
                        }

                        @Override
                        public void onRefreshTokenFail() {
                            onServiceActivateListener.onLoginFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                        }
                    });
                } else {
                    onServiceActivateListener.onLoginFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                }
            }
        });
    }


    public void registerUser(final Context context, final RegisterUserRequest registerUserRequest, final RegisterUserBusiness.OnServiceRegisterListener onServiceRegisterListener) {
        if (!NetworkHelper.isOnline(context)) {
            onServiceRegisterListener.onRegisterFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""), null);
            return;
        }

        connect(getHeaderSession(registerUserRequest.getjSessionId())).create(RegistrationService.class).registerUser(registerUserRequest, new Callback<LiveloResponse>() {
            @Override
            public void success(LiveloResponse liveloResponse, Response response) {

                if (liveloResponse != null) {
                    if (liveloResponse.isFormError()) {
                        onServiceRegisterListener.onRegisterFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""), liveloResponse.getAuthenticationToken());
                        return;
                    }
                }

                onServiceRegisterListener.onRegisterSuccess(liveloResponse.getAuthenticationToken());
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getResponse() != null && error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {
                    getNewToken(context, new OnRefreshTokenListener() {
                        @Override
                        public void onRefreshTokenSucess() {
                            registerUser(context, registerUserRequest, onServiceRegisterListener);
                        }

                        @Override
                        public void onRefreshTokenFail() {
                            onServiceRegisterListener.onRegisterFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""), null);
                        }
                    });
                } else {
                    onServiceRegisterListener.onRegisterFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""), null);
                }
            }
        });
    }


    public void retrieveDevices(final Context context, final ActivateDeviceBusiness.OnRetrieveDevice onRetrieveDevice) {
        if (!NetworkHelper.isOnline(context)) {
            onRetrieveDevice.onRetrieveFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(ActivateDeviceService.class).retrieveMobile(LiveloPontosApp.getInstance().getLogin().getAccessToken(), new Callback<RetrieveDeviceResponse>() {

            @Override
            public void success(RetrieveDeviceResponse retrieveDeviceResponse, Response response) {
                if (retrieveDeviceResponse == null) {
                    onRetrieveDevice.onRetrieveFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                    return;
                }

                onRetrieveDevice.onRetrieveSuccess(retrieveDeviceResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getResponse() != null &&
                        error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {
                    getNewToken(context, new OnRefreshTokenListener() {
                        @Override
                        public void onRefreshTokenSucess() {
                            retrieveDevices(context, onRetrieveDevice);
                        }

                        @Override
                        public void onRefreshTokenFail() {
                            onRetrieveDevice.onRetrieveFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                        }
                    });
                } else {
                    onRetrieveDevice.onRetrieveFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                }
            }
        });
    }


    public void
    requestCodeSMS(final Context context, final TransactionTokenRequest transactionTokenRequest, final ActivateCodeBusiness.OnRequestCodeSMS onRequestCodeSMS) {
        if (!NetworkHelper.isOnline(context)) {
            onRequestCodeSMS.onRequestCodeSMSFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(ActivateDeviceService.class).requestCodeSMS(LiveloPontosApp.getInstance().getLogin().getAccessToken(),
                transactionTokenRequest, new Callback<TransactionTokenResponse>() {

                    @Override
                    public void success(TransactionTokenResponse transactionTokenResponse, Response response) {
                        if (transactionTokenResponse == null) {
                            onRequestCodeSMS.onRequestCodeSMSFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                            return;
                        }

                        onRequestCodeSMS.onRequestCodeSMSSuccess(transactionTokenResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {
                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    requestCodeSMS(context, transactionTokenRequest, onRequestCodeSMS);
                                }

                                @Override
                                public void onRefreshTokenFail() {
                                    onRequestCodeSMS.onRequestCodeSMSFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            onRequestCodeSMS.onRequestCodeSMSFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }
                });
    }


    public void activationCodeSMS(final Context context, final ActivationTokenRequest activationTokenRequest, final ActivateCodeBusiness.OnActivateMobileToken onActivateMobileToken) {
        if (!NetworkHelper.isOnline(context)) {
            onActivateMobileToken.onActivateMobileTokenFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(ActivateDeviceService.class).activationMobileToken(LiveloPontosApp.getInstance().getLogin().getAccessToken(),
                activationTokenRequest, new Callback<ActivationTokenResponse>() {

                    @Override
                    public void success(ActivationTokenResponse activationTokenResponse, Response response) {
                        if (activationTokenResponse == null) {
                            onActivateMobileToken.onActivateMobileTokenFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                            return;
                        } else if (!activationTokenResponse.isActivateMobileTokenResponse()) {
                            onActivateMobileToken.onActivateMobileTokenFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                            return;
                        }

                        onActivateMobileToken.onActivateMobileTokenSuccess(activationTokenResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {
                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    activationCodeSMS(context, activationTokenRequest, onActivateMobileToken);
                                }

                                @Override
                                public void onRefreshTokenFail() {
                                    onActivateMobileToken.onActivateMobileTokenFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            onActivateMobileToken.onActivateMobileTokenFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }
                });
    }

    /**
     * Envia para o servidor a solicitação de recuperar senha.
     */
    public void forgotPassword(final Context context, ForgotPasswordRequest request,
                               final ForgotPasswordBusiness.OnRecoverCallListener listener) {
        if (!NetworkHelper.isOnline(context)) {
            listener.onRecoverCallFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(ForgotPasswordService.class).recoverPassword(request, new Callback<ForgotPasswordResponse>() {
            @Override
            public void success(ForgotPasswordResponse forgotPasswordResponse, Response response) {
                listener.onRecoverCallSuccess();
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onRecoverCallFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
            }
        });
    }


    /**
     * Alterar a senha do usuário.
     */
    public void changePassword(final Context context, final ChangePasswordRequest request,
                               final ChangePasswordBusiness.OnChangeCallServiceListener listener) {
        if (!NetworkHelper.isOnline(context)) {
            listener.onFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }
        connect().create(ChangePasswordService.class).changePassword(LiveloPontosApp.getInstance().getLogin().getAccessToken(),
                request,
                new Callback<ChangePasswordReponse>() {
                    @Override
                    public void success(ChangePasswordReponse changePasswordReponse, Response response) {
                        listener.onSuccess();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {

                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    changePassword(context, request, listener);
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

    public void getCart(final String sessionId, final CartBusiness.OnServiceCartListener listener) {
        if (!NetworkHelper.isOnline(context)) {
            listener.onFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(CartService.class).getCart(LiveloPontosApp.getInstance().getLogin().getAccessToken(),
                sessionId,
                new Callback<CartResponse>() {
                    @Override
                    public void success(CartResponse cartResponse, Response response) {
                        if (cartResponse != null) {
                            listener.onSuccess(cartResponse);
                        } else {
                            listener.onFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {

                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    getCart(sessionId, listener);
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

    public void updateProductCart(final String idProduct, final UpdateResquest updateResquest, final CartBusiness.OnServiceUpdateCartListener listener) {
        if (!NetworkHelper.isOnline(context)) {
            listener.onUpdateFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(CartService.class).updateProduct(LiveloPontosApp.getInstance().getLogin().getAccessToken(),
                idProduct,
                updateResquest,
                new Callback<Object>() {
                    @Override
                    public void success(Object o, Response response) {
                        if (response.getStatus() == 200) {
                            listener.onUpdateSuccess();
                        } else {
                            listener.onUpdateFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {

                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    updateProductCart(idProduct, updateResquest, listener);
                                }

                                @Override
                                public void onRefreshTokenFail() {
                                    listener.onUpdateFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            listener.onUpdateFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }
                });
    }

    public void deleteProductCart(final String idProduct, final CartBusiness.OnServiceDeleteListener listener) {
        if (!NetworkHelper.isOnline(context)) {
            listener.onDeleteFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(CartService.class).deleteProduct(LiveloPontosApp.getInstance().getLogin().getAccessToken(),
                idProduct,
                new Callback<Object>() {
                    @Override
                    public void success(Object o, Response response) {
                        if (response.getStatus() == 200) {
                            listener.onDeleteSuccess();
                        } else {
                            listener.onDeleteFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {

                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    deleteProductCart(idProduct, listener);
                                }

                                @Override
                                public void onRefreshTokenFail() {
                                    listener.onDeleteFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            listener.onDeleteFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }
                });
    }

    public void claimCoupon(final CoupomRequest coupomRequest, final CartBusiness.OnServiceCouponListener listener) {
        if (!NetworkHelper.isOnline(context)) {
            listener.onCouponFail(new LiveloException(LiveloException.EXCEPTION_INTERNET_ERROR, ""));
            return;
        }

        connect().create(CartService.class).claimCoupon(LiveloPontosApp.getInstance().getLogin().getAccessToken(),
                coupomRequest,
                new Callback<br.com.pontomobi.livelopontos.service.livelo.cart.LiveloResponse>() {
                    @Override
                    public void success(br.com.pontomobi.livelopontos.service.livelo.cart.LiveloResponse liveloResponse, Response response) {
                        if (response.getStatus() == 200 && liveloResponse == null) {
                            listener.onCouponSuccess();
                        } else {
                            listener.onCouponFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse() != null &&
                                error.getResponse().getStatus() == Constants.ApiErroCodeReturn.INVALID_TOKEN) {

                            getNewToken(context, new OnRefreshTokenListener() {
                                @Override
                                public void onRefreshTokenSucess() {
                                    claimCoupon(coupomRequest, listener);
                                }

                                @Override
                                public void onRefreshTokenFail() {
                                    listener.onCouponFail(new LiveloException(LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR, ""));
                                }
                            });
                        } else {
                            listener.onCouponFail(new LiveloException(LiveloException.EXCEPTION_SERVICE_ERROR, ""));
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
