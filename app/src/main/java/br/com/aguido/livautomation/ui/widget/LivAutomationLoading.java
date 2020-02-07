package br.com.aguido.livautomation.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import br.com.aguido.livautomation.R;

/**
 * Created by vilmar.filho on 1/5/16.
 */
public class LivAutomationLoading extends AppCompatImageView {

    public LivAutomationLoading(Context context) {
        super(context);
    }

    public LivAutomationLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupLoading();
    }

    public LivAutomationLoading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupLoading();
    }

    private void setupLoading(){
        setBackgroundResource(R.drawable.loading_animated);

        post(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
                frameAnimation.start();
            }
        });
    }
}
