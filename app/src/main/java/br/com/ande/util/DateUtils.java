package br.com.ande.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class DateUtils {

    public static Date getDateFromTimestamp(long timestamp){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return cal.getTime();
    }

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    public static HashMap<DATE_DIFFERENCE, Object> printDifference(Date startDate, Date endDate){

        HashMap<DATE_DIFFERENCE, Object> data = new HashMap<>();

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        String hours;

        if(elapsedHours > 0) {
            hours = elapsedHours + "h e " + elapsedMinutes + "m";
        }else{
            if(elapsedMinutes <= 0 )
                hours = elapsedSeconds+"s";
            else
                hours = elapsedMinutes+"m";
        }

        data.put(DATE_DIFFERENCE.DAYS, elapsedDays);
        data.put(DATE_DIFFERENCE.HOUR, elapsedHours);
        data.put(DATE_DIFFERENCE.MINUTES, elapsedMinutes);
        data.put(DATE_DIFFERENCE.SECONDS, elapsedSeconds);
        data.put(DATE_DIFFERENCE.STRING, hours);

        return data;

    }

    public static long getCurrentTimeInMillis(){
        Date date               = new Date();
        Calendar cal            = Calendar.getInstance();
        cal.setTime(date);
        return cal.getTimeInMillis();
    }

    public static Date toDate(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentDate(){
        Calendar c          = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(c.getTime());
    }

    public static boolean isCurrentDay(Date date){
        return DateUtils.getCurrentDate().equals(date);
    }

    public static String dateToQueryString(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    public enum DATE_DIFFERENCE{
        DAYS,
        HOUR,
        MINUTES,
        SECONDS,
        STRING
    }

}
