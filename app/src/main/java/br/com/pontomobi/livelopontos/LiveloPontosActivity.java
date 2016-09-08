package br.com.pontomobi.livelopontos;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by selem.gomes on 02/09/15.
 */
public class LiveloPontosActivity extends AppCompatActivity {

    private String TAG = "ACTIVITY_CONTROL";

    protected Typeface tf;
    protected ActionBar mActionBar;

    private static int mForegroundActivities = 0;
    private boolean inBackground = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void configureActionBar() {
        mActionBar = getSupportActionBar();

        if (mActionBar == null) {
            return;
        }

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("");
        mActionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_voltar));
    }


    public void customizeToolbarWithButtonBack() {
        if (getSupportActionBar() == null) return;

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mForegroundActivities++;

        if (mForegroundActivities == 1) {
            Log.i(TAG, "in foreground");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        inBackground = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        inBackground = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        inBackground = true;
        mForegroundActivities--;

        if (mForegroundActivities == 0) {
            Log.i(TAG, "in background");
        }
    }

    public static boolean isAppInForeground() {
        if (mForegroundActivities == 1) {
            return true;
        }

        return false;
    }

    public boolean isAlive() {
        return !inBackground && !isFinishing();
    }

    public int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    protected BarData generateBarData(int dataSets, float range, int count) {

        tf = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_500.otf");
        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();

        for(int i = 0; i < dataSets; i++) {

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

//            entries = FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "stacked_bars.txt");

            for(int j = 0; j < count; j++) {
                entries.add(new BarEntry(j, (float) (Math.random() * range) + range / 4));
            }

            BarDataSet ds = new BarDataSet(entries, getLabel(i));
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }

        BarData d = new BarData(sets);
        d.setValueTypeface(tf);
        return d;
    }

    private String[] mLabels = new String[] { "Company A", "Company B", "Company C", "Company D", "Company E", "Company F" };

    private String getLabel(int i) {
        return mLabels[i];
    }

}
