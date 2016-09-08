package br.com.pontomobi.livelopontos.ui.myPoints.extract;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.TransactionLineItem;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.Util;


/**
 * Created by selemafonso on 05/06/15.
 */
public class ExtractAdapter extends ArrayAdapter<TransactionLineItem> {

    private int layoutToInflate;
    private LayoutInflater inflater;
    private NumberFormat numberFormat;

    public ExtractAdapter(Context context, List<TransactionLineItem> items) {
        super(context, R.layout.item_extract, items);

        layoutToInflate = R.layout.item_extract;
        numberFormat = NumberFormat.getNumberInstance();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void initViewHolder(View convertView, ViewHolder holder) {
        holder.extractItemdateNumber = (TextView) convertView.findViewById(R.id.extract_item_date_number);
        holder.extractItemdateDesc = (TextView) convertView.findViewById(R.id.extract_item_date_desc);
        holder.extractItemStore = (TextView) convertView.findViewById(R.id.extract_item_store);
        holder.extractItemStoreType = (TextView) convertView.findViewById(R.id.extract_item_store_type);
        holder.extractItemPoints = (TextView) convertView.findViewById(R.id.extract_item_points);
        holder.extractItemPts = (TextView) convertView.findViewById(R.id.extract_item_pts);
        holder.extractItemContent = (RelativeLayout) convertView.findViewById(R.id.extract_item_content);
    }

    private void configureViewHolder(TransactionLineItem extract, final ViewHolder holder, int position) {
        String charSplit = (extract.getTransactionDate().contains("-")) ? "-" : "/";
        String[] date = extract.getTransactionDate().split(charSplit);

        holder.extractItemdateNumber.setText(date[0]);
        holder.extractItemdateDesc.setText(DateUtil.getDayOfWeek(extract.getTransactionDate()));
        holder.extractItemStore.setText(extract.getPartnerName());

        if (!TextUtils.isEmpty(extract.getTransactionType())) {
            holder.extractItemStoreType.setVisibility(View.VISIBLE);
            holder.extractItemStoreType.setText(Util.getTypeTransactionName(extract.getTransactionType()));
        } else {
            holder.extractItemStoreType.setVisibility(View.GONE);
        }

        if (extract.getPoints() > 0) {
            holder.extractItemPoints.setTextColor(getContext().getResources().getColor(R.color.extract_item_points_color1));
            holder.extractItemPoints.setText(numberFormat.format(extract.getPoints()));
        } else {
            holder.extractItemPoints.setTextColor(getContext().getResources().getColor(R.color.extract_item_points_color2));
            holder.extractItemPoints.setText(numberFormat.format(extract.getPoints()));
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(layoutToInflate, parent, false);
            convertView.setTag(holder);

            initViewHolder(convertView, holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        configureViewHolder(getItem(position), holder, position);

        return convertView;
    }

    private class ViewHolder {
        private TextView extractItemdateNumber;
        private TextView extractItemdateDesc;
        private TextView extractItemStore;
        private TextView extractItemStoreType;
        private TextView extractItemPoints;
        private TextView extractItemPts;
        private RelativeLayout extractItemContent;
    }
}
