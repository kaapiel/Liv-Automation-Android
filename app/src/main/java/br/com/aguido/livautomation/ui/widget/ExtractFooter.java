package br.com.aguido.livautomation.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.aguido.livautomation.R;

/**
 * Created by selem.gomes on 04/09/15.
 */
public class ExtractFooter extends RelativeLayout {

    private TextView extractFooterTitle;
    private TextView extractFooterPoints;
    private Context context;

    public ExtractFooter(Context context) {
        super(context);
    }

    public ExtractFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, attrs);
    }

    private void inflate(Context context, AttributeSet attrs) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ExtractFooter);
        String title = styledAttrs.getString(R.styleable.ExtractFooter_title_footer);
        String points = styledAttrs.getString(R.styleable.ExtractFooter_points_footer);

        styledAttrs.recycle();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_extract_footer, this, true);

        extractFooterTitle = (TextView) findViewById(R.id.view_extract_footer_title);
        extractFooterPoints = (TextView) findViewById(R.id.view_extract_footer_points);

        if (title != null) {
            extractFooterTitle.setText(title);
        }

        if (points != null) {
            extractFooterPoints.setText(points);
        }
    }

    public TextView getExtractFooterTitle() {
        return extractFooterTitle;
    }

    public void setAmountPoints(String pointsAmount) {
        if (pointsAmount.contains("-")) {
            this.extractFooterPoints.setTextColor(context.getResources().getColor(R.color.extract_footer_points_negative_text_color));
            this.extractFooterPoints.setText(pointsAmount);
        } else {
            this.extractFooterPoints.setTextColor(context.getResources().getColor(R.color.extract_footer_points_positive_text_color));
            this.extractFooterPoints.setText(pointsAmount.equalsIgnoreCase("0") ? pointsAmount : pointsAmount);
        }


    }
}
