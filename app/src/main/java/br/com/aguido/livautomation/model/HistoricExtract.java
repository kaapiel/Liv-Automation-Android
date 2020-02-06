package br.com.aguido.livautomation.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.aguido.livautomation.service.livautomation.usertransactions.model.TransactionLineItem;
import br.com.aguido.livautomation.util.DateUtil;

/**
 * Created by selem.gomes on 12/11/15.
 */
public class HistoricExtract implements Serializable {

    private String monthYear;
    private String partnerCode;
    private String partnerName;
    private int points;
    private String transactionDate;
    private String transactionType;
    private List<TransactionLineItem> transactionLineItemList = new ArrayList<TransactionLineItem>();

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
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

    public List<TransactionLineItem> getTransactionLineItemList() {
        return transactionLineItemList;
    }

    public void setTransactionLineItemList(List<TransactionLineItem> transactionLineItemList) {
        this.transactionLineItemList = transactionLineItemList;
    }

    public static List<HistoricExtract> historicExtract(List<TransactionLineItem> transactionLineItemList, String transactionType, boolean orderMostRecent) {
        List<HistoricExtract> historicExtractList = new ArrayList<HistoricExtract>();

        for(TransactionLineItem transactionLineItem : transactionLineItemList) {
            String charSplit = (transactionLineItem.getTransactionDate().contains("-")) ? "-" : "/";
            String[] date = transactionLineItem.getTransactionDate().split(charSplit);

            if(!TextUtils.isEmpty(transactionType)
                    && !transactionType.equals(transactionLineItem.getTransactionType())) {
                continue;
            }

            HistoricExtract historicExtract = new HistoricExtract();
            historicExtract.setMonthYear(DateUtil.getMonthName(Integer.parseInt(date[1])) + "/" + date[2]);
            historicExtract.setPoints(transactionLineItem.getPoints());
            historicExtract.setPartnerCode(transactionLineItem.getPartnerCode());
            historicExtract.setPartnerName(transactionLineItem.getPartnerName());
            historicExtract.setTransactionDate(transactionLineItem.getTransactionDate());
            historicExtract.setTransactionType(transactionLineItem.getTransactionType());
            historicExtractList.add(historicExtract);
        }

        if (orderMostRecent) {
            Collections.reverse(historicExtractList);
        }
        return historicExtractList;
    }

    public static List<HistoricExtract> historicExtractByPartner(List<TransactionLineItem> transactionLineItemList) {
        List<HistoricExtract> historicExtractList = new ArrayList<HistoricExtract>();

        for(int i = 0; i < transactionLineItemList.size(); i++) {

            TransactionLineItem transactionLineItem = transactionLineItemList.get(i);

            if(transactionLineItem.isChecked()) {
                continue;
            }

            historicExtractList.add(totalizeMonth(transactionLineItemList, transactionLineItem.getPartnerName(), i));
        }
        restartCheck(transactionLineItemList);


        Collections.sort(historicExtractList, new Comparator<HistoricExtract>() {
            public int compare(HistoricExtract p1, HistoricExtract p2) {
                return p1.getPartnerName().toUpperCase().compareTo(p2.getPartnerName().toUpperCase());
            }
        });

        return historicExtractList;
    }

    public static HistoricExtract totalizeMonth(List<TransactionLineItem> transactionLineItemList, String partnerName, int position) {
        HistoricExtract historicExtract = new HistoricExtract();

        for(int i = position; i < transactionLineItemList.size(); i++) {

            TransactionLineItem transactionLineItem = transactionLineItemList.get(i);
            if(!transactionLineItem.getPartnerName().equals(partnerName)) {
                continue;
            }

            if(transactionLineItem.isChecked()) {
                continue;
            }
            historicExtract.setPartnerCode(transactionLineItem.getPartnerCode());
            historicExtract.setPartnerName(transactionLineItem.getPartnerName());
            historicExtract.setPoints(historicExtract.getPoints() + transactionLineItem.getPoints());
            historicExtract.getTransactionLineItemList().add(transactionLineItem);
            transactionLineItem.setChecked(true);
        }
        return historicExtract;
    }

    public static void restartCheck(List<TransactionLineItem> transactionLineItemList) {
        for(TransactionLineItem transactionLineItem : transactionLineItemList) {
            transactionLineItem.setChecked(false);
        }
    }
}
