package br.com.pontomobi.livelowear.service.model;

import java.util.List;

/**
 * Created by selem.gomes on 18/01/16.
 */
public class WearPoints {
    private String amountPoints;
    private long lastUpdateData;
    private String amountPointsAccumulated;
    private List<WearPointsPerMonth> wearPointsPerMonthList;

    public WearPoints() {
    }

    public WearPoints(String amountPoints, long lastUpdate, String amountPointsAccumulated) {
        this.amountPoints = amountPoints;
        this.lastUpdateData = lastUpdate;
        this.amountPointsAccumulated = amountPointsAccumulated;
    }

    public String getAmountPoints() {
        return amountPoints;
    }

    public void setAmountPoints(String amountPoints) {
        this.amountPoints = amountPoints;
    }

    public long getLastUpdateData() {
        return lastUpdateData;
    }

    public void setLastUpdateData(long lastUpdate) {
        this.lastUpdateData = lastUpdate;
    }

    public String getAmountPointsAccumulated() {
        return amountPointsAccumulated;
    }

    public void setAmountPointsAccumulated(String amountPointsAccumulated) {
        this.amountPointsAccumulated = amountPointsAccumulated;
    }

    public List<WearPointsPerMonth> getWearPointsPerMonthList() {
        return wearPointsPerMonthList;
    }

    public void setWearPointsPerMonthList(List<WearPointsPerMonth> wearPointsPerMonthList) {
        this.wearPointsPerMonthList = wearPointsPerMonthList;
    }
}
