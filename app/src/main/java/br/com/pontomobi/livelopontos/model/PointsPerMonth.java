package br.com.pontomobi.livelopontos.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.pontomobi.livelopontos.service.livelo.usertransactions.model.TransactionLineItem;

/**
 * Created by selem.gomes on 10/11/15.
 */
public class PointsPerMonth implements Serializable {
    private int month;
    private int year;
    private int points;
    private List<TransactionLineItem> transactionLineItemList = new ArrayList<TransactionLineItem>();

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<TransactionLineItem> getTransactionLineItemList() {
        return transactionLineItemList;
    }

    public void setTransactionLineItemList(List<TransactionLineItem> transactionLineItemList) {
        this.transactionLineItemList = transactionLineItemList;
    }
}
