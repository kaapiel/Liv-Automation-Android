package br.com.pontomobi.livelowear.ui.error;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableHeaderTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.common.api.GoogleApiClient;

import br.com.pontomobi.livelowear.R;
import br.com.pontomobi.livelowear.ui.LiveloWearBaseActivity;
import br.com.pontomobi.livelowear.ui.LoadingActivity;
import br.com.pontomobi.livelowear.ui.home.HomeActivity;

/**
 * Created by selem.gomes on 18/01/16.
 */
public class GenericErrorActivity extends LiveloWearBaseActivity {
    public static final String KEY_ERROR_LOGIN = "keyErrorLogin";

    private WearableHeaderTextView errorTitle;
    private WearableHeaderTextView errorDesc;
    private RelativeLayout buttonError;
    private boolean isLoginError;

    private GoogleApiClient googleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_error);
        isLoginError = getIntent().getExtras().getBoolean(KEY_ERROR_LOGIN, false);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.generic_error_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                errorTitle = (WearableHeaderTextView) stub.findViewById(R.id.generic_error_title);
                errorDesc = (WearableHeaderTextView) stub.findViewById(R.id.generic_error_desc);
                buttonError = (RelativeLayout) stub.findViewById(R.id.generic_error_button);

                if (isLoginError) {
                    errorTitle.setText(getResources().getString(R.string.generic_error_login_title));
                    errorDesc.setText(getResources().getString(R.string.generic_error_login_desc));
                } else {
                    errorTitle.setTextSize(15);
                    errorTitle.setText(getResources().getString(R.string.generic_error_all_title));
                    errorDesc.setText(getResources().getString(R.string.generic_error_all_desc));
                }

                buttonError.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isLoginError) {
                            openAppInPhone();
                        } else {
                            finish();
                        }
                    }
                });
            }
        });
    }

    private void openAppInPhone(){
        Intent animationIntent = new Intent(GenericErrorActivity.this, ConfirmationActivity.class);
        animationIntent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION);
        startActivity(animationIntent);

        //Intent intent = new Intent(GenericErrorActivity.this, LoadingActivity.class);
        //startActivity(intent);
        finish();
    }
}
