package br.com.aguido.livautomation;

import android.app.Application;
import android.text.TextUtils;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.squareup.otto.Bus;

import br.com.aguido.livautomation.helper.SharedPreferencesHelper;
import br.com.aguido.livautomation.model.Alert;
import br.com.aguido.livautomation.model.Cart;
import br.com.aguido.livautomation.service.livautomation.brandingbank.model.PartnerPartyEnrollment;
import br.com.aguido.livautomation.service.livautomation.login.model.LoginResponse;
import br.com.aguido.livautomation.service.livautomation.userprofile.model.ProfileResponse;
import br.com.aguido.livautomation.util.Util;

public class LivAutomationApp extends Application {
    private static LivAutomationApp instance;
    private Bus bus;
    private LoginResponse login;
    private Alert noConnection;
    private Alert serviceError;
    private Alert errorGeneric;
    private Alert openUrl;
    private Alert expiredSession;
    private ProfileResponse profileResponse;
    private boolean serviceCallSummaryOk = false;
    private boolean serviceCallExtractOk = false;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    private String sessionId;
    private PartnerPartyEnrollment brandingBank;
    private Cart cartUser;
    private String versionNumber = null;
    private String deviceId = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        generateAlerts();
        loadBrandinBankFromCache();
        loadSessionId();
    }

    public String getVersionNumber() {
        if(this.versionNumber != null){
            return this.versionNumber;
        }
        this.versionNumber = Util.getVersion(getBaseContext());
        return this.versionNumber;
    }

    public String getDeviceId(){
        if(this.deviceId != null){
            return this.deviceId;
        }
        this.deviceId = Util.getDeviceId(getBaseContext(), getCpf());
        return this.deviceId;
    }

    private void generateAlerts() {
        generateAlertNoConnection();
        generateAlertServiceError();
        generateAlertOpenUrl();
        generateAlertSessionExpired();
        generateAlertErrorGeneric();
    }

    private void initGoogleAnaltics() {
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker(Constants.GoogleAnalytisEvents.KEY_GOOGLE_ANALYTICS);
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
    }

    private void generateAlertNoConnection() {
        noConnection = new Alert();
        noConnection.setTitle(getResources().getString(R.string.connection_error_title));
        noConnection.setDescription(getResources().getString(R.string.connection_error_description));
        noConnection.setImage(getResources().getResourceEntryName(R.drawable.erro_conexao));
        noConnection.setPositiveButton(getResources().getString(R.string.connection_error_option_try_again));
    }

    private void generateAlertServiceError() {
        serviceError = new Alert();
        serviceError.setTitle(getResources().getString(R.string.generic_error_title));
        serviceError.setDescription(getResources().getString(R.string.generic_error_description));
        serviceError.setImage(getResources().getResourceEntryName(R.drawable.erro_generico));
        serviceError.setPositiveButton(getResources().getString(R.string.generic_error_option_try_again));
    }

    private void generateAlertOpenUrl() {
        openUrl = new Alert();
        openUrl.setTitle(getResources().getString(R.string.open_url_title));
        openUrl.setDescription(getResources().getString(R.string.open_url_description));
        openUrl.setImage(getResources().getResourceEntryName(R.drawable.erro_site));
        openUrl.setPositiveButton(getResources().getString(R.string.open_url_option_positive));
        openUrl.setNegativeButton(getResources().getString(R.string.open_url_option_negative));
    }

    private void generateAlertErrorGeneric() {
        errorGeneric = new Alert();
        errorGeneric.setTitle(getResources().getString(R.string.generic_error_title));
        errorGeneric.setDescription(getResources().getString(R.string.error_generic_description));
        errorGeneric.setImage(getResources().getResourceEntryName(R.drawable.erro_generico));
        errorGeneric.setPositiveButton(getResources().getString(R.string.error_generic_option_positive));
        errorGeneric.setNegativeButton(getResources().getString(R.string.error_generic_option_negative));
    }

    private void generateAlertSessionExpired() {
        expiredSession = new Alert();
        expiredSession.setTitle(getResources().getString(R.string.logout_session_expired_title));
        expiredSession.setDescription(getResources().getString(R.string.logout_session_expired_desc));
        expiredSession.setImage(getResources().getResourceEntryName(R.drawable.erro_generico));
        expiredSession.setPositiveButton(getResources().getString(R.string.logout_session_expired_option));
    }

    public void sendTrackerEvent(String screenName, String category, String action, String label) {
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }

    public static LivAutomationApp getInstance() {
        return instance;
    }

    public String getCpf() {
        ProfileResponse profile = loadInfoUserFromCache();
        if (profile != null) {
            return profile.getUserProfileResponse().getCpf();
        }
        return "";
    }

    public LoginResponse getLogin() {
        return login;
    }

    public void setLogin(LoginResponse login) {
        this.login = login;
    }

    public Alert getNoConnection() {
        return noConnection;
    }

    public Alert getServiceError() {
        return serviceError;
    }

    public Alert getOpenUrl() {
        return openUrl;
    }

    public Alert getErrorGeneric() {
        return errorGeneric;
    }

    public Alert getExpiredSession() {
        return expiredSession;
    }

    public ProfileResponse getProfileResponse() {
        return profileResponse;
    }

    public void setProfileResponse(ProfileResponse profileResponse) {
        this.profileResponse = profileResponse;
    }

    public boolean isServiceCallSummaryOk() {
        return serviceCallSummaryOk;
    }

    public void setServiceCallSummaryOk(boolean serviceCallSummaryOk) {
        this.serviceCallSummaryOk = serviceCallSummaryOk;
    }

    public boolean isServiceCallExtractOk() {
        return serviceCallExtractOk;
    }

    public void setServiceCallExtractOk(boolean serviceCallExtractOk) {
        this.serviceCallExtractOk = serviceCallExtractOk;
    }

    public ProfileResponse loadInfoUserFromCache() {

        String jsonUser = SharedPreferencesHelper.read(this, Constants.SharedPrefsKeys.SHARED_PREF_USER_PROFILE_NAME, Constants.SharedPrefsKeys.KEY_USER_PROFILE, null);
        ProfileResponse profileResponse = null;

        if (!TextUtils.isEmpty(jsonUser)) {

            Gson gson = new Gson();

            try {
                profileResponse = gson.fromJson(jsonUser, ProfileResponse.class);
            } catch (Exception e) {
                //Ignored
            }
        }

        return profileResponse;
    }

    public void loadBrandinBankFromCache(){
        String jsonBank = SharedPreferencesHelper.read(this, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.BRANDING_BANK, null);
        PartnerPartyEnrollment partnerPartyEnrollment = null;
        if (!TextUtils.isEmpty(jsonBank)) {

            Gson gson = new Gson();

            partnerPartyEnrollment = gson.fromJson(jsonBank, PartnerPartyEnrollment.class);
            setBrandingBank(partnerPartyEnrollment);
            return;
        }

        setBrandingBank(null);
    }

    private void loadSessionId() {
        setSessionId(Util.loadSessioId(getBaseContext()));
    }

    public String getSessionId() {
        return "SESSIONID=" + sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public PartnerPartyEnrollment getBrandingBank() {
        return brandingBank;
    }

    public void setBrandingBank(PartnerPartyEnrollment brandingBank) {
        this.brandingBank = brandingBank;
    }

    public Cart getCartUser() {
        return cartUser;
    }

    public void setCartUser(Cart cartUser) {
        this.cartUser = cartUser;
    }
}
