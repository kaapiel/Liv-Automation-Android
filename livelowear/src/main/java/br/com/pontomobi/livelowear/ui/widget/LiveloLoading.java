package br.com.pontomobi.livelowear.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import br.com.pontomobi.livelowear.R;

/**
 * Created by vilmar.filho on 2/18/16.
 */
public class LiveloLoading extends ImageView {

    public LiveloLoading(Context context) {
        super(context);
    }

    public LiveloLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupLoading();
    }

    public LiveloLoading(Context context, AttributeSet attrs, int defStyle) {
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
