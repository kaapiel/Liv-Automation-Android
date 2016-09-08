package br.com.pontomobi.livelopontos.ui.activateDevice.activatecode;

import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.rey.material.widget.EditText;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.broadcast.SMSReceiver;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.activationmobiletoken.ActivationTokenResponse;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ProfileResponse;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.UserProfileResponse;
import br.com.pontomobi.livelopontos.ui.activateDevice.ActivateDeviceActivity;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.util.gcm.GCMRegisterAsync;
import br.com.pontomobi.livelopontos.util.gcm.GCMUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vilmar.filho on 12/23/15.
 */
public class ActivateCodeFragment extends LiveloPontoFragment {

    private static final String BUNDLE_REQUEST_SMS = "b_request_sms";

    @Bind(R.id.et_code) EditText mCodeActivation;
    @Bind(R.id.root_activate_code) LinearLayout mRootFrag;
    @Bind(R.id.token_activation) AppCompatButton mTokenActivation;

    private RelativeLayout mLoadingContent;

    private ActivateCodeBusiness mActivateCodeBusiness;
    private GoogleCloudMessaging mGcm;
    private String regId;

    private SMSReceiver mSmsReceiver;

    public static ActivateCodeFragment newInstance(boolean requestSMS){
        ActivateCodeFragment f = new ActivateCodeFragment();

        Bundle args = new Bundle();
        args.putBoolean(BUNDLE_REQUEST_SMS, requestSMS);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivateCodeBusiness = new ActivateCodeBusiness(getActivity(), getOnActivateMobileToken());

        if(getArguments() != null){
            boolean isRequestSMS = getArguments().getBoolean(BUNDLE_REQUEST_SMS, false);
            if(isRequestSMS){
                callServiceRequestSMS();
            }
        }


        if (GCMUtil.checkPlayServices((ActivateDeviceActivity) getActivity())) {
            mGcm = GoogleCloudMessaging.getInstance(getActivity());
            regId = GCMUtil.getRegistrationId(getActivity());

            if (regId.trim().length() == 0) {
                registerDevicePush();
            }
        }

        registerReceiver();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activate_code, container, false);

        ButterKnife.bind(this, v);

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTypefaceInEditText();

        mLoadingContent = (RelativeLayout) getActivity().findViewById(R.id.loading_content);
        textChangedListeners();
    }

    private void textChangedListeners() {
        mCodeActivation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() < 6) {
                    mTokenActivation.setTextColor(getResources().getColor(R.color.gray_888888));
                    mTokenActivation.setEnabled(false);
                } else {
                    mTokenActivation.setTextColor(getResources().getColor(android.R.color.white));
                    mTokenActivation.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    private void registerReceiver() {
        mSmsReceiver = new SMSReceiver(getListenerSMS());
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(9999);
        getActivity().registerReceiver(mSmsReceiver, intentFilter);
    }


    private void registerDevicePush(){
        GCMRegisterAsync gcmRegisterAsync = new GCMRegisterAsync(mGcm, Constants.GCM_SENDER_ID, getOnGCMRegisterCompleted());
        gcmRegisterAsync.execute();
    }


    private SMSReceiver.SMSReceiverListener getListenerSMS(){
        return new SMSReceiver.SMSReceiverListener() {
            @Override
            public void successReceiver(String code) {
                if (isAdded()) {
                    if (code.length() < 6) return;

                    mCodeActivation.requestFocus();
                    mCodeActivation.setText("");
                    mCodeActivation.setText(code);

                    activationCode();
                }
            }
        };
    }


    private GCMRegisterAsync.OnGCMRegisterCompleted getOnGCMRegisterCompleted(){
        return new GCMRegisterAsync.OnGCMRegisterCompleted() {
            @Override
            public void onRegisterCompleted(String regId) {
                Log.i("GCMRegisterAsync", regId);
                GCMUtil.storeRegistrationId(getContext(), regId);
            }
        };
    }


    private ActivateCodeBusiness.OnActivateMobileToken getOnActivateMobileToken(){
        return new ActivateCodeBusiness.OnActivateMobileToken() {
            @Override
            public void onActivateMobileTokenSuccess(ActivationTokenResponse response) {
                mLoadingContent.setVisibility(View.GONE);
                ((ActivateDeviceActivity) getActivity()).goTo(ActivateDeviceActivity.WELCOME);
            }

            @Override
            public void onActivateMobileTokenFail(final LiveloException exception) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingContent.setVisibility(View.GONE);
                        if (exception.getErrorCode() == LiveloException.EXCEPTION_SERVICE_ERROR) {
                            showBannerErrorRegister();
                        } else {
                            showDialogNoConnection(exception.getAlertToShow(getActivity()));
                        }
                    }
                }, 500);
            }
        };
    }


    private void showBannerErrorRegister() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(getActivity(), LiveloPontosApp.getInstance().getErrorGeneric(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callServiceActivationSMS();
                        }

                        @Override
                        public void onNegativeClick() {
                            Log.d("DIALOG", "NEGATIVE");
                        }

                        @Override
                        public void onBackPressedInDialog() {

                        }
                    });
        }
    }


    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if(isAlive()) {
            dialogCustom.showCustomDialog(getActivity(), alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callServiceActivationSMS();
                        }

                        @Override
                        public void onNegativeClick() {
                        }

                        @Override
                        public void onBackPressedInDialog() {

                        }
                    });
        }
    }


    private void setTypefaceInEditText() {
        Typeface MuseoSans300 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MuseoSans_300.otf");
        mCodeActivation.setTypeface(MuseoSans300);
    }


    @OnClick(R.id.token_activation)
    public void activationCode(){

        if(mCodeActivation.getText().toString().length() < 6){
            showMessage(R.string.activate_device_error_code);
        } else {
            callServiceActivationSMS();
        }
    }


    @OnClick(R.id.send_code_again)
    public void requestCode(){
        callServiceRequestSMS();
        showMessage(R.string.activate_device_text_send_sms);
    }


    private void callServiceRequestSMS(){

        ProfileResponse profileResponse = LiveloPontosApp.getInstance().getProfileResponse();

        if(profileResponse != null && profileResponse.getUserProfileResponse() != null){
            UserProfileResponse userProfile = profileResponse.getUserProfileResponse();

            mActivateCodeBusiness.requestCode(userProfile.getUserPhoneByType("M"));
        } else {
            ((ActivateDeviceActivity) getActivity()).loadInfoFromCache();
        }
    }


    private void callServiceActivationSMS(){
        mLoadingContent.setVisibility(View.VISIBLE);

        String code = mCodeActivation.getText().toString();
        UserProfileResponse userProfile = LiveloPontosApp.getInstance().getProfileResponse().getUserProfileResponse();

        mActivateCodeBusiness.activateCodeSMS(userProfile, code);
    }


    private void showMessage(int rscMessage){
        Snackbar.make(mRootFrag, rscMessage, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        getActivity().unregisterReceiver(mSmsReceiver);
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
