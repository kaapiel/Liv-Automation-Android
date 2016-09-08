package br.com.pontomobi.livelopontos.ui.myPoints;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.pontomobi.livelopontos.ui.myPoints.extract.ExtractFragment;
import br.com.pontomobi.livelopontos.ui.myPoints.summary.SummaryFragment;

/**
 * Created by selemafonso on 19/09/15.
 */
public class MyPointsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MyPointsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SummaryFragment summaryFragment = new SummaryFragment();
                return summaryFragment;
            case 1:
                ExtractFragment extractFragment = new ExtractFragment();
                return extractFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
