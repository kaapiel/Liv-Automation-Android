package br.com.pontomobi.livelopontos.model;

import java.util.List;

import br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model.PointsExpiredResponse;

/**
 * Created by selem.gomes on 21/09/15.
 */
public class Summary {

    private String amountPoints;
    private String amountPointsAccumulated;
    private String amountPointsExpire;
    private String amountPointsRescued;
    private PointsExpiredResponse pointsExpiredResponse;
    private List<PointsPerMonth> pointsPerMonthList;

    /**
     * This attribute was deprecated.
     *
     *  @deprecated Use {@link Summary#lastUpdateData} instead.
     *
     */
    //private Timestamp lastUpdate;

    private long lastUpdateData;



    public String getAmountPoints() {
        return amountPoints;
    }

    public void setAmountPoints(String amountPoints) {
        this.amountPoints = amountPoints;
    }

    /*public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }*/

    public long getLastUpdateData() {
        return lastUpdateData;
    }

    public void setLastUpdateData(long lastUpdateData) {
        this.lastUpdateData = lastUpdateData;
    }

    public String getAmountPointsAccumulated() {
        return amountPointsAccumulated;
    }

    public void setAmountPointsAccumulated(String amountPointsAccumulated) {
        this.amountPointsAccumulated = amountPointsAccumulated;
    }

    public String getAmountPointsExpire() {
        return amountPointsExpire;
    }

    public void setAmountPointsExpire(String amountPointsExpire) {
        this.amountPointsExpire = amountPointsExpire;
    }

    public String getAmountPointsRescued() {
        return amountPointsRescued;
    }

    public void setAmountPointsRescued(String amountPointsRescued) {
        this.amountPointsRescued = amountPointsRescued;
    }

    public PointsExpiredResponse getPointsExpiredResponse() {
        return pointsExpiredResponse;
    }

    public void setPointsExpiredResponse(PointsExpiredResponse pointsExpiredResponse) {
        this.pointsExpiredResponse = pointsExpiredResponse;
    }

    public List<PointsPerMonth> getPointsPerMonthList() {
        return pointsPerMonthList;
    }

    public void setPointsPerMonthList(List<PointsPerMonth> pointsPerMonthList) {
        this.pointsPerMonthList = pointsPerMonthList;
    }
}
