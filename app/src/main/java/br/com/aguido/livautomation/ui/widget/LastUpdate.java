package br.com.aguido.livautomation.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.aguido.livautomation.R;

/**
 * Created by selem.gomes on 04/09/15.
 */
public class LastUpdate extends RelativeLayout {

    private TextView lastUpdateLabel;
    private TextView lastUpdateDateHour;
    private LinearLayout lastUpdateLoading;
    private RelativeLayout lastUpdateContent;
    private Context context;

    private ImageView refreshImage;

    private OnUpdateListener onUpdateListener;

    public LastUpdate(Context context) {
        super(context);
        this.context = context;
    }

    public LastUpdate(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, attrs);
    }

    private void inflate(Context context, AttributeSet attrs) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.LastUpdate);
        String label = styledAttrs.getString(R.styleable.LastUpdate_last_update_label);
        String date = styledAttrs.getString(R.styleable.LastUpdate_last_update_date);

        styledAttrs.recycle();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.last_update, this, true);

        lastUpdateLabel = (TextView) findViewById(R.id.last_update_label);
        lastUpdateDateHour = (TextView) findViewById(R.id.last_update_date_hour);
        lastUpdateContent = (RelativeLayout) findViewById(R.id.last_update_content);
        lastUpdateLoading = (LinearLayout) findViewById(R.id.last_update_loading_content);

        refreshImage = (ImageView) findViewById(R.id.refresh_data);
        refreshImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //startAnimationRefresh();
                if (onUpdateListener != null) onUpdateListener.onUpdateData();
            }
        });

        if (label != null) {
            lastUpdateLabel.setText(label);
        }

        if (date != null) {
            lastUpdateDateHour.setText(date);
        }
    }

    public TextView getLastUpdateLabel() {
        return lastUpdateLabel;
    }

    public void setLastUpdateLabel(TextView lastUpdateLabel) {
        this.lastUpdateLabel = lastUpdateLabel;
    }

    public LinearLayout getLastUpdateLoading() {
        return lastUpdateLoading;
    }

    public void setLastUpdateLoading(LinearLayout lastUpdateLoading) {
        this.lastUpdateLoading = lastUpdateLoading;
    }

    public RelativeLayout getLastUpdateContent() {
        return lastUpdateContent;
    }

    public void setLastUpdateContent(RelativeLayout lastUpdateContent) {
        this.lastUpdateContent = lastUpdateContent;
    }

    public TextView getLastUpdateDateHour() {
        return lastUpdateDateHour;
    }

    public ImageView getRefreshImage() {
        return refreshImage;
    }

    public void setLastUpdateDateHour(String lastUpdateDateHour) {
        lastUpdateLoading.setVisibility(GONE);
        lastUpdateContent.setVisibility(VISIBLE);

        this.lastUpdateDateHour.setText(lastUpdateDateHour);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);
        this.lastUpdateDateHour.startAnimation(fadeIn);

        stopAnimationRefresh();
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    public void startAnimationRefresh() {
        final AnimationSet animSet = new AnimationSet(true);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);

        final RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setRepeatCount(Animation.INFINITE);

        animSet.addAnimation(rotate);

        if (refreshImage != null)
            this.refreshImage.startAnimation(animSet);
    }

    public void stopAnimationRefresh() {
        if (refreshImage != null)
            this.refreshImage.clearAnimation();
    }

    public interface OnUpdateListener {
        void onUpdateData();
    }
}
