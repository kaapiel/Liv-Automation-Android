package br.com.aguido.livautomation.service.livautomation.pointsexpired.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by rodrigo.lopes on 11/5/15.
 */
public class HistogramMapAdapter extends TypeAdapter {

    private static ArrayList<String> months = new ArrayList<String>(Arrays.asList(new String[]{"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"}));
    ;

    @Override
    public void write(JsonWriter out, Object value) throws IOException {

    }

    @Override
    public TreeMap read(JsonReader in) throws IOException {

        final HistogramViewMap histogram = new HistogramViewMap();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            Long value = in.nextLong();

            int mes = months.indexOf(name.substring(0, 3));
            int year = Integer.valueOf(name.substring(4));
            histogram.put(new Date(1970 + year, mes, 1).getTime(), value);

        }
        in.endObject();

        return histogram;
    }

}
