package br.com.pontomobi.livelowear.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import com.squareup.otto.Subscribe;

import br.com.pontomobi.livelowear.Constants;
import br.com.pontomobi.livelowear.LiveloWearApp;
import br.com.pontomobi.livelowear.R;
import br.com.pontomobi.livelowear.service.SendMessageThread;
import br.com.pontomobi.livelowear.service.model.WearError;
import br.com.pontomobi.livelowear.service.model.WearLogin;
import br.com.pontomobi.livelowear.service.model.WearPoints;
import br.com.pontomobi.livelowear.ui.LiveloWearBaseActivity;
import br.com.pontomobi.livelowear.ui.error.GenericErrorActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 14/01/16.
 */
public class HomeActivity extends LiveloWearBaseActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LiveloWearHome";
    @Bind(R.id.grid_home)
    GridViewPager gridHome;
    private GoogleApiClient googleClient;
    FragmentGridPagerAdapter fragmentGridPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        //new SendMessageThread(Constants.RequestSendPhone.REQUEST_CHECK_LOGIN, googleClient).start();

        fragmentGridPagerAdapter = new HomeAdapter(getFragmentManager());
        gridHome.setAdapter(fragmentGridPagerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        googleClient.connect();
        LiveloWearApp.getInstance().getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        LiveloWearApp.getInstance().getBus().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void responseCheckLogin(WearLogin wearLogin) {
        Log.v(TAG, "Subscribe_Login");
        if (!wearLogin.isLogged()) {
            Intent intent = new Intent(this, GenericErrorActivity.class);
            intent.putExtra(GenericErrorActivity.KEY_ERROR_LOGIN, true);
            startActivity(intent);
            HomeActivity.this.finish();
        }
    }

    @Subscribe
    public void responseGetPoints(WearPoints wearPoints) {
        LiveloWearApp.getInstance().setWearPoints(wearPoints);
        Log.v(TAG, "PONTOS");

    }

    @Subscribe
    public void errorConnection(WearError wearError) {
        callActivityError();
    }

    private void callActivityError(){
        Intent intent = new Intent(this, GenericErrorActivity.class);
        intent.putExtra(GenericErrorActivity.KEY_ERROR_LOGIN, false);
        startActivity(intent);
        HomeActivity.this.finish();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v(TAG, "Connected Success");
        new SendMessageThread(Constants.RequestSendPhone.REQUEST_CHECK_LOGIN, googleClient).start();
        new SendMessageThread(Constants.RequestSendPhone.REQUEST_GET_AMOUNT_POINTS, googleClient).start();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v(TAG, "Connected Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(TAG, "Connected Failed");
    }
}
