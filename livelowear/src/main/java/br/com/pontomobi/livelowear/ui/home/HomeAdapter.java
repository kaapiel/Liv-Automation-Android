package br.com.pontomobi.livelowear.ui.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.wearable.view.FragmentGridPagerAdapter;

import br.com.pontomobi.livelowear.ui.gotoapp.GoToAppFragment;
import br.com.pontomobi.livelowear.ui.graphicpoints.GraphicPointsFragment;
import br.com.pontomobi.livelowear.ui.mypointsaccumulated.MyPointsAccumulatedFragment;
import br.com.pontomobi.livelowear.ui.mypointsbalance.MyPointsBalanceFragment;
import br.com.pontomobi.livelowear.ui.validationcode.ValidationCodeFragment;

/**
 * Created by selem.gomes on 14/01/16.
 */
public class HomeAdapter extends FragmentGridPagerAdapter {

    private Fragment myPointsBalance;
    private Fragment validationCode;
    private Fragment myPointsAccumulated;
    private Fragment graphicPoints;
    private Fragment goToApp;


    public HomeAdapter(FragmentManager fm) {
        super(fm);

        myPointsBalance = new MyPointsBalanceFragment();
        validationCode = new ValidationCodeFragment();
        myPointsAccumulated = new MyPointsAccumulatedFragment();
        graphicPoints = new GraphicPointsFragment();
        goToApp = new GoToAppFragment();
    }

    @Override
    public Fragment getFragment(int i, int column) {
        switch (column) {
            case 0:
                return myPointsBalance;
            case 1:
                return validationCode;
            case 2:
                return myPointsAccumulated;
            case 3:
                return graphicPoints;
            case 4:
                return goToApp;
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 5;
    }
}
