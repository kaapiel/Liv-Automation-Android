package br.com.pontomobi.livelowear.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by selem.gomes on 22/01/16.
 */
public class DateUtil {

    public static String getLastUpdate(long timestamp) {
        if (timestamp < 0) {
            return ("Atualizado em ...");
        }

        Date date = new Date(timestamp);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy '\nàs' HH:mm");
        return ("Atualizado em " + df.format(date));
    }

    public static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "JANEIRO";
            case 2:
                return "FEVEREIRO";
            case 3:
                return "MARÇO";
            case 4:
                return "ABRIL";
            case 5:
                return "MAIO";
            case 6:
                return "JUNHO";
            case 7:
                return "JULHO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SETEMBRO";
            case 10:
                return "OUTUBRO";
            case 11:
                return "NOVEMBRO";
            case 12:
                return "DEZEMBRO";

            default:
                return "";

        }
    }
}
