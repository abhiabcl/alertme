package alertme.flavortech.com.alertme.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alertme.flavortech.com.alertme.R;
import alertme.flavortech.com.alertme.util.AlertHandler;
import alertme.flavortech.com.alertme.util.AlertMeConstant;


public class SettingActivity extends Activity {

    private static final String mTag = SettingActivity.class.getName();
    private ToggleButton tbEmAlertMail;
    private ToggleButton tbEmAlertSms;
    private ToggleButton tbEmAlertCall;
    private ToggleButton tbGnAlertMail;
    private ToggleButton tbGnAlertSms;
    private ToggleButton tbGnSaveHistory;
    private Spinner spSnoozeNo;
    private Spinner spSnoozeInterval;
    private Spinner spUpdateInterval;
    private Integer mNumberofSnooze;
    private Integer mSnoozeInterval;
    private Integer mUpdateInterval;
    private HashMap hmSettingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView tv_activity_header = (TextView)findViewById(R.id.activity_header);
        tv_activity_header.setText(R.string.settingtitle);

        tbEmAlertMail = (ToggleButton)findViewById(R.id.t_btn_EmMailNotify);
        tbEmAlertSms = (ToggleButton)findViewById(R.id.t_btn_EmSMSNotify);
        tbEmAlertCall = (ToggleButton)findViewById(R.id.t_btn_EmCallNotify);
        tbGnAlertMail = (ToggleButton)findViewById(R.id.t_btn_GnMailNotify);
        tbGnAlertSms = (ToggleButton)findViewById(R.id.t_btn_GnSMSNotify);
        tbGnSaveHistory = (ToggleButton)findViewById(R.id.t_btn_GnSaveTrip);

        spSnoozeNo = (Spinner) findViewById(R.id.lv_NoSnooze);
        spSnoozeInterval = (Spinner) findViewById(R.id.lv_IntervalSnooze);
        spUpdateInterval = (Spinner) findViewById(R.id.lv_UpdateInterval);

        List<String> NoSnooze = new ArrayList<String>();
        for (int i=0; i<=10; i++){
            NoSnooze.add(String.valueOf(i));
        }
        ArrayAdapter<String> NoSnoozeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, NoSnooze);
        NoSnoozeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSnoozeNo.setAdapter(NoSnoozeAdapter);

        List<String> IntervalSnooze = new ArrayList<String>();
        for (int i=0; i<=60; i++){
            IntervalSnooze.add(String.valueOf(i));

        }

        ArrayAdapter<String> IntervalSnoozeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, IntervalSnooze);
        IntervalSnoozeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSnoozeInterval.setAdapter(IntervalSnoozeAdapter);
        spUpdateInterval.setAdapter(IntervalSnoozeAdapter);

        if ( (AlertHandler.GetSharedPreferences(mTag, this)) != null ){
            HashMap hmProfileData = AlertHandler.GetSharedPreferences(mTag, SettingActivity.this);


            tbEmAlertMail.setText(hmProfileData.get(AlertMeConstant.SettingEmAlertMail).toString());
            tbEmAlertCall.setText(hmProfileData.get(AlertMeConstant.SettingEmAlertCall).toString());
            tbEmAlertSms.setText(hmProfileData.get(AlertMeConstant.SettingEmAlertSms).toString());

            tbGnAlertSms.setText(hmProfileData.get(AlertMeConstant.SettingGnAlertSms).toString());
            tbGnAlertMail.setText(hmProfileData.get(AlertMeConstant.SettingGnAlertMail).toString());
            tbGnSaveHistory.setText(hmProfileData.get(AlertMeConstant.SettingGnSaveHistory).toString());
            spUpdateInterval.setSelection(Integer.parseInt(hmProfileData.get(AlertMeConstant.SettingUpdateInterval).toString()));

            spSnoozeNo.setSelection(Integer.parseInt(hmProfileData.get(AlertMeConstant.SettingSnoozeNo).toString()));
            spSnoozeInterval.setSelection(Integer.parseInt(hmProfileData.get(AlertMeConstant.SettingSnoozeInterval).toString()));
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you want save setting?")
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1) {
                        Log.i(mTag, "No need to save data");
                        SettingActivity.super.onBackPressed();
                    }
                })
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Log.i(mTag, "Going to save setting data");
                        hmSettingData = new HashMap();

                        hmSettingData.put(AlertMeConstant.SettingEmAlertMail, tbEmAlertMail.getText());
                        hmSettingData.put(AlertMeConstant.SettingEmAlertSms, tbEmAlertSms.getText());
                        hmSettingData.put(AlertMeConstant.SettingEmAlertCall, tbEmAlertCall.getText());

                        hmSettingData.put(AlertMeConstant.SettingGnAlertMail, tbGnAlertMail.getText());
                        hmSettingData.put(AlertMeConstant.SettingGnAlertSms, tbGnAlertSms.getText());
                        hmSettingData.put(AlertMeConstant.SettingGnSaveHistory, tbGnSaveHistory.getText());
                        hmSettingData.put(AlertMeConstant.SettingUpdateInterval, spUpdateInterval.getSelectedItemId());

                        hmSettingData.put(AlertMeConstant.SettingSnoozeNo, spSnoozeNo.getSelectedItem().toString());
                        hmSettingData.put(AlertMeConstant.SettingSnoozeInterval, spSnoozeInterval.getSelectedItem().toString());

                        AlertHandler.SetSharedPreferences(mTag, hmSettingData, SettingActivity.this, true);
                        SettingActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
