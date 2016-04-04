package alertme.flavortech.com.alertme.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import alertme.flavortech.com.alertme.R;
import alertme.flavortech.com.alertme.util.AlertHandler;
import alertme.flavortech.com.alertme.util.AlertMeConstant;
import alertme.flavortech.com.alertme.util.DateTimeHandler;
import alertme.flavortech.com.alertme.util.TripDataSource;

/**
 * Created by etbdefi on 1/22/2016.
 */
public class AlarmReceiverActivity extends Activity {
    private MediaPlayer mMediaPlayer;
    private final static String mTag = "AlarmReceiverActivity";

    private static AlarmReceiverActivity inst;
    private TripDataSource datasource;

    public static AlarmReceiverActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        datasource = new TripDataSource(this);
        datasource.open();

        Log.i(mTag, "onCreate..................");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.alarm_notification);

        Button stopAlarm = (Button) findViewById(R.id.alarm_stop);
        Button snoozeAlarm = (Button) findViewById(R.id.alarm_snooze);

        stopAlarm.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Log.i(mTag, "onStop button touch");
                //update trip status in db from inprocess to completed.

                new AlertDialog.Builder(inst)
                        .setTitle("Trip Completed")
                        .setMessage("Is trip is completed?")
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface arg0, int arg1) {
                                Log.i(mTag, "Trip is canceled.");
                                if ( datasource.updateTripInProcessStatus("Canceled") )
                                    Log.i(mTag, "Trip data is updated!");
                                else
                                    Log.i(mTag, "Trip data update got error");
                                //Check updated mode from setting and send update.
                                // if isMail Enable
                                if ( (AlertHandler.GetSharedPreferences(mTag, AlarmReceiverActivity.this)) != null ) {
                                    HashMap hmSettingeData = AlertHandler.GetSharedPreferences(mTag, AlarmReceiverActivity.this);
                                    if ( hmSettingeData.get(AlertMeConstant.SettingGnAlertMail).toString() == ""){
                                        AlertHandler.sendEmail(AlarmReceiverActivity.this, "AlertMe: Trip update", "Your trip is completed!");
                                    }

                                }
                                //Get mail id from the profile setting and send mail
                                // if isSms Enable
                                // Get Phone no from the profile setting and send SMS

                            }
                        })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Log.i(mTag, "Trip is completed.");
                                HashMap hmSettingeData = AlertHandler.GetSharedPreferences(mTag, AlarmReceiverActivity.this);
                                if (datasource.updateTripInProcessStatus("Completed")) {
                                    Log.i(mTag, "Trip data is updated!");
                                    if (hmSettingeData.get(AlertMeConstant.SettingGnAlertMail).toString() == "") {
                                        AlertHandler.sendEmail(AlarmReceiverActivity.this, "AlertMe: Trip update", "Your trip is stoped!");
                                    }
                                } else {
                                    Log.i(mTag, "Trip data update got error");

                                    if (hmSettingeData.get(AlertMeConstant.SettingGnAlertMail).toString() == "") {
                                        AlertHandler.sendEmail(AlarmReceiverActivity.this, "AlertMe: Trip update", "Trip data update got error!");
                                    }
                                }
                                }
                            }

                            ).

                            create()

                            .

                            show();

                            mMediaPlayer.stop();

                            finish();

                            return false;
                        }
            });

        snoozeAlarm.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Log.i(mTag, "snoozeAlarm button touch");
                long interValMills;
                //Get snooze interval from setting and get newCalTime and start

                mMediaPlayer.stop();
                finish();

                if ((AlertHandler.GetSharedPreferences(SettingActivity.class.getName(), inst)) != null) {
                    Log.i(mTag, "Setting data found");
                    String savedInterval = AlertHandler.GetSharedPreferencesByValue(SettingActivity.class.getName(), inst, AlertMeConstant.SettingSnoozeInterval);

                    Log.i(mTag, "Setting data with savedInterval: " + savedInterval);

                    if (!(savedInterval.equalsIgnoreCase("None") && savedInterval == null)) {
                        interValMills = Long.parseLong(savedInterval);
                        interValMills = interValMills * 60 * 60;
                    } else {
                        interValMills = 0;
                    }

                    Calendar alarmTime;
                    alarmTime = DateTimeHandler.getTripEndTime(0, 1);
                    AlertHandler.setAlarm(alarmTime, getApplicationContext());
                }
                return false;
            }
        });

        playSound(this, getAlarmUri());
    }

    private void playSound(Context context, Uri alert) {
        Log.i(mTag, "playSound..................");
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
}
