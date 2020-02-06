package br.com.aguido.livautomation.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.aguido.livautomation.service.livautomation.usertransactions.model.TransactionLineItem;

/**
 * Created by selem.gomes on 09/11/15.
 */
public class Extract {

    private int month;
    private String amountPoints;
    private String amountPointsAccumulated;
    private String amountPointsRescued;
    private String amountPointsExpire;
    private String amountPointsDownload;
    private String amountPointsChargebacks;
    private Timestamp lastUpdate;
    private List<TransactionLineItem> transactionLineItemList = new ArrayList<TransactionLineItem>();


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getAmountPoints() {
        return amountPoints;
    }

    public void setAmountPoints(String amountPoints) {
        this.amountPoints = amountPoints;
    }

    public String getAmountPointsAccumulated() {
        return amountPointsAccumulated;
    }

    public void setAmountPointsAccumulated(String amountPointsAccumulated) {
        this.amountPointsAccumulated = amountPointsAccumulated;
    }

    public String getAmountPointsRescued() {
        return amountPointsRescued;
    }

    public void setAmountPointsRescued(String amountPointsRescued) {
        this.amountPointsRescued = amountPointsRescued;
    }

    public String getAmountPointsExpire() {
        return amountPointsExpire;
    }

    public void setAmountPointsExpire(String amountPointsExpire) {
        this.amountPointsExpire = amountPointsExpire;
    }

    public String getAmountPointsDownload() {
        return amountPointsDownload;
    }

    public void setAmountPointsDownload(String amountPointsDownload) {
        this.amountPointsDownload = amountPointsDownload;
    }

    public String getAmountPointsChargebacks() {
        return amountPointsChargebacks;
    }

    public void setAmountPointsChargebacks(String amountPointsChargebacks) {
        this.amountPointsChargebacks = amountPointsChargebacks;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<TransactionLineItem> getTransactionLineItemList() {
        return transactionLineItemList;
    }

    public void setTransactionLineItemList(List<TransactionLineItem> transactionLineItemList) {
        this.transactionLineItemList = transactionLineItemList;
    }
}
