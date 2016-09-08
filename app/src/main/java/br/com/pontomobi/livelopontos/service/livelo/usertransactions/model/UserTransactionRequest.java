package br.com.pontomobi.livelopontos.service.livelo.usertransactions.model;

/**
 * Created by selem.gomes on 06/11/15.
 */
public class UserTransactionRequest {
    private String authorization;
    private String dateFrom;
    private String dateTo;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
