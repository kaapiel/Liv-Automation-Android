package br.com.aguido.livautomation.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by selem.gomes on 30/10/15.
 */
public class StringUtil {

    public static String removeCaractersCPF(String cpf) {

        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        return cpf;
    }

    public static String formatCash(float value){
        DecimalFormat decimalFormat = new DecimalFormat("R$ ##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return decimalFormat.format(value);
    }

    public static String formatPoints(long points) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String text = (points <= 1) ? "pt" : "pts";
        return numberFormat.format(points) + text;
    }

    public static String nullSafeReplaceAll(String str, String match, String replacement) {
        return str == null || replacement == null ? "" : str.replaceAll(match, replacement);
    }

    public static String removeAccents(String s) {
        //Remove acentos da string
        String tempString = Normalizer.normalize(s, Normalizer.Form.NFD);
        return tempString.replaceAll("[^\\p{ASCII}]", "");
    }

    public static String formatNumber(int number) {
        return String.format("%02d", number);
    }
}
