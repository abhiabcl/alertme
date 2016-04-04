package alertme.flavortech.com.alertme.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import alertme.flavortech.com.alertme.util.AlertMeConstant;

/**
 * Created by etbdefi on 12/30/2015.
 */


// NOT IN USE- NEED TO REMOVE
public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
//        StartActivity inst = StartActivity.instance();
//        inst.setAlarmText("Alarm! Wake up! Wake up!");

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

        Log.i("AlarmReceiver", "Stop button press.....");
//        String as = Context.ALARM_SERVICE;
//
//        PendingIntent sender = PendingIntent.getBroadcast(context, AlertMeConstant.ALERT_ALARM_ID, intent, 0);
//
//        AlarmManager amg = (AlarmManager) context.getSystemService(as);
//
//        amg.cancel(sender);
    }
}
