package br.com.aguido.livautomation.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.aguido.livautomation.R;

/**
 * Created by selem.gomes on 04/09/15.
 */
public class BannerError extends RelativeLayout {

    private RelativeLayout bannerContent;
    private TextView bannerText;
    private Context context;

    public BannerError(Context context) {
        super(context);
    }

    public BannerError(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, attrs);
    }

    private void inflate(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.banner_error, this, true);

        bannerText = (TextView) findViewById(R.id.banner_error_text);
        bannerContent = (RelativeLayout) findViewById(R.id.banner_error_content);
    }

    public void showAndAnimBannerError() {
        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        bannerContent.startAnimation(slideUp);
    }

    public void hideAndAnimBannerError() {
        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        bannerContent.startAnimation(slideDown);
    }

    public RelativeLayout getBannerContent() {
        return bannerContent;
    }

    public void setBannerContent(RelativeLayout bannerContent) {
        this.bannerContent = bannerContent;
    }

    public TextView getBannerText() {
        return bannerText;
    }

    public void setBannerText(String bannerText) {
        this.bannerText.setText(bannerText);
    }
}
