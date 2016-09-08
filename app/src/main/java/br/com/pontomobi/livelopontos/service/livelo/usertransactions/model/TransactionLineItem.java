package br.com.pontomobi.livelopontos.service.livelo.usertransactions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by selem.gomes on 06/11/15.
 */
public class TransactionLineItem implements Serializable {

    @SerializedName("@class")
    @Expose
    private String classReturn;

    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;

    @SerializedName("partnerCode")
    @Expose
    private String partnerCode;

    @SerializedName("partnerName")
    @Expose
    private String partnerName;

    @SerializedName("points")
    @Expose
    private int points;

    @SerializedName("reasonComment")
    @Expose
    private Object reasonComment;

    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;

    @SerializedName("transactionType")
    @Expose
    private String transactionType;

    @SerializedName("transactionTypeCode")
    @Expose
    private String transactionTypeCode;

    private boolean checked = false;


    public String getClassReturn() {
        return classReturn;
    }

    public void setClassReturn(String classReturn) {
        this.classReturn = classReturn;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Object getReasonComment() {
        return reasonComment;
    }

    public void setReasonComment(Object reasonComment) {
        this.reasonComment = reasonComment;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
