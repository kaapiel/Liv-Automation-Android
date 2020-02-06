package br.com.aguido.livautomation.listener;

import br.com.aguido.livautomation.service.livautomation.productCatalog.subcategory.model.ChildSKU;

/**
 * Created by Diogo Bittencourt on 5/20/16.
 */
public interface OnSelectPartnerListener {

    void onPartnerSelected(ChildSKU childSKU);
}
