package br.com.pontomobi.livelopontos.ui.myPoints;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selemafonso on 19/09/15.
 */
public class MyPointsActivity extends LiveloPontosActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.toolbar_title) TextView toolbarTitle;

    @Bind(R.id.my_points_tab)
    TabLayout myPointsTab;
    @Bind(R.id.my_points_pager)
    ViewPager myPointsPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_points);

        ButterKnife.bind(this);

        customizeToolbarMyPoints();
        customizeToolbar(getString(R.string.app_name_upper));

        initTab();
        changeTabsFont();
        initViewPager();
    }

    private void initTab() {
        myPointsTab.addTab(myPointsTab.newTab().setText(getString(R.string.summary_points_title)));
        myPointsTab.addTab(myPointsTab.newTab().setText(getString(R.string.extract_points_title)));
        myPointsTab.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void changeTabsFont() {

        Typeface mTypeface = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_500.otf");

        ViewGroup vg = (ViewGroup) myPointsTab.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
                }
            }
        }
    }

    private void initViewPager() {
        final MyPointsAdapter adapter = new MyPointsAdapter(getSupportFragmentManager(), myPointsTab.getTabCount());
        myPointsPager.setAdapter(adapter);
        myPointsPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(myPointsTab));
        myPointsTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myPointsPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                myPointsPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                myPointsPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    private void customizeToolbar(String title) {
        toolbarTitle.setText(title);
    }

    private void customizeToolbarMyPoints() {
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.my_points_toolbar_bg));
        customizeToolbarWithButtonBack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            MyPointsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
