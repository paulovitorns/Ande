package br.com.ande.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class DateUtils {

    public static Date getDateFromTimestamp(long timestamp){
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(timestamp);
//        return cal.getTime();
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        sdf.setTimeZone(tz);//set time zone.
        String localTime = sdf.format(new Date(timestamp * 1000));
        Date date = new Date();
        try {
            date = sdf.parse(localTime);//get local date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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

//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays,
//                elapsedHours, elapsedMinutes, elapsedSeconds);

        return hours;

    }

}
