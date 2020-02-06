package br.com.aguido.livautomation.util;

import android.content.Context;

import java.util.List;

import br.com.aguido.livautomation.service.livautomation.cart.model.CommerceItem;

/**
 * Created by vilmar.filho on 5/31/16.
 */
public class CartDownloadImage {

    private Context context;
    private List<CommerceItem> commerceItems;
    private OnCartDownload listener;

    public CartDownloadImage(Context context, OnCartDownload listener) {
        this.context = context;
        this.listener = listener;
    }

    public interface OnCartDownload {
        void processFinished();
    }

}
