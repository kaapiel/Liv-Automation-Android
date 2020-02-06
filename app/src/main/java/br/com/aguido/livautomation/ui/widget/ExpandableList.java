package br.com.aguido.livautomation.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by selem.gomes on 11/09/15.
 */
public class ExpandableList extends ListView {

    boolean expanded = false;

    public ExpandableList(Context context) {
        super(context);
    }

    public ExpandableList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isExpanded()) {
            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            android.view.ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

