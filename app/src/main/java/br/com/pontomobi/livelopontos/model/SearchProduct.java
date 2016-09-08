package br.com.pontomobi.livelopontos.model;

import java.io.Serializable;

/**
 * Created by vilmar.filho on 5/16/16.
 */
public class SearchProduct implements Serializable{

    private static final long serialVersionUID = 1L;

    private String term;
    private int totalProducts;
    private int indexPagination;

    public SearchProduct(String term, int totalProducts, int indexPagination) {
        this.term = term;
        this.totalProducts = totalProducts;
        this.indexPagination = indexPagination;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getIndexPagination() {
        return indexPagination;
    }

    public void setIndexPagination(int indexPagination) {
        this.indexPagination = indexPagination;
    }
}
