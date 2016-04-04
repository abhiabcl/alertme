package alertme.flavortech.com.alertme.activity;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;

import alertme.flavortech.com.alertme.R;

/**
 * Created by etbdefi on 12/30/2015.
 */



// NOT IN USE- NEED TO REMOVE

public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;
    private Button alarmStop;


    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        sendNotification("Wake Up! Wake Up! Trip is going to end");
    }

    private void sendNotification(String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.alarm_notification);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, StartActivity.class), 0);

//        Intent buttonsIntent = new Intent(this, AlarmReceiverActivity.class);
//        buttonsIntent.putExtra("do_action", "play");
//        remoteViews.setOnClickPendingIntent(R.id.alarm_stop, PendingIntent.getActivity(this, 0, buttonsIntent, 0));

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alert Me").setSmallIcon(R.drawable.alertme)
                .setContent(remoteViews);

        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.i("AlarmService", "Notification sent.");
    }
}
