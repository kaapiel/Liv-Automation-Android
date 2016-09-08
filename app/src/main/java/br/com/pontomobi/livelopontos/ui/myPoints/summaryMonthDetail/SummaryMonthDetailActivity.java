package br.com.pontomobi.livelopontos.ui.myPoints.summaryMonthDetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.PointsPerMonth;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 24/09/15.
 */
public class SummaryMonthDetailActivity extends LiveloPontosActivity {
    public static final String KEY_MONTH_NAME = "KEY_MONTH_NAME";
    public static final String KEY_MONTH_INFO = "KEY_MONTH_INFO";


    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycle_view_summary_month_detail)
    RecyclerView recycleViewSummaryMonthDetail;

    String monthName;
    PointsPerMonth pointsPerMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_month_detail);
        monthName = getIntent().getExtras().getString(KEY_MONTH_NAME);
        pointsPerMonth = (PointsPerMonth) getIntent().getExtras().getSerializable(KEY_MONTH_INFO);

        getToolbarTitle();
        ButterKnife.bind(this);
        configureActionBar();
        initRecyclerView();
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(toolbar);
        super.configureActionBar();
        toolbarTitle.setText(monthName);
    }

    private void getToolbarTitle() {
        String[] aux = monthName.split("/");
        String mes = aux[0].substring(0, 3);
        monthName = mes + "/" + aux[1];
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewSummaryMonthDetail.setLayoutManager(layoutManager);
        recycleViewSummaryMonthDetail.setAdapter(new SummaryMonthDetailAdapter(this, pointsPerMonth.getTransactionLineItemList()));
    }
}
