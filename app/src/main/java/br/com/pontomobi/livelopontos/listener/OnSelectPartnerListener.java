package br.com.pontomobi.livelopontos.listener;

import br.com.pontomobi.livelopontos.service.livelo.productCatalog.subcategory.model.ChildSKU;

/**
 * Created by Diogo Bittencourt on 5/20/16.
 */
public interface OnSelectPartnerListener {

    void onPartnerSelected(ChildSKU childSKU);
}
