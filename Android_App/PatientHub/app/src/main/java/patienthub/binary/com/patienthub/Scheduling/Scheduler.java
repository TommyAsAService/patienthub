package patienthub.binary.com.patienthub.Scheduling;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by elliotV on 11/10/15.
 */
public class Scheduler {


    public static int daysSince(String dateString){

        Date date = getStartDateAsDateType(dateString);

        //Get current/start date as Calendar types
        Date current = new Date();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(current);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);

        return daysBetween(startDate, currentDate);
    }

    public static String timeOfDay(){

        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        //Morning 00-1159
        // Afternoon 1200-1759
        // Evening 1800-2400
        if(currentHour<=11){
            return "Morning";
        } else if (currentHour <=17) {
            return "Afternoon";
        } else {
            return "Evening";
        }


    }

    //Converts String date from database to Date type
    private static Date getStartDateAsDateType(String dateString){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse(dateString);
            System.out.println(date);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return date;
    }

    private static int daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        int daysBetween = 0;
        Log.d("dates",date.toString()+ " "+endDate.toString());
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }
}
