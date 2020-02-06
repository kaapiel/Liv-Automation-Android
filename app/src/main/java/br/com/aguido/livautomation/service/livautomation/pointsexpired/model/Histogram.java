package br.com.aguido.livautomation.service.livautomation.pointsexpired.model;

import java.io.Serializable;

/**
 * Created by selemafonso on 14/11/15.
 */
public class Histogram implements Serializable {
    private String month;
    private String year;
    private long value;

    public String getMonth() {
        return month;
    }

    public Histogram setMonth(String month) {
        this.month = month;
        return this;
    }

    public long getValue() {
        return value;
    }

    public Histogram setValue(long value) {
        this.value = value;
        return this;
    }

    public String getYear() {
        return year;
    }

    public Histogram setYear(String year) {
        this.year = year;
        return this;
    }

    public String getMonthName() {
        if (getMonth().equalsIgnoreCase("Jan")) {
            return "Janeiro";
        } else if (getMonth().equalsIgnoreCase("Fev")) {
            return "Fevereiro";
        } else if (getMonth().equalsIgnoreCase("Mar")) {
            return "Março";
        } else if (getMonth().equalsIgnoreCase("Abr")) {
            return "Abril";
        } else if (getMonth().equalsIgnoreCase("Mai")) {
            return "Maio";
        } else if (getMonth().equalsIgnoreCase("Jun")) {
            return "Junho";
        } else if (getMonth().equalsIgnoreCase("Jul")) {
            return "Julho";
        } else if (getMonth().equalsIgnoreCase("Ago")) {
            return "Agosto";
        } else if (getMonth().equalsIgnoreCase("Set")) {
            return "Setembro";
        } else if (getMonth().equalsIgnoreCase("Out")) {
            return "Outubro";
        } else if (getMonth().equalsIgnoreCase("Nov")) {
            return "Novembro";
        } else if (getMonth().equalsIgnoreCase("Dez")) {
            return "Dezembro";
        } else {
            return "";
        }
    }
}
