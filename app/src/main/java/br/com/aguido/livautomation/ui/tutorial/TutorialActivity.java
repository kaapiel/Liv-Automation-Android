package br.com.aguido.livautomation.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import br.com.aguido.livautomation.Constants;
import br.com.aguido.livautomation.LivAutomationActivity;
import br.com.aguido.livautomation.R;
import br.com.aguido.livautomation.helper.SharedPreferencesHelper;
import br.com.aguido.livautomation.model.Tutorial;
import br.com.aguido.livautomation.ui.login.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 02/09/15.
 */
public class TutorialActivity extends LivAutomationActivity implements View.OnClickListener {

    @BindView(R.id.tutorial_view_pager)
    ViewPager tutorialViewPager;
    @BindView(R.id.tutorial_pager_indicator)
    CirclePageIndicator tutorialPagerIndicator;
    @BindView(R.id.tutorial_join_now)
    TextView tutorialJoinNow;
    @BindView(R.id.tutorial_activate_account)
    TextView tutorialActivateAccount;

    private List<Tutorial> tutorials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        initView();
        generateTutorialList();
        initViewPager();
    }

    private void initView() {
        tutorialJoinNow.setOnClickListener(this);
        tutorialActivateAccount.setOnClickListener(this);
    }

    private void initViewPager() {
        tutorialViewPager.setAdapter(new TutorialAdapter(getBaseContext(), tutorials, getSupportFragmentManager()));
        tutorialPagerIndicator.setViewPager(tutorialViewPager);
        tutorialPagerIndicator.setOnPageChangeListener(getOnPageChangeListener());
    }

    private ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
    }

    private void generateTutorialList() {
        tutorials = new ArrayList<>();

        tutorials.add(new Tutorial(getString(R.string.tutorial_firts_page_title),
                getString(R.string.tutorial_firts_page_description), null, null, getResources().getResourceEntryName(R.drawable.tut_bg_1),
                getResources().getResourceEntryName(R.drawable.tut_logo), null));

        tutorials.add(new Tutorial(getString(R.string.tutorial_second_page_title),
                getString(R.string.tutorial_second_page_description), null, null, getResources().getResourceEntryName(R.drawable.tut_bg_2),
                getResources().getResourceEntryName(R.drawable.tut_pontos), null));

        tutorials.add(new Tutorial(getString(R.string.tutorial_third_page_title),
                getString(R.string.tutorial_third_page_description), null, null, getResources().getResourceEntryName(R.drawable.tut_bg_3),
                getResources().getResourceEntryName(R.drawable.tut_extrato), null));

        tutorials.add(new Tutorial(getString(R.string.tutorial_fourth_page_title),
                getString(R.string.tutorial_fourth_page_description), null, null, getResources().getResourceEntryName(R.drawable.tut_bg_4),
                getResources().getResourceEntryName(R.drawable.tut_tudo), null));

        tutorials.add(new Tutorial(null, null, getString(R.string.tutorial_fifth_page_title),
                getString(R.string.tutorial_fifth_page_description), getResources().getResourceEntryName(R.drawable.tut_bg_5),
                null, getResources().getResourceEntryName(R.drawable.tut_comece)));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tutorial_join_now:
                SharedPreferencesHelper.write(getBaseContext(), Constants.SharedPrefsKeys.SHOW_TUTORIAL, "showTutorial", false);
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}
