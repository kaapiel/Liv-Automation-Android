package br.com.pontomobi.livelopontos.ui.myPoints.extract;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by selem.gomes on 03/05/16.
 */
public class CenterLockListener extends RecyclerView.OnScrollListener {

    private boolean mAutoSet = true;
    private int mCenterPivot;
    private Context context;
    private ExtractFragment.OnMonthSelectListener listener;

    public CenterLockListener(int center, Context context, ExtractFragment.OnMonthSelectListener listener) {
        this.mCenterPivot = center;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

        if (mCenterPivot == 0) {

            mCenterPivot = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (recyclerView.getLeft() + recyclerView.getRight()) : (recyclerView.getTop() + recyclerView.getBottom());
        }
        if (!mAutoSet) {

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                View view = findCenterView(lm);
                int viewCenter = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (view.getLeft() + view.getRight()) / 2 : (view.getTop() + view.getBottom()) / 2;
                int scrollNeeded = viewCenter - mCenterPivot;

                if (lm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    recyclerView.smoothScrollBy(scrollNeeded, 0);
                } else {
                    recyclerView.smoothScrollBy(0, (int) (scrollNeeded));
                }
                mAutoSet = true;
            }
        }
        View view = findCenterView(lm);
        TextView tx = (TextView) view.findViewById(R.id.txt_month);

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            tx.setTextColor(context.getResources().getColor(android.R.color.white));
            listener.onMonthSelect(tx.getText().toString());
        }

        if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
            tx.setTextColor(context.getResources().getColor(R.color.extract_tablayout_item_unselected));
            mAutoSet = false;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    private View findCenterView(LinearLayoutManager lm) {

        int minDistance = 0;
        View view = null;
        View returnView = null;
        boolean notFound = true;

        for (int i = lm.findFirstVisibleItemPosition(); i <= lm.findLastVisibleItemPosition() && notFound; i++) {

            view = lm.findViewByPosition(i);

            int center = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (view.getLeft() + view.getRight()) / 2 : (view.getTop() + view.getBottom()) / 2;
            int leastDifference = Math.abs(mCenterPivot - center);

            if (leastDifference <= minDistance || i == lm.findFirstVisibleItemPosition()) {
                minDistance = leastDifference;
                returnView = view;
            } else {
                notFound = false;

            }
        }
        return returnView;
    }
}
