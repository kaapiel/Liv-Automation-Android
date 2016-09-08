package br.com.pontomobi.livelopontos.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by selem.gomes on 04/09/15.
 */
public class ItemSummary extends RelativeLayout {

    private TextView itemSummaryTitle;
    private TextView itemSummarySubtitle;
    private TextView itemSummaryPoints;
    private Context context;

    public ItemSummary(Context context) {
        super(context);
    }

    public ItemSummary(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, attrs);
    }

    private void inflate(Context context, AttributeSet attrs) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ItemSummary);
        String title = styledAttrs.getString(R.styleable.ItemSummary_title_summary);
        String subtitle = styledAttrs.getString(R.styleable.ItemSummary_subtitle_summary);
        String points = styledAttrs.getString(R.styleable.ItemSummary_points_summary);

        styledAttrs.recycle();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_summary, this, true);

        itemSummaryTitle = (TextView) findViewById(R.id.summary_item_title);
        itemSummarySubtitle = (TextView) findViewById(R.id.summary_item_subtitle);
        itemSummaryPoints = (TextView) findViewById(R.id.summary_item_points);

        if (title != null) {
            itemSummaryTitle.setText(title);
        }

        if (subtitle != null) {
            itemSummarySubtitle.setText(subtitle);
        }

        if (points != null) {
            itemSummaryPoints.setText(points);
        }
    }

    public TextView getItemSummaryTitle() {
        return itemSummaryTitle;
    }

    public void setItemSummaryTitle(TextView itemSummaryTitle) {
        this.itemSummaryTitle = itemSummaryTitle;
    }

    public TextView getItemSummarySubtitle() {
        return itemSummarySubtitle;
    }

    public void setItemSummarySubtitle(TextView itemSummarySubtitle) {
        this.itemSummarySubtitle = itemSummarySubtitle;
    }

    public void setItemSummaryPoints(TextView itemSummaryPoints) {
        this.itemSummaryPoints = itemSummaryPoints;
    }


    public void setAmountPoints(String pointsAmount) {
        if (pointsAmount.contains("-")) {
            this.itemSummaryPoints.setTextColor(context.getResources().getColor(R.color.summary_item_pts_negative_text_color));
        } else {
            this.itemSummaryPoints.setTextColor(context.getResources().getColor(R.color.summary_item_points_color));
        }

        this.itemSummaryPoints.setText(pointsAmount);

    }

}
