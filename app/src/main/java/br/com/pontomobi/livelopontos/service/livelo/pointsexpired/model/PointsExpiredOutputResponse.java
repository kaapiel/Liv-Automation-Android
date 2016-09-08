package br.com.pontomobi.livelopontos.service.livelo.pointsexpired.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by selemafonso on 14/11/15.
 */
public class PointsExpiredOutputResponse {
    @SerializedName("output")
    private PointsExpiredResponse pointsExpiredResponse;

    public PointsExpiredResponse getPointsExpiredResponse() {
        return pointsExpiredResponse;
    }

    public void setPointsExpiredResponse(PointsExpiredResponse pointsExpiredResponse) {
        this.pointsExpiredResponse = pointsExpiredResponse;
    }
}
