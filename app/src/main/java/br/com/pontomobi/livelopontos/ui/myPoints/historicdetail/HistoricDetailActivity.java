package br.com.pontomobi.livelopontos.ui.myPoints.historicdetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.HistoricExtract;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 12/11/15.
 */
public class HistoricDetailActivity extends LiveloPontosActivity {
    public static final String KEY_HISTORIC_EXTRACT_ITEM = "keyHistoricExtractItem";

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycle_view_historic_detail)
    RecyclerView recycleViewHistoricDetail;

    HistoricExtract historicExtract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_detail);
        historicExtract = (HistoricExtract) getIntent().getExtras().getSerializable(KEY_HISTORIC_EXTRACT_ITEM);

        ButterKnife.bind(this);
        configureActionBar();
        initRecyclerView();
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(toolbar);
        super.configureActionBar();
        toolbarTitle.setText(historicExtract.getPartnerName());
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
        recycleViewHistoricDetail.setLayoutManager(layoutManager);
        recycleViewHistoricDetail.setAdapter(new HistoricDetailAdapter(this, historicExtract.getTransactionLineItemList()));
    }
}
