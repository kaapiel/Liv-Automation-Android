package br.com.pontomobi.livelopontos.ui.myInfo;

import android.content.Context;

import com.google.gson.Gson;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.brandingbank.model.BrandingBank;
import br.com.pontomobi.livelopontos.service.livelo.brandingbank.model.PartnerPartyEnrollment;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ProfileResponse;
import br.com.pontomobi.livelopontos.ui.home.HomeActivity;
import br.com.pontomobi.livelopontos.util.DateUtil;

/**
 * Created by selem.gomes on 13/11/15.
 */
public class MyInfoBusiness {
    private Context context;
    private HomeActivity.OnMyInfoBusinessListener onMyInfoBusinessListener;
    private ProfileResponse profileResponse;
    private LiveloException profileException;

    public MyInfoBusiness(Context context, HomeActivity.OnMyInfoBusinessListener onMyInfoBusinessListener) {
        this.context = context;
        this.onMyInfoBusinessListener = onMyInfoBusinessListener;
    }

    public void callServiceGetUserProfile() {
        LiveloRepository.with(context).getUserProfile(context, getOnServiceUserProfileListener());
    }

    private void setLastUpdate(ProfileResponse response) {
        response.getUserProfileResponse().setLastUpdateData(DateUtil.getCurrentDateInMillis());
    }

    private OnServiceUserProfileListener getOnServiceUserProfileListener() {
        OnServiceUserProfileListener onServiceUserProfileListener = new OnServiceUserProfileListener() {
            @Override
            public void onGetUserProfileSuccess(ProfileResponse response) {
                LiveloPontosApp.getInstance().setProfileResponse(response);
                setLastUpdate(response);
                saveInfoUserProfileInShared(response);
                profileResponse = response;
                callServiceBrandingBank();
            }

            @Override
            public void onGetUserProfileFail(LiveloException exception) {
                profileException = exception;
                callServiceBrandingBank();
            }
        };

        return onServiceUserProfileListener;
    }

    private void saveInfoUserProfileInShared(ProfileResponse response) {
        Gson gson = new Gson();
        String json = gson.toJson(response);
        SharedPreferencesHelper.write(context, Constants.SharedPrefsKeys.SHARED_PREF_USER_PROFILE_NAME, Constants.SharedPrefsKeys.KEY_USER_PROFILE, json);
    }

    private void callServiceBrandingBank() {
        LiveloRepository.with(context).getBrandingBanks(context, getOnServiceBrandingBankListener());
    }


    private OnServiceBrandingBankListener getOnServiceBrandingBankListener() {
        return new OnServiceBrandingBankListener() {
            @Override
            public void onGetBrandingBankSuccess(BrandingBank brandingBank) {
                if(brandingBank != null && brandingBank.getPartnerPartyEnrollmentList() != null) {
                    PartnerPartyEnrollment partnerPartyEnrollment = brandingBank.getPartnerPartyEnrollmentList().getPartnerPartyEnrollmentEnable();
                    saveInfoBrandingBankInShared(partnerPartyEnrollment);
                }

                onMyInfoBusinessListener.onMyInfoBusinessSuccess(profileResponse);
            }

            @Override
            public void onFail(LiveloException exception) {
                if(profileResponse != null) {
                    onMyInfoBusinessListener.onMyInfoBusinessSuccess(profileResponse);
                } else {
                    onMyInfoBusinessListener.onMyInfoBusinessFail(profileException);
                }
            }
        };
    }

    private void saveInfoBrandingBankInShared(PartnerPartyEnrollment partnerPartyEnrollment) {

        if (partnerPartyEnrollment == null) {
            return;
        }

        LiveloPontosApp.getInstance().setBrandingBank(partnerPartyEnrollment);

        Gson gson = new Gson();
        String json = gson.toJson(partnerPartyEnrollment);
        SharedPreferencesHelper.write(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.BRANDING_BANK, json);
    }

    public interface OnServiceUserProfileListener {
        void onGetUserProfileSuccess(ProfileResponse response);

        void onGetUserProfileFail(LiveloException exception);
    }

    public interface OnServiceBrandingBankListener {
        void onGetBrandingBankSuccess(BrandingBank brandingBank);

        void onFail(LiveloException exception);
    }
}
