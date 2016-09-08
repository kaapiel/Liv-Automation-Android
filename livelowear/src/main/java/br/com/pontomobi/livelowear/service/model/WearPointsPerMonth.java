package br.com.pontomobi.livelowear.service.model;

/**
 * Created by selem.gomes on 23/01/16.
 */
public class WearPointsPerMonth {
    private int month;
    private int year;
    private int points;

    public WearPointsPerMonth() {
    }

    public WearPointsPerMonth(int month, int year, int points) {
        this.month = month;
        this.year = year;
        this.points = points;
    }

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
}
