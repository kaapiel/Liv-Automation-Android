package br.com.pontomobi.livelopontos.ui.myPoints.historicdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by selemafonso on 05/06/15.
 */
public class HistoricDetailRowHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView historicDetailItemDateNumber;
    private TextView historicDetailItemDateDesc;
    private TextView historicDetailItemStore;
    private TextView historicDetailItemType;
    private TextView historicDetailItemPoints;


    public HistoricDetailRowHolder(Context context, View view) {
        super(view);
        this.context = context;
        this.historicDetailItemDateNumber = (TextView) view.findViewById(R.id.historic_detail_item_date_number);
        this.historicDetailItemDateDesc = (TextView) view.findViewById(R.id.historic_detail_item_date_desc);
        this.historicDetailItemStore = (TextView) view.findViewById(R.id.historic_detail_item_store);
        this.historicDetailItemType = (TextView) view.findViewById(R.id.historic_detail_item_store_type);
        this.historicDetailItemPoints = (TextView) view.findViewById(R.id.historic_detail_item_points);
    }

    public TextView getHistoricDetailItemDateNumber() {
        return historicDetailItemDateNumber;
    }

    public TextView getHistoricDetailItemDateDesc() {
        return historicDetailItemDateDesc;
    }

    public TextView getHistoricDetailItemStore() {
        return historicDetailItemStore;
    }

    public TextView getHistoricDetailItemType() {
        return historicDetailItemType;
    }

    public TextView getHistoricDetailItemPoints() {
        return historicDetailItemPoints;
    }
}
