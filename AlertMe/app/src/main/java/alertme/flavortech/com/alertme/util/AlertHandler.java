package alertme.flavortech.com.alertme.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import alertme.flavortech.com.alertme.activity.AlarmReceiverActivity;

import static alertme.flavortech.com.alertme.util.AlertMeConstant.*;

/**
 * Created by etbdefi on 12/31/2015.
 */
public class AlertHandler {

    private static final String mTag = "AlertHandler";


    private static void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public static void sendEmail(Activity pActivity, String pSubject, String pMailTxt) {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, pSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, pMailTxt);

        try {
            pActivity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
            Log.i(mTag, "Finished sending email...");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(pActivity, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendSMS(Activity pActivity, String pSMSNumber, String pSMSText ){
        Uri uri = Uri.parse("smsto:" + pSMSNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", pSMSText);
        pActivity.startActivity(intent);
    }

    public static HashMap populateList( TripData data ) {
//        ArrayList<HashMap> list;
//        list = new ArrayList<HashMap>();

        HashMap<String, String> temp1 = new HashMap<String, String>();
        temp1.put(TRIP_TITLE, data.getTripTitle());
        temp1.put(TRIP_PICKUP, data.getTripPickup());
        temp1.put(TRIP_DROP, data.getTripDrop());

        temp1.put(TRIP_DURATION, data.getTripDuration());
        temp1.put(TRIP_STATUS, data.getTripStatus());
//        list.add(temp1);

        return temp1;

    }

    public static String GetAddressFromCord (double lat, double lan ){
        String locAddress = "";
        // send get to url
        // http://maps.googleapis.com/maps/api/geocode/json?latlng=44.4647452,7.3553838&sensor=true
//        5C:FA:B4:41:DC:50:6B:C5:7F:6B:A8:D8:0A:45:2F:07:E2:A6:DD:CE;
        return  locAddress;
    }

    public static void SetSharedPreferences(String pPrefName, HashMap pData, Context pCtx, Boolean pIsNew ) {
        Log.i(mTag, "Going to save data into preference :" + pPrefName);
        SharedPreferences preferences = pCtx.getSharedPreferences(pPrefName, pCtx.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(pPrefName, true);
        Set set = pData.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            editor.putString(me.getKey().toString(), me.getValue().toString());
            Log.i(mTag, "Key: "+ me.getKey() +" Value: "+me.getValue().toString());
        }
        if (pIsNew ) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    public static HashMap GetSharedPreferences( String pPrefName, Context pCtx){
        Log.i(mTag, "Going to get data from preference :" + pPrefName);
        HashMap hmData = new HashMap();
        SharedPreferences prefs = pCtx.getSharedPreferences(
                pPrefName, Context.MODE_PRIVATE);
        if ( prefs.contains(pPrefName) ) {
            for (Map.Entry entry : prefs.getAll().entrySet())
                hmData.put(entry.getKey(), entry.getValue().toString());
        }else{
            hmData = null;
        }
        return hmData;
    }

    public static String GetSharedPreferencesByValue (String pPrefName, Context pCtx, String pKey ){
        Log.i(mTag, "Going to get data from preference :" + pPrefName +" Key: "+ pKey);
        String mData = null;
        SharedPreferences prefs = pCtx.getSharedPreferences(
                pPrefName, Context.MODE_PRIVATE);
        if ( prefs.contains(pPrefName) ) {
            mData = prefs.getString(pKey, "None").toString();
        }
        return mData;
    }

    public static Boolean setAlarm(Calendar targetCal, Context mInst){
        Log.d(mTag, "Going to set Alarm for reminder");
        Boolean isAlarmSet = Boolean.FALSE;
        long interValMills = 1;
        try {
//            Intent myIntent = new Intent(StartActivity.this, AlarmReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(StartActivity.this, 0, myIntent, 0);
//        alarmManager.set(AlarmManager.RTC, targetCal.getTimeInMillis(), pendingIntent);
//        Intent intent = new Intent(getBaseContext(), StartActivity.class);
//            if ( (AlertHandler.GetSharedPreferences(SettingActivity.class.getName(), this)) != null ) {
//                Log.i(mTag, "Setting data found");
//                String savedInterval = AlertHandler.GetSharedPreferencesByValue(SettingActivity.class.getName(), this, AlertMeConstant.SettingSnoozeInterval);
//
//                Log.i(mTag, "Setting data with savedInterval: " +savedInterval);
//
//                if(!(savedInterval.equalsIgnoreCase("None") && savedInterval == null) ){
//                    interValMills = Long.parseLong(savedInterval);
//                    interValMills = interValMills *60 * 60;
//                }else{
//                    interValMills = 0;
//                }
//
//            }

            Intent intent = new Intent(mInst, AlarmReceiverActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mInst,
                    AlertMeConstant.ALERT_ALARM_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), AlertMeConstant.ALERT_ALARM_ID, myIntent, 0);
            AlarmManager alarmManager = (AlarmManager)mInst.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), interValMills, pendingIntent);
            Log.d(mTag, "Alarm Set");
            isAlarmSet = Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return isAlarmSet;
        }
    }
}
