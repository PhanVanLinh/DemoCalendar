package com.example.nguyenminhduong.democalendar.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by framgia on 19/05/2017.
 */
public final class DateTimeUtils {

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy/MM/dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_EEE_D_MMM__YYYY = "EEE, d MMM yyyy";
    public static final String DATE_FORMAT_YYYY_MM_JAPANESE = "MM/yyyy";
    public static final String DATE_FORMAT_MMMM = "MMMM";
    public static final String FORMAT_DATE = "dd/MM/yyyy";
    public static final String TIME_FORMAT_HH_MM = "HH:mm";
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM = "yyyy/MM/dd - HH:mm";
    public static final String DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY = "HH:mm - dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY_2 = "HH:mm dd/MM/yyyy";
    public static final String DATE_FORMAT_YYYY_MM_DD_A = "yyyy/MM/dd a";
    public static final String INPUT_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String DATE_FORMAT_YYYY_MM = "MM/yyyy";
    private static final String TAG = DateTimeUtils.class.getName();
    private static final int DAY_OF_YEAR = 365;
    private static final String TIME_ZONE_GMT = "GMT";
    private static final int MINUTES_OF_HOUR = 60;
    private static final int FIRST_DAY_OF_MONTH = 1;

    private DateTimeUtils() {
        // No-op
    }

    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    public static String convertToString(Date source, String format) {
        if (source == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(format, Locale.US);
        return df.format(source);
    }

    public static Date convertStringToDate(String date) {
        SimpleDateFormat parser = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.getDefault());
        try {
            return parser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date();
        }
    }

    public static Date convertStringToDate(String date, String format) {
        SimpleDateFormat parser = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return parser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date();
        }
    }
}
