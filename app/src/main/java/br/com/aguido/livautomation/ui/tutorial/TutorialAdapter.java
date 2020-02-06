package br.com.aguido.livautomation.ui.tutorial;

import android.content.Context;




import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import br.com.aguido.livautomation.model.Tutorial;

/**
 * Created by selem.gomes on 02/09/15.
 */
public class TutorialAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Tutorial> tutorials;

    public TutorialAdapter(Context context, List<Tutorial> tutorials, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.tutorials = tutorials;
    }

    @Override
    public Fragment getItem(int position) {
        return TutorialFragment.newInstance(context, tutorials.get(position));
    }

    @Override
    public int getCount() {
        return tutorials != null ? tutorials.size() : 0;
    }
}
