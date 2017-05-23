package br.com.ande.util;

import java.util.Calendar;
import java.util.Date;

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
    public static String printDifference(Date startDate, Date endDate){

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
            hours = elapsedMinutes+"m";
        }

        return hours;

    }

    public static long getCurrentTimeInMillis(){
        Date date               = new Date();
        Calendar cal            = Calendar.getInstance();
        cal.setTime(date);
        return cal.getTimeInMillis();
    }

}
