package br.com.pontomobi.livelowear.ui;

import android.content.Context;
import android.support.wearable.activity.WearableActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by selem.gomes on 14/01/16.
 */
public class LiveloWearBaseActivity extends WearableActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
