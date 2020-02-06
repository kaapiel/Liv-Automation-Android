package br.com.aguido.livautomation.ui.myInfo;

import android.content.Context;

import com.google.gson.Gson;

import br.com.aguido.livautomation.Constants;
import br.com.aguido.livautomation.LivAutomationApp;
import br.com.aguido.livautomation.helper.SharedPreferencesHelper;
import br.com.aguido.livautomation.service.livautomation.LivautomationException;
import br.com.aguido.livautomation.service.livautomation.LivautomationRepository;
import br.com.aguido.livautomation.service.livautomation.brandingbank.model.BrandingBank;
import br.com.aguido.livautomation.service.livautomation.brandingbank.model.PartnerPartyEnrollment;
import br.com.aguido.livautomation.service.livautomation.userprofile.model.ProfileResponse;
import br.com.aguido.livautomation.ui.home.HomeActivity;
import br.com.aguido.livautomation.util.DateUtil;

/**
 * Created by selem.gomes on 13/11/15.
 */
public class MyInfoBusiness {
    private Context context;
    private HomeActivity.OnMyInfoBusinessListener onMyInfoBusinessListener;
    private ProfileResponse profileResponse;
    private LivautomationException profileException;

    public MyInfoBusiness(Context context, HomeActivity.OnMyInfoBusinessListener onMyInfoBusinessListener) {
        this.context = context;
        this.onMyInfoBusinessListener = onMyInfoBusinessListener;
    }

    public void callServiceGetUserProfile() {
        LivautomationRepository.with(context).getUserProfile(context, getOnServiceUserProfileListener());
    }

    private void setLastUpdate(ProfileResponse response) {
        response.getUserProfileResponse().setLastUpdateData(DateUtil.getCurrentDateInMillis());
    }

    private OnServiceUserProfileListener getOnServiceUserProfileListener() {
        OnServiceUserProfileListener onServiceUserProfileListener = new OnServiceUserProfileListener() {
            @Override
            public void onGetUserProfileSuccess(ProfileResponse response) {
                LivAutomationApp.getInstance().setProfileResponse(response);
                setLastUpdate(response);
                saveInfoUserProfileInShared(response);
                profileResponse = response;
                callServiceBrandingBank();
            }

            @Override
            public void onGetUserProfileFail(LivautomationException exception) {
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
        LivautomationRepository.with(context).getBrandingBanks(context, getOnServiceBrandingBankListener());
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
            public void onFail(LivautomationException exception) {
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

        LivAutomationApp.getInstance().setBrandingBank(partnerPartyEnrollment);

        Gson gson = new Gson();
        String json = gson.toJson(partnerPartyEnrollment);
        SharedPreferencesHelper.write(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.BRANDING_BANK, json);
    }

    public interface OnServiceUserProfileListener {
        void onGetUserProfileSuccess(ProfileResponse response);

        void onGetUserProfileFail(LivautomationException exception);
    }

    public interface OnServiceBrandingBankListener {
        void onGetBrandingBankSuccess(BrandingBank brandingBank);

        void onFail(LivautomationException exception);
    }
}
