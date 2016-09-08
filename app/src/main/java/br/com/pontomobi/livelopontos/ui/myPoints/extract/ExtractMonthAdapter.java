package br.com.pontomobi.livelopontos.ui.myPoints.extract;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.MonthsExtract;

/**
 * Created by selem.gomes on 04/05/16.
 */
public class ExtractMonthAdapter  extends RecyclerView.Adapter<ExtractMonthAdapter.ExtractMonthHolder> {
    List<MonthsExtract> monthList;
    ExtractFragment.OnClickMonthListener listener;

    public ExtractMonthAdapter(List<MonthsExtract> monthList, ExtractFragment.OnClickMonthListener listener) {
        this.monthList = monthList;
        this.listener = listener;
    }

    @Override
    public ExtractMonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_extract_month, parent, false);
        return new ExtractMonthHolder(v);
    }

    @Override
    public void onBindViewHolder(ExtractMonthHolder holder, int position) {

        holder.tvName.setText(monthList.get(position).getDateToShow());
        holder.tvName.setGravity(Gravity.CENTER);
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    public class ExtractMonthHolder extends RecyclerView.ViewHolder {

        protected TextView tvName;
        View container;

        public ExtractMonthHolder(View itemView) {
            super(itemView);
            container = itemView;
            tvName = (TextView) itemView.findViewById(R.id.txt_month);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
