package br.com.aguido.livautomation.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by selem.gomes on 09/11/15.
 */
public class DateUtil {

    public static int getCurrentMonth() {
        java.util.Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return (month);
    }

    public static String formatDateForService(Date date) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return (df.format(date));
    }

    public static Date getDateBefore30Days(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        return (removeTime(calendar.getTime()));
    }

    public static Date getDateBeforeMonths(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, -month);
        return (cal.getTime());
    }

    public static Date getDateAfterMonths(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, month);
        return (cal.getTime());
    }

    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return (cal.getTime());
    }

    /**
     * Returns a object timestamp.
     *
     * @return A Timestamp object.
     * @deprecated Use {@link #getCurrentDateInMillis()} instead.
     */
    public static Timestamp getCurrentDateInTimestamp() {
        return (new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Returns the current date in millis.
     *
     * @return A Timestamp in millis.
     */
    public static long getCurrentDateInMillis() {
        return (System.currentTimeMillis());
    }

    /**
     * Returns a string formatted with date to show.
     *
     * @param timestamp A object Timestamp java.
     * @return string formatted with date to show.
     * @deprecated Use {@link #getFormatDateToShow(long)} instead.
     */
    public static String getFormatDateToShow(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
        return (df.format(date));
    }

    /**
     * Returns a string formatted with date to show.
     *
     * @param timestamp A date timestamp in long.
     * @return string formatted with date to show.
     */
    public static String getFormatDateToShow(long timestamp) {
        Date date = new Date(timestamp);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
        return (df.format(date));
    }

    public static String getFormatDateToShowJustDate(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return (df.format(date));
    }


    public static String getFormatDateToShow(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
        return (df.format(date));
    }

    public static String getFirstDateInMonth(int currentMonth, int year) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.MONTH, currentMonth);
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.YEAR, year);
        Date firstDateOfPreviousMonth = aCalendar.getTime();

        return (formatDateForService(firstDateOfPreviousMonth));
    }

    public static Date getFirstDateInMonthReturnDate(int currentMonth, int year) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.MONTH, currentMonth);
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.YEAR, year);
        Date firstDateOfPreviousMonth = aCalendar.getTime();

        return firstDateOfPreviousMonth;
    }

    public static String getLastDateInMonth(int currentMonth, int year) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.MONTH, currentMonth);
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        aCalendar.set(Calendar.YEAR, year);
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        return (formatDateForService(lastDateOfPreviousMonth));
    }

    public static Date getLastDateInMonth(Date date) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(date);
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        return (lastDateOfPreviousMonth);
    }

    public static Date getDateInMonthAndYear(int month, int year) {
        if (month > 0) {
           month = month - 1;
        }

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.MONTH, month);
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.YEAR, year);
        Date date = aCalendar.getTime();

        return (date);
    }

    public static String getDayOfWeek(String date) {
        String charSplit = (date.contains("-")) ? "-" : "/";
        String[] dateSplit = date.split(charSplit);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]) - 1, Integer.parseInt(dateSplit[0]));
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (getDayName(dayOfWeek));
    }

    public static String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return "DOM";
            case 2:
                return "SEG";
            case 3:
                return "TER";
            case 4:
                return "QUA";
            case 5:
                return "QUI";
            case 6:
                return "SEX";
            case 7:
                return "SAB";


            default:
                return "";

        }
    }

    public static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEV";
            case 3:
                return "MAR";
            case 4:
                return "ABR";
            case 5:
                return "MAI";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AGO";
            case 9:
                return "SET";
            case 10:
                return "OUT";
            case 11:
                return "NOV";
            case 12:
                return "DEZ";

            default:
                return "";

        }
    }

    public static int getMonthNumber(String month) {
        if (month.equalsIgnoreCase("JAN")) {
            return 1;
        } else if (month.equalsIgnoreCase("FEV")) {
            return 2;
        } else if (month.equalsIgnoreCase("MAR")) {
            return 3;
        } else if (month.equalsIgnoreCase("ABR")) {
            return 4;
        } else if (month.equalsIgnoreCase("MAI")) {
            return 5;
        } else if (month.equalsIgnoreCase("JUN")) {
            return 6;
        } else if (month.equalsIgnoreCase("JUL")) {
            return 7;
        } else if (month.equalsIgnoreCase("AGO")) {
            return 8;
        } else if (month.equalsIgnoreCase("SET")) {
            return 9;
        } else if (month.equalsIgnoreCase("OUT")) {
            return 10;
        } else if (month.equalsIgnoreCase("NOV")) {
            return 11;
        } else if (month.equalsIgnoreCase("DEZ")) {
            return 12;
        } else {
            return 0;
        }

    }

    public static boolean dateBetween(Date dateFrom, Date dateTo, String dateCompare) {
        Date date = getDateToService(dateCompare);
        date = removeTime(date);
        dateFrom = removeTime(dateFrom);
        dateTo = removeTime(dateTo);

        if (date.compareTo(dateFrom) < 0) {
            return false;
        } else if (date.compareTo(dateTo) > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static Date getDateToService(String date) {
        String charSplit = (date.contains("-")) ? "-" : "/";
        String[] dateSplit = date.split(charSplit);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]) - 1, Integer.parseInt(dateSplit[0]));
        return (calendar.getTime());
    }

    public static boolean checkIfDateHasMoreThanDaysAgo(Date date, int days) {
        int differenceDays = getDifferenceDays(date, new Date());

        if (differenceDays > days) {
            return true;
        }

        return false;
    }

    public static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();

        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

}
