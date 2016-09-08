package br.com.pontomobi.livelowear.ui.validationcode;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableHeaderTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import com.squareup.otto.Subscribe;

import java.sql.Timestamp;

import br.com.pontomobi.livelowear.Constants;
import br.com.pontomobi.livelowear.LiveloWearApp;
import br.com.pontomobi.livelowear.R;
import br.com.pontomobi.livelowear.service.SendMessageThread;
import br.com.pontomobi.livelowear.service.model.WearRescueCode;

/**
 * Created by selem.gomes on 14/01/16.
 */
public class ValidationCodeFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private ImageView validationCodeBtn;
    private GoogleApiClient googleClient;
    private ProgressBar validationCodeProgressBar;
    private boolean timeRunning = false;
    private PowerManager.WakeLock wakeLock;

    private WearableHeaderTextView validationCodeTitle;
    private WearableHeaderTextView validationCodeCode;
    private WearableHeaderTextView validationCodeRenew;
    private RelativeLayout validationCodeContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_validation_code, container, false);
        LiveloWearApp.getInstance().getBus().register(this);

        googleClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        final WatchViewStub stub = (WatchViewStub) view.findViewById(R.id.validation_code_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                validationCodeBtn = (ImageView) stub.findViewById(R.id.validation_code_btn);

                validationCodeProgressBar = (ProgressBar) stub.findViewById(R.id.validation_code_progress);
                validationCodeTitle = (WearableHeaderTextView) stub.findViewById(R.id.validation_code_title);
                validationCodeCode = (WearableHeaderTextView) stub.findViewById(R.id.validation_code_code);
                validationCodeRenew = (WearableHeaderTextView) stub.findViewById(R.id.validation_code_renew);
                validationCodeContent = (RelativeLayout) stub.findViewById(R.id.validation_code_content);
                validationCodeProgressBar.setProgress(0);
                validationCodeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (timeRunning) {
                            return;
                        }
                        new SendMessageThread(Constants.RequestSendPhone.REQUEST_VALIDATION_CODE, googleClient).start();
                    }
                });

                if (LiveloWearApp.getInstance().getWearRescueCode() != null) {
                    onResultGetToken(LiveloWearApp.getInstance().getWearRescueCode());
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new SendMessageThread(Constants.RequestSendPhone.REQUEST_VALIDATION_CODE, googleClient).start();
    }

    @Subscribe
    public void onResponseValidationCode(final WearRescueCode wearRescueCode) {
        validationCodeContent.post(new Runnable() {
            @Override
            public void run() {

                if (wearRescueCode.getRescueCode().equals("000000")) {
                    showErrorGenerateCode();
                } else {
                    LiveloWearApp.getInstance().setWearRescueCode(wearRescueCode);
                    onResultGetToken(wearRescueCode);
                    startTime(wearRescueCode);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        LiveloWearApp.getInstance().getBus().unregister(this);
        super.onDestroy();
    }

    private void startTime(WearRescueCode wearRescueCode) {
        PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My wakelook");
        wakeLock.acquire();
        validationCodeProgressBar.setProgress(0);
        validationCodeProgressBar.setMax(30);

        long time = System.currentTimeMillis() - wearRescueCode.getTimeCurrent();

        //Timestamp current = new Timestamp(System.currentTimeMillis());
        //Timestamp phone = new Timestamp(System.currentTimeMillis());

        if (time > 30000) {
            time = 30000;
        } else {
            time = 30000 - time;
            validationCodeProgressBar.setProgress(Math.round((float) time / 1000.0f));
        }

        new CountDownTimer(time, 100) {
            int secondsLeft = 0;


            public void onTick(long millisUntilFinished) {
                timeRunning = true;
                if (Math.round((float) millisUntilFinished / 1000.0f) != secondsLeft) {

                    secondsLeft = Math.round((float) millisUntilFinished / 1000.0f);

                    int progress = 30 - secondsLeft;
                    validationCodeProgressBar.setProgress(progress);
                    Log.i("TIMER", progress + " s");
                }
            }

            public void onFinish() {
                LiveloWearApp.getInstance().setWearRescueCode(null);
                timeRunning = false;
                validationCodeProgressBar.setProgress(30);
                validationCodeProgressBar.setMax(30);

                validationCodeCode.setVisibility(View.GONE);
                fadeInAnimation(validationCodeRenew);

                wakeLock.release();
                new SendMessageThread(Constants.RequestSendPhone.REQUEST_DELETE_SHARED, googleClient).start();

                resetViewState();
            }
        }.start();
    }

    public void onResultGetToken(WearRescueCode wearRescueCode) {
        validationCodeTitle.setVisibility(View.GONE);
        validationCodeRenew.setVisibility(View.GONE);
        validationCodeCode.setText(wearRescueCode.getRescueCode());

        fadeInAnimation(validationCodeCode);
    }

    private void showErrorGenerateCode(){
        validationCodeTitle.setVisibility(View.GONE);
        validationCodeCode.setVisibility(View.GONE);
        validationCodeRenew.setText(R.string.validation_code_error);
        fadeInAnimation(validationCodeRenew);
    }

    private void resetViewState(){
        validationCodeTitle.setVisibility(View.GONE);
        validationCodeCode.setVisibility(View.GONE);
        validationCodeRenew.setText(R.string.validation_code_renew);
        fadeInAnimation(validationCodeRenew);
    }

    private void fadeInAnimation(WearableHeaderTextView view) {
        view.setVisibility(View.VISIBLE);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);
        view.startAnimation(fadeIn);
    }

    @Override
    public void onStart() {
        super.onStart();
        googleClient.connect();
    }

    // Disconnect from the data layer when the Activity stops
    @Override
    public void onStop() {
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }

        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
