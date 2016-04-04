package alertme.flavortech.com.alertme.util;

import android.text.format.Time;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by etbdefi on 12/30/2015.
 */
public class DateTimeHandler {

    private static final String mTag = "DateTimeHandler";

    public static Calendar getTripEndTime (Integer pHour, Integer pMinute){
//        Time today = new Time(Time.getCurrentTimezone());
//        today.setToNow();
//
//        Log.d(mTag, "Day: " + today.monthDay);             // Day of the month (1-31)
//        Log.d(mTag, "Month: " + today.month);              // Month (0-11)
//        Log.d(mTag, "Year: " + today.year);                // Year
//        Log.d(mTag, "Time: " + today.format("%k:%M:%S"));

        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        Log.d(mTag, "Old Calender: " + calSet.toString());

//        calSet.add(Calendar.HOUR_OF_DAY, pHour);
        calSet.add(Calendar.MINUTE, 1);


//        calSet.set(Calendar.DAY_OF_MONTH, today.monthDay);
//        calSet.set(Calendar.MONTH, today.month);
//        calSet.set(Calendar.YEAR, today.);
//        calSet.set(Calendar.HOUR, today.hour + pHour);
//        calSet.set(Calendar.MINUTE, today.minute + pMinute);

        Log.d(mTag, "New Calender: "+ calSet.toString());

        return calSet;
    }

}
