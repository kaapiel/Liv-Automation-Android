package br.com.pontomobi.livelopontos.ui.orderHistory;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by vilmar.filho on 12/7/15.
 */
public enum EnumStatus {

    PROCESS(R.string.rescue_history_label_order_proccess, R.drawable.bg_order_process, R.color.order_status_process),
    SEND(R.string.rescue_history_filter_order_send, R.drawable.bg_order_send, R.color.order_status_send),
    FINALIZED(R.string.rescue_history_label_order_finalized, R.drawable.bg_order_finalized, R.color.order_status_finalized),
    CANCELED(R.string.rescue_history_label_order_canceled, R.drawable.bg_order_canceled, R.color.order_status_canceled);


    private int label;
    private int drawable;
    private int color;

    EnumStatus(int label, int drawable, int color){
        this.label = label;
        this.drawable = drawable;
        this.color = color;

    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
