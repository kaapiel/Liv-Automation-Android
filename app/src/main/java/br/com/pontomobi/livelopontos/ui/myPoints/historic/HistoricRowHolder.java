package br.com.pontomobi.livelopontos.ui.myPoints.historic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by selemafonso on 05/06/15.
 */
public class HistoricRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private TextView historicItemdateNumber;
    private TextView historicItemdateDesc;
    private TextView historicItemStore;
    private TextView historicItemType;
    private TextView historicItemPoints;
    private TextView historicMonthYear;
    private ImageView historicArrow;
    private RelativeLayout historicItemContent;
    private RelativeLayout historicDateContent;
    private HistoricActivity.OnHistoricClickListener onHistoricClickListener;


    public HistoricRowHolder(Context context, View view, HistoricActivity.OnHistoricClickListener onHistoricClickListener) {
        super(view);
        this.context = context;
        this.historicItemdateNumber = (TextView) view.findViewById(R.id.item_historic_date_number);
        this.historicItemdateDesc = (TextView) view.findViewById(R.id.item_historic_date_desc);
        this.historicItemStore = (TextView) view.findViewById(R.id.item_historic_store);
        this.historicItemType = (TextView) view.findViewById(R.id.item_historic_type);
        this.historicItemPoints = (TextView) view.findViewById(R.id.item_historic_points);
        this.historicItemContent = (RelativeLayout) view.findViewById(R.id.item_historic_content);
        this.historicMonthYear = (TextView) view.findViewById(R.id.item_historic_monthYear);
        this.historicArrow = (ImageView) view.findViewById(R.id.item_historic_arrow);
        this.historicDateContent = (RelativeLayout) view.findViewById(R.id.item_historic_date_content);

        this.onHistoricClickListener = onHistoricClickListener;
        this.historicItemContent.setOnClickListener(this);
    }

    public TextView getHistoricItemdateNumber() {
        return historicItemdateNumber;
    }

    public TextView getHistoricItemdateDesc() {
        return historicItemdateDesc;
    }

    public TextView getHistoricItemStore() {
        return historicItemStore;
    }

    public TextView getHistoricItemType() {
        return historicItemType;
    }

    public TextView getHistoricItemPoints() {
        return historicItemPoints;
    }

    public RelativeLayout getHistoricItemContent() {
        return historicItemContent;
    }

    public TextView getHistoricMonthYear() {
        return historicMonthYear;
    }

    public ImageView getHistoricArrow() {
        return historicArrow;
    }

    public RelativeLayout getHistoricDateContent() {
        return historicDateContent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_historic_content:
                onHistoricClickListener.onItemClick(getAdapterPosition());
                break;
        }
    }
}
