package alertme.flavortech.com.alertme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;
import android.widget.TextView;

import alertme.flavortech.com.alertme.R;
import alertme.flavortech.com.alertme.adapter.ButtonGridAdapter;
import alertme.flavortech.com.alertme.util.AlertHandler;

public class DashboardActivity extends Activity {

    private String mTag = "DashboardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        try {
            if ( (AlertHandler.GetSharedPreferences(ProfileActivity.class.getName(), this)) == null ) {
                Log.i(mTag, "Profile not found, Going to create.");
                Intent pMainActivity = new Intent(getBaseContext(),
                        ProfileActivity.class);
                startActivity(pMainActivity);
            }
            initializeButtonGrid();

        } catch (Exception ex) {
            Log.e(mTag, ex.getMessage());
        }

        TextView tv_activity_header = (TextView)findViewById(R.id.activity_header);
        tv_activity_header.setText(R.string.dashboardtitile);


    }


    private void initializeButtonGrid() {
        GridView grid = (GridView) findViewById(R.id.ButtonGrid);
        grid.setAdapter(new ButtonGridAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
