package br.com.pontomobi.livelopontos.service.livelo.usertransactions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by selem.gomes on 06/11/15.
 */
public class OutputUserTransactionResponse {

    @SerializedName("@class")
    @Expose
    private String classReturn;
    @SerializedName("emptyTransactionList")
    @Expose
    private boolean emptyTransactionList;
    @SerializedName("exception")
    @Expose
    private Object exception;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("transactionLineItemList")
    @Expose
    private List<TransactionLineItem> transactionLineItemList = new ArrayList<TransactionLineItem>();


    public String getClassReturn() {
        return classReturn;
    }

    public void setClassReturn(String classReturn) {
        this.classReturn = classReturn;
    }

    public boolean isEmptyTransactionList() {
        return emptyTransactionList;
    }

    public void setEmptyTransactionList(boolean emptyTransactionList) {
        this.emptyTransactionList = emptyTransactionList;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<TransactionLineItem> getTransactionLineItemList() {
        return transactionLineItemList;
    }

    public void setTransactionLineItemList(List<TransactionLineItem> transactionLineItemList) {
        this.transactionLineItemList = transactionLineItemList;
    }
}
