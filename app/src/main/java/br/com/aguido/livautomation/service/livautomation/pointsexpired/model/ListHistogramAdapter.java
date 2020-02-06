package br.com.aguido.livautomation.service.livautomation.pointsexpired.model;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.aguido.livautomation.util.DateUtil;

/**
 * Created by rodrigo.lopes on 11/5/15.
 */
public class ListHistogramAdapter extends TypeAdapter {

    private static ArrayList<String> months = new ArrayList<>(Arrays.asList(new String[]{"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"}));

    @Override
    public void write(JsonWriter out, Object value) throws IOException { }

    @Override
    public ListHistogram read(JsonReader in) throws IOException {

        final ListHistogram histogram = new ListHistogram();
        in.beginObject();
        while (in.hasNext()) {
            Histogram histogramItem = new Histogram();

            String name = in.nextName();
            Long value = in.nextLong();
            String monthName;
            String year;

            if (name.contains("'")) {
                String[] monthYear = name.split("'");
                monthName = TextUtils.isEmpty(monthYear[0]) ? getMonth(name) : monthYear[0];
                year = TextUtils.isEmpty(monthYear[1]) ? getYear(name) : monthYear[1];

                if (year.length() < 4) {
                    year = getYearFourDigits(monthName, year);
                }

            } else {
                monthName = getMonth(name);
                year = getYear(name);
                year = getYearFourDigits(monthName, year);
            }

            histogramItem.setYear(year);
            histogramItem.setMonth(monthName);
            histogramItem.setValue((int) (long) value);
            histogram.add(histogramItem);
            //histogram.add(new Histogram().setMonth(name.substring(0, 3)).setValue(1970+Integer.valueOf(name.substring(4))));
        }
        in.endObject();

        return histogram;
    }

    private String getMonth(String name) {
        return name.substring(0, 3);
    }

    private String getYear(String name) {
        String year = name.substring(4, 6);
        return year;
    }

    public static String getYearFourDigits(String month, String year) {
        String newYear;
        String formatDate1 = "dd-MM-yy";
        String formatDate = "dd-MM-yyyy";

        int monthNumber = DateUtil.getMonthNumber(month);
        String receiverDate = String.format("%02d-%02d-%02d", 1, monthNumber, Integer.parseInt(year));
        SimpleDateFormat sdf1 = new SimpleDateFormat(formatDate1);
        sdf1.set2DigitYearStart(new GregorianCalendar(2015, 1, 1).getTime());
        SimpleDateFormat sdf2 = new SimpleDateFormat(formatDate);

        try {
            String formatYear = sdf2.format(sdf1.parse(receiverDate));
            Date data = sdf2.parse(formatYear);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data);
            newYear = Integer.toString(calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
            newYear = year;
        }
        return newYear;
    }

}
