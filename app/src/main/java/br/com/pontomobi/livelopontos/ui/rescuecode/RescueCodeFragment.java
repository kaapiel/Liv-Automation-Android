package br.com.pontomobi.livelopontos.ui.rescuecode;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.service.wear.model.WearRescueCode;
import br.com.pontomobi.livelopontos.util.RescueCodeUtil;

/**
 * Created by vilmar.filho on 12/11/15.
 */
public class RescueCodeFragment extends LiveloPontoFragment {

    private TextView mGenerateCode;
    private TextView mTvSeconds;
    private ProgressBar mProgressBar;
    private AppCompatButton mBtnGenerate;

    private CountDownTimer mCountDownTimer;
    private boolean timeRunning;

    public static RescueCodeFragment newInstance() {
        return new RescueCodeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rescue_code_generate, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGenerateCode = (TextView) view.findViewById(R.id.generate_code);
        mTvSeconds = (TextView) view.findViewById(R.id.tv_seconds);
        mProgressBar = (ProgressBar) view.findViewById(R.id.time_progress);

        mBtnGenerate = (AppCompatButton) view.findViewById(R.id.generate_code_rescue);
        mBtnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateCode();
            }
        });

        setTypefaceInEditText();
        setupTimer();

        generateCode();
    }


    private void setTypefaceInEditText() {
        Typeface MuseoSans100 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MuseoSans_500.otf");
        mGenerateCode.setTypeface(MuseoSans100);
    }


    public void generateCode() {
        if (!timeRunning) {
            if(!setupTimer()) {
                startTimeAndGenerateCode();
            }
        }
    }

    private boolean setupTimer() {

        String rescueCodeStr = SharedPreferencesHelper.read(getActivity(), Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.KEY_TIME, null);

        if (TextUtils.isEmpty(rescueCodeStr)) {
            return false;
        } else {
            WearRescueCode wearRescueCode = new GsonBuilder().create().fromJson(rescueCodeStr, WearRescueCode.class);

            long time = System.currentTimeMillis() - wearRescueCode.getTimeCurrent();

            if (time >= 30000) {
                resetState();
                return false;
            } else {
                time = 30000 - time;

                setCode(wearRescueCode.getRescueCode());
                startTime(time);
            }
        }

        return  true;
    }

    @Override
    public void onResume() {
        super.onResume();
        setMenuSelected(R.id.menu_rescue_code);

        if(timeRunning)
            setupTimer();
    }

    private void startTimeAndGenerateCode() {
        String cpf = LiveloPontosApp.getInstance().getCpf();

        String token = RescueCodeUtil.generateToken(getContext(), cpf);
        saveTimeCurrent(System.currentTimeMillis(), token);
        setCode(token);
        startTime(30000);
    }

    private void setCode(String code) {
        if(isAlive()) mGenerateCode.setText(code);
    }

    private void startTime(long millis) {
        mBtnGenerate.setBackgroundColor(getResources().getColor(R.color.gray_e5e5e5));
        mCountDownTimer = new CountDownTimer(millis, 100) {
            int secondsLeft = 0;

            public void onTick(long millisUntilFinished) {
                timeRunning = true;

                if(isAlive()) mTvSeconds.setTextColor(getResources().getColor(R.color.blue_60c8ca));

                if (Math.round((float) millisUntilFinished / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) millisUntilFinished / 1000.0f);
                    if(isAlive()) mTvSeconds.setText(secondsLeft + "s");

                    //int progress = 30 - secondsLeft;
                    if(isAlive()) mProgressBar.setProgress(secondsLeft);
                    Log.i("TIMER", secondsLeft + " s");
                }
            }

            public void onFinish() {
                resetState();
            }
        }.start();
    }


    private void saveTimeCurrent(long timeCurrent, String token) {
        WearRescueCode rescueCode = new WearRescueCode(timeCurrent, token);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String rescueCodeStr = gson.toJson(rescueCode);

        SharedPreferencesHelper.write(getActivity(), Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.KEY_TIME, rescueCodeStr);
    }

    private void resetState(){
        timeRunning = false;

        if(isAlive()) mTvSeconds.setTextColor(getResources().getColor(R.color.gray_cccccc));
        if(isAlive()) mTvSeconds.setText("0");
        if(isAlive()) mProgressBar.setProgress(0);
        if(isAlive()) mProgressBar.setMax(30);
        if(isAlive()) mGenerateCode.setText("");
        if (isAlive())
            mBtnGenerate.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_button_enable));

        SharedPreferencesHelper.remove(getActivity(), Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.KEY_TIME);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
        }
    }

    /*@Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }*/
}
