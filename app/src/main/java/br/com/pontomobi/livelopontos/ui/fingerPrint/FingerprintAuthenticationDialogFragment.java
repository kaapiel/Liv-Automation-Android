package br.com.pontomobi.livelopontos.ui.fingerPrint;

import android.app.Activity;
import android.app.DialogFragment;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;

/**
 * Created by selemafonso on 15/04/16.
 */
public class FingerprintAuthenticationDialogFragment extends DialogFragment implements FingerprintUiHelper.Callback {

    private Button mCancelButton;

    private FingerprintManager.CryptoObject mCryptoObject;
    private FingerprintUiHelper mFingerprintUiHelper;
    private LoginActivity mActivity;
    private LoginActivity.OnLoginByFingerPrint onLoginByFingerPrint;
    private FingerprintManager fingerprintManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle(getString(R.string.fingerprint_sign_in));
        View v = inflater.inflate(R.layout.fingerprint_dialog_container, container, false);
        mCancelButton = (Button) v.findViewById(R.id.second_dialog_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onLoginByFingerPrint.onResultLogin(false);
            }
        });

        mFingerprintUiHelper = new FingerprintUiHelper(fingerprintManager, (ImageView) v.findViewById(R.id.fingerprint_icon),
                (TextView) v.findViewById(R.id.fingerprint_status), this);
        getDialog().setCanceledOnTouchOutside(false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFingerprintUiHelper.startListening(mCryptoObject);
    }

    public void setmCryptoObject(FingerprintManager.CryptoObject mCryptoObject) {
        this.mCryptoObject = mCryptoObject;
    }

    public void setOnLoginByFingerPrint(LoginActivity.OnLoginByFingerPrint onLoginByFingerPrint) {
        this.onLoginByFingerPrint = onLoginByFingerPrint;
    }

    public void setFingerprintManager(FingerprintManager fingerprintManager) {
        this.fingerprintManager = fingerprintManager;
    }

    @Override
    public void onPause() {
        super.onPause();
        mFingerprintUiHelper.stopListening();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (LoginActivity) activity;
    }

    @Override
    public void onAuthenticated() {
        // Callback from FingerprintUiHelper. Let the activity know that authentication was
        // successful.
        onLoginByFingerPrint.onResultLogin(true /* withFingerprint */);
        dismiss();
    }

    @Override
    public void onError() {
        onLoginByFingerPrint.onResultLogin(false);
    }

}
