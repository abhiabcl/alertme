package com.avsw.chatonbluetooth;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(R.layout.splash);
	Thread splashThread = new Thread() {
	@Override
	public void run() {
	try {
	int waited = 0;
	while (waited < 5000) { //Five second timer
	sleep(100);
	waited += 100;
	}
	} catch (InterruptedException e) { //Any errors that might occur
	// do nothing
	} finally {
	finish();
	Intent BTChatActivity = new Intent(getBaseContext(),
			BluetoothChat.class);
	startActivity(BTChatActivity);
	}
	}
	};
	splashThread.start();
	}
	@Override
	public void onBackPressed() {
	 
	return;
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

}
