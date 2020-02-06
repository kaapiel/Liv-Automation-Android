package br.com.aguido.livautomation.service.livautomation.usertransactions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.com.aguido.livautomation.model.PointsPerMonth;
import br.com.aguido.livautomation.util.DateUtil;

/**
 * Created by selem.gomes on 06/11/15.
 */
public class UserTransactionsResponse {
    public final static String TRANSACTION_TYPE_ACCUMULATED = "accumulations";
    public final static String TRANSACTION_TYPE_RESCUED = "redemptions";
    public final static String TRANSACTION_TYPE_CHARGEBACKS = "chargebacks";
    public final static String TRANSACTION_TYPE_DOWNLOADS = "downloads";
    public final static String TRANSACTION_TYPE_EXPIRE = "pontosaExpirar";


    public final static String TRANSACTION_TYPE_ACCUMULATED_NAME = "ACÚMULO";
    public final static String TRANSACTION_TYPE_RESCUED_NAME = "RESGATE";
    public final static String TRANSACTION_TYPE_CHARGEBACKS_NAME = "ESTORNO";
    public final static String TRANSACTION_TYPE_EXPIRE_NAME = "EXPIRADO";
    public final static String TRANSACTION_TYPE_DOWNLOADS_NAME = "TRANSFERÊNCIA";

    @SerializedName("output")
    @Expose
    private OutputUserTransactionResponse output;
    private int amountPointsAccumulated;
    private int amountPointsRescued;
    private int amountPointsDownload;
    private int amountPointsChargebacks;
    private int amountPoints = 0;


    public OutputUserTransactionResponse getOutput() {
        return output;
    }

    public void setOutput(OutputUserTransactionResponse output) {
        this.output = output;
    }

    public int getAmountPointsAccumulated() {
        return amountPointsAccumulated;
    }

    public void setAmountPointsAccumulated(int amountPointsAccumulated) {
        this.amountPointsAccumulated = amountPointsAccumulated;
    }

    public int getAmountPointsRescued() {
        return amountPointsRescued;
    }

    public void setAmountPointsRescued(int amountPointsRescued) {
        this.amountPointsRescued = amountPointsRescued;
    }

    public int getAmountPointsDownload() {
        return amountPointsDownload;
    }

    public void setAmountPointsDownload(int amountPointsDownload) {
        this.amountPointsDownload = amountPointsDownload;
    }

    public int getAmountPointsChargebacks() {
        return amountPointsChargebacks;
    }

    public void setAmountPointsChargebacks(int amountPointsChargebacks) {
        this.amountPointsChargebacks = amountPointsChargebacks;
    }

    public int getAmountPoints() {
        return amountPoints;
    }

    public void setAmountPoints(int amountPoints) {
        this.amountPoints = amountPoints;
    }

    public void totalizePoints() {

        if (output == null
                || output.getTransactionLineItemList() == null) {
            return;
        }

        int accumulated = 0;
        int rescued = 0;
        int download = 0;
        int chargebacks = 0;
        int amountPoints = 0;

        for (TransactionLineItem transactionLineItem : output.getTransactionLineItemList()) {

            if (DateUtil.checkIfDateHasMoreThanDaysAgo(DateUtil.getDateToService(transactionLineItem.getTransactionDate()), 30)) {
                continue;
            }

            if (transactionLineItem.getTransactionType().equals(TRANSACTION_TYPE_ACCUMULATED)) {
                accumulated += transactionLineItem.getPoints();
            }

            if (transactionLineItem.getTransactionType().equals(TRANSACTION_TYPE_RESCUED)) {
                rescued += transactionLineItem.getPoints();
            }

            if (transactionLineItem.getTransactionType().equals(TRANSACTION_TYPE_DOWNLOADS)) {
                download += transactionLineItem.getPoints();
            }

            if (transactionLineItem.getTransactionType().equals(TRANSACTION_TYPE_CHARGEBACKS)) {
                chargebacks += transactionLineItem.getPoints();
            }

            amountPoints += transactionLineItem.getPoints();
        }

        setAmountPointsAccumulated(accumulated);
        setAmountPointsRescued(rescued);
        setAmountPointsChargebacks(chargebacks);
        setAmountPointsDownload(download);
        setAmountPoints(amountPoints);
    }

    public void totalizePointsExtract() {
        int accumulated = 0;
        int rescued = 0;
        int download = 0;
        int chargebacks = 0;
        int amountPoints = 0;

        for (TransactionLineItem transactionLineItem : output.getTransactionLineItemList()) {

            if (transactionLineItem.getTransactionType().equals(TRANSACTION_TYPE_ACCUMULATED)) {
                accumulated += transactionLineItem.getPoints();
            }

            if (transactionLineItem.getTransactionType().equals(TRANSACTION_TYPE_RESCUED)) {
                rescued += transactionLineItem.getPoints();
            }

            if (transactionLineItem.getTransactionType().equals(TRANSACTION_TYPE_DOWNLOADS)) {
                download += transactionLineItem.getPoints();
            }

            if (transactionLineItem.getTransactionType().equals(TRANSACTION_TYPE_CHARGEBACKS)) {
                chargebacks += transactionLineItem.getPoints();
            }

            amountPoints += transactionLineItem.getPoints();
        }

        setAmountPointsAccumulated(accumulated);
        setAmountPointsRescued(rescued);
        setAmountPointsChargebacks(chargebacks);
        setAmountPointsDownload(download);
        setAmountPoints(amountPoints);
    }

    public List<PointsPerMonth> totalizePointsPerMonth(String transactionType, Date dateInitial, Date dateFinish) {
        List<PointsPerMonth> pointsPerMonthList = new ArrayList<PointsPerMonth>();

        if (output == null
                || output.getTransactionLineItemList() == null) {
            return pointsPerMonthList;
        }

        for (int i = 0; i < output.getTransactionLineItemList().size(); i++) {
            TransactionLineItem transactionLineItem = output.getTransactionLineItemList().get(i);

            if (!transactionLineItem.getTransactionType().equalsIgnoreCase(transactionType)) {
                continue;
            }

            if (!DateUtil.dateBetween(dateInitial, dateFinish, transactionLineItem.getTransactionDate())) {
                continue;
            }

            if (transactionLineItem.isChecked()) {
                continue;
            }

            String charSplit = (transactionLineItem.getTransactionDate().contains("-")) ? "-" : "/";
            String[] dateSplit = transactionLineItem.getTransactionDate().split(charSplit);
            PointsPerMonth pointsPerMonth = new PointsPerMonth();
            pointsPerMonth.setMonth(Integer.parseInt(dateSplit[1]));
            pointsPerMonth.setYear(Integer.parseInt(dateSplit[2]));
            totalizeMonth(pointsPerMonth, transactionType, dateInitial, dateFinish, i);
            pointsPerMonthList.add(pointsPerMonth);

        }

        restartChecek();
        return pointsPerMonthList;
    }

    private void totalizeMonth(PointsPerMonth pointsPerMonth, String transactionType, Date dateInitial,
                               Date dateFinish, int position) {

        if (output == null
                || output.getTransactionLineItemList() == null) {
            return;
        }


        for (int i = position; i < output.getTransactionLineItemList().size(); i++) {
            TransactionLineItem transactionLineItem = output.getTransactionLineItemList().get(i);

            if (transactionLineItem.isChecked()) {
                continue;
            }

            if (!transactionLineItem.getTransactionType().equalsIgnoreCase(transactionType)) {
                continue;
            }

            String charSplit = (transactionLineItem.getTransactionDate().contains("-")) ? "-" : "/";
            String[] dateSplit = transactionLineItem.getTransactionDate().split(charSplit);
            if (Integer.parseInt(dateSplit[1]) != pointsPerMonth.getMonth() || Integer.parseInt(dateSplit[2]) != pointsPerMonth.getYear()) {
                continue;
            }

            if (!DateUtil.dateBetween(dateInitial, dateFinish, transactionLineItem.getTransactionDate())) {
                continue;
            }

            pointsPerMonth.setPoints(pointsPerMonth.getPoints() + transactionLineItem.getPoints());
            pointsPerMonth.getTransactionLineItemList().add(transactionLineItem);

            transactionLineItem.setChecked(true);
        }
    }

    private void restartChecek() {
        for (int i = 0; i < output.getTransactionLineItemList().size(); i++) {
            output.getTransactionLineItemList().get(i).setChecked(false);
        }
    }

    public void orderListByDate() {
        if (getOutput() == null || getOutput().getTransactionLineItemList() == null) return;

        List<TransactionLineItem> transactionLineItems = getOutput().getTransactionLineItemList();

        Collections.sort(transactionLineItems, new Comparator<TransactionLineItem>() {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

            public int compare(TransactionLineItem p1, TransactionLineItem p2) {
                try {
                    return f.parse(p1.getTransactionDate()).compareTo(f.parse(p2.getTransactionDate()));
                } catch (ParseException e) {
                    return 0;
                }
            }
        });
    }
}
