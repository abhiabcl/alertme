package alertme.flavortech.com.alertme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import alertme.flavortech.com.alertme.R;
import alertme.flavortech.com.alertme.adapter.TripviewAdapter;
import alertme.flavortech.com.alertme.util.AlertHandler;
import alertme.flavortech.com.alertme.util.AlertMeConstant;
import alertme.flavortech.com.alertme.util.TripData;
import alertme.flavortech.com.alertme.util.TripDataSource;


public class TripHistoryActivity extends Activity {

    private ListView mLVTripDetails;
    private static TripHistoryActivity inst;

    TripDataSource datasource;
    List<TripData> tripdata;
    Boolean isHistoryEnable = Boolean.FALSE;
    private String mTag = "TripHistoryActivity";


    public static TripHistoryActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triphistory);

        TextView tv_activity_header = (TextView)findViewById(R.id.activity_header);
        tv_activity_header.setText(R.string.triphistory);

        mLVTripDetails = (ListView) findViewById(R.id.listview);

        // Check show history is enable then show the history
        // if not enable, show messages "History not enable "

        if ((AlertHandler.GetSharedPreferences(SettingActivity.class.getName(), this)) != null) {
            Log.i(mTag, "Setting data found");
            String savedInterval = AlertHandler.GetSharedPreferencesByValue(SettingActivity.class.getName(), this, AlertMeConstant.SettingGnSaveHistory);

            Log.i(mTag, "Setting data with Histroy is enable : ");

            if (savedInterval.equalsIgnoreCase("ON")) {
                isHistoryEnable = true;
            }
        }

        if ( isHistoryEnable ) {
            datasource = new TripDataSource(this);
            datasource.open();

            if (datasource.getTripCount() > 0) {
                tripdata = datasource.getAllData();
                ArrayList<HashMap> list = new ArrayList<HashMap>();
                Iterator itr = tripdata.iterator();

                while (itr.hasNext()) {
                    list.add(AlertHandler.populateList((TripData) itr.next()));
                }

                TripviewAdapter adapter = new TripviewAdapter(this, list);
                mLVTripDetails.setAdapter(adapter);
            }
        } else {
            //show message in TextView "History not enable", For enable history update setting
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stop, menu);
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
