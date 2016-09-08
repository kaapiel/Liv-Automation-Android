package br.com.pontomobi.livelopontos.ui.activateDevice.activatecode;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.iid.InstanceID;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.activationmobiletoken.ActivationTokenRequest;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.activationmobiletoken.ActivationTokenResponse;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.activationmobiletoken.MobileDevice;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.transactiontoken.Phone;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.transactiontoken.TransactionTokenRequest;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.transactiontoken.TransactionTokenResponse;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.UserPhoneResponse;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.UserProfileResponse;
import br.com.pontomobi.livelopontos.util.DeviceManager;
import br.com.pontomobi.livelopontos.util.StringUtil;
import br.com.pontomobi.livelopontos.util.gcm.GCMUtil;

/**
 * Created by vilmar.filho on 1/25/16.
 */
public class ActivateCodeBusiness {

    private static final String REQUESTOR_AGENT_ID = "Mobile App";
    private static final String REASCON_CODE = "7";
    private static final String PLATFORM_NAME = "ANDROID";

    private Context mContext;
    private OnActivateMobileToken mOnActivateMobileToken;

    private UserProfileResponse mUserProfileResponse;
    private String mSmsToken;
    private String mDeviceId;


    public ActivateCodeBusiness(Context mContext, OnActivateMobileToken onActivateMobileToken) {
        this.mContext = mContext;
        this.mOnActivateMobileToken = onActivateMobileToken;
    }


    public void requestCode(UserPhoneResponse userPhone) {
        int countryCode = 0;
        int areaCode = 0;
        int number = 0 ;

        if(userPhone != null) {
            countryCode = TextUtils.isEmpty(userPhone.getPhoneCountryCode()) ? 0 : Integer.parseInt(userPhone.getPhoneCountryCode());
            areaCode = TextUtils.isEmpty(userPhone.getPhoneAreaCode()) ? 0 : Integer.parseInt(userPhone.getPhoneAreaCode());
            number = TextUtils.isEmpty(userPhone.getPhoneNo()) ? 0 : Integer.parseInt(userPhone.getPhoneNo());
        }

        Phone phone = new Phone(countryCode, areaCode, number);

        LiveloRepository.with(mContext).requestCodeSMS(
                mContext,
                new TransactionTokenRequest(phone, REQUESTOR_AGENT_ID, REASCON_CODE),
                getOnRequestCodeSMS());
    }


    public void activateCodeSMS(UserProfileResponse userProfileResponse, String token) {
        mUserProfileResponse = userProfileResponse;
        mSmsToken = token;
        mDeviceId = getDeviceId(mUserProfileResponse.getCpf(), token);

        String deviceModel = getDeviceModel();
        String deviceName = PLATFORM_NAME;
        String deviceNumber = getCellPhone(mUserProfileResponse.getPhoneNumbers());
        String pushNotificationToken = GCMUtil.getRegistrationId(mContext);

        LiveloRepository.with(mContext).activationCodeSMS(mContext,
                new ActivationTokenRequest(mSmsToken, new MobileDevice(mDeviceId, deviceModel, deviceName, deviceNumber, pushNotificationToken)),
                getOnActivateMobileToken());
    }


    private OnRequestCodeSMS getOnRequestCodeSMS() {
        return new OnRequestCodeSMS() {
            @Override
            public void onRequestCodeSMSSuccess(TransactionTokenResponse response) {
                Log.i("OnRequestCodeSMS", "onRequestCodeSMSSuccess()");
            }

            @Override
            public void onRequestCodeSMSFail(LiveloException exception) {
                Log.i("OnRequestCodeSMS", "onRequestCodeSMSFail()");
            }
        };
    }


    private OnActivateMobileToken getOnActivateMobileToken() {
        return new OnActivateMobileToken() {
            @Override
            public void onActivateMobileTokenSuccess(ActivationTokenResponse response) {
                String cpf = TextUtils.isEmpty(mUserProfileResponse.getCpf()) ? "111" : mUserProfileResponse.getCpf();
                boolean success = DeviceManager.with(mContext).saveDeviceInfo(cpf, mDeviceId, mSmsToken);

                if (success)
                    SharedPreferencesHelper.write(mContext, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.DEVICE_ACTIVE, true);

                mOnActivateMobileToken.onActivateMobileTokenSuccess(response);
            }

            @Override
            public void onActivateMobileTokenFail(LiveloException exception) {
                mOnActivateMobileToken.onActivateMobileTokenFail(exception);
            }
        };
    }


    private String getDeviceModel() {
        return StringUtil.nullSafeReplaceAll(Build.MODEL, " ", "_");
    }


    private String getDeviceId(String cpf, String token) {
        long timestamp = Calendar.getInstance().getTime().getTime();
        String deviceId = cpf + token + timestamp + InstanceID.getInstance(mContext).getId();

        return sha256(deviceId);
    }


    private static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getCellPhone(List<UserPhoneResponse> listPhones) {

        String phone = "";

        if (listPhones == null) {
            return phone;
        }

        for (int i = 0; i < listPhones.size(); i++) {
            UserPhoneResponse userPhone = listPhones.get(i);
            if (userPhone.getPhoneType().equalsIgnoreCase("M")) { //M = Mobile
                phone = userPhone.getPhoneAreaCode() + userPhone.getPhoneNo();
                break;
            }
        }

        return phone;
    }

    public interface OnRequestCodeSMS {
        void onRequestCodeSMSSuccess(TransactionTokenResponse response);

        void onRequestCodeSMSFail(LiveloException exception);
    }


    public interface OnActivateMobileToken {
        void onActivateMobileTokenSuccess(ActivationTokenResponse response);

        void onActivateMobileTokenFail(LiveloException exception);
    }
}
