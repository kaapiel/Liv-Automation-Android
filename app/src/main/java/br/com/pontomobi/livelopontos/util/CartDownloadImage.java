package br.com.pontomobi.livelopontos.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.cart.model.CommerceItem;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.product.ProductRequest;
import br.com.pontomobi.livelopontos.service.livelo.productCatalog.product.ProductResponse;

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
