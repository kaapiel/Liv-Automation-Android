package br.com.pontomobi.livelowear.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.pontomobi.livelowear.R;

/**
 * Created by selem.gomes on 19/01/16.
 */
public class GraphicBar extends LinearLayout {
    private Context context;
    private TextView graphicBarItemMonth;
    private TextView graphicBarItemAmount;
    private View graphicBarItemBar;
    private long points;

    public GraphicBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        inflate(context, attrs);
    }

    private void inflate(Context context, AttributeSet attrs) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.GraphicBar);
        styledAttrs.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.graphic_item, this, true);

        graphicBarItemMonth = (TextView) findViewById(R.id.graphic_item_month);
        graphicBarItemAmount = (TextView) findViewById(R.id.graphic_item_amount);
        graphicBarItemBar = findViewById(R.id.graphic_item_bar);
    }

    public void width(double heightMax) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) graphicBarItemBar.getLayoutParams();
        params.width = calculaWidth(heightMax);
        graphicBarItemBar.setLayoutParams(params);
        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_left);
        graphicBarItemBar.startAnimation(slideUp);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                graphicBarItemMonth.setVisibility(VISIBLE);
                graphicBarItemAmount.setVisibility(VISIBLE);
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(1000);
                graphicBarItemMonth.startAnimation(fadeIn);
                graphicBarItemAmount.startAnimation(fadeIn);
            }
        }, 1000);
    }

    public TextView getGraphicBarItemMonth() {
        return graphicBarItemMonth;
    }

    public void setGraphicBarItemMonth(TextView graphicBarItemMonth) {
        this.graphicBarItemMonth = graphicBarItemMonth;
    }

    public TextView getGraphicBarItemAmount() {
        return graphicBarItemAmount;
    }

    public void setGraphicBarItemAmount(TextView graphicBarItemAmount) {
        this.graphicBarItemAmount = graphicBarItemAmount;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    private int calculaWidth(double heightMax) {
        int height = 0;
        height = (int) Math.round((150 * getPoints()) / heightMax);
        return height;
    }

}
