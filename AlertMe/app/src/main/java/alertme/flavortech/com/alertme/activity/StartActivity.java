package alertme.flavortech.com.alertme.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import alertme.flavortech.com.alertme.R;
import alertme.flavortech.com.alertme.adapter.TripviewAdapter;
import alertme.flavortech.com.alertme.util.AlertHandler;
import alertme.flavortech.com.alertme.util.AlertMeConstant;
import alertme.flavortech.com.alertme.util.DateTimeHandler;
import alertme.flavortech.com.alertme.util.TripData;
import alertme.flavortech.com.alertme.util.TripDataSource;

import android.support.v4.app.FragmentActivity;

public class StartActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private final static String mTag = "StartActivity";

    private EditText mEtTipName;
    private EditText mEtPickupLoc;
    private EditText mEtDropLoc;
    private TextView mEtTripDuration;

    private Button mBtnCam;
    private Button mBtnPickLoc;
    private Button mBtnDropLoc;
    private Button mBtnTripStart;
    private ImageView mImgView;
    private Spinner mSpinnerHH;
    private Spinner mSpinnerMM;
    private LinearLayout mLLStopTrip;
    private ScrollView mSVStartTrip;
    private ListView mLVTripDetails;

    int PLACE_PICKER_REQUEST = 1;
    public static int TAKE_PHOTO_CODE = 0;
    public static int count=0;

    public static File mFile;
    public static File mDir;
    public static Bitmap mBitmap;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static StartActivity inst;

    private LocationManager mLocationManager;
    double latitude;
    double longitude;

    LatLng southWestBounds;
    LatLng northEastBounds;

    GoogleApiClient mGoogleApiClient;
    TripDataSource datasource;
    TripData tripData;
    Boolean isTripDataSet = Boolean.FALSE;


    public static StartActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
        if( mGoogleApiClient != null )
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Check if any trip is in-process show trip status with cancel
        // if not in-process show trip form

        datasource = new TripDataSource(this);
        datasource.open();

        TextView tv_activity_header = (TextView) findViewById(R.id.activity_header);
        tv_activity_header.setText(R.string.starttitle);

        mEtTipName = (EditText)findViewById(R.id.et_triptitle);
        mEtPickupLoc = (EditText)findViewById(R.id.et_pickup);
        mEtDropLoc = (EditText)findViewById(R.id.et_drop);
        mEtTripDuration = (TextView)findViewById(R.id.tv_duration);

        mBtnCam = (Button) findViewById(R.id.btn_cam);
        mBtnPickLoc = (Button) findViewById(R.id.bt_pick_loc);
        mBtnDropLoc = (Button) findViewById(R.id.bt_drop_loc);
        mBtnTripStart = (Button) findViewById(R.id.bt_start);
        mImgView = (ImageView) findViewById(R.id.imageView);

        mSpinnerHH = (Spinner) findViewById(R.id.spinner_hh);
        mSpinnerMM = (Spinner) findViewById(R.id.spinner_mm);

        mLLStopTrip = (LinearLayout) findViewById(R.id.ll_trip_stop);

        mSVStartTrip = (ScrollView)findViewById(R.id.sv_trip_start);
        mLVTripDetails = (ListView) findViewById(R.id.listview);

        //Check for in-process trip
        List<TripData> inProcesstrip = datasource.getInProcessTrip();
        if( inProcesstrip.size() > 0){
            InProcessTrip( inProcesstrip );
        }else{
            StartTrip();
        }


    }

    public void StartTrip(){

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        ;

        mLLStopTrip.setVisibility(View.INVISIBLE);

        List<String> itemsHH = new ArrayList<String>();
        for (int i=0; i<=24; i++){
            itemsHH.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterHH = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, itemsHH);
        adapterHH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerHH.setAdapter(adapterHH);

        List<String> itemsMM = new ArrayList<String>();
        for (int i=0; i<=60; i++){
            itemsMM.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterMM = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, itemsMM);
        adapterMM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMM.setAdapter(adapterMM);

        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/AlertMe/";
        Log.d(mTag, "Create dir for pict: "+dir);

        mDir = new File(dir);
        mDir.mkdirs();

        mBtnCam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // here,counter will be incremented each time,and the picture taken by camera will be stored as 1.jpg,2.jpg and likewise.
                count++;
                String file = dir + count + ".jpg";
                Log.d(mTag, "File: " + file);
                mFile = new File(file);
                try {
                    mFile.createNewFile();
                } catch (IOException e) {
                }

                Uri outputFileUri = Uri.fromFile(mFile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });

//        mBtnPickLoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (mGoogleApiClient == null || !mGoogleApiClient.isConnected())
//                    return;
//
//                try {
//                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
////                    LatLngBounds llb = new LatLngBounds(southWestBounds, northEastBounds);
////                    builder.setLatLngBounds(llb);
//                    Context context = getApplicationContext();
//                    startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

//        mBtnDropLoc.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//
//            }
//        });

        mBtnTripStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(mTag, "Trip started ....");
                getTripData();
                Integer HH = Integer.parseInt(mSpinnerHH.getSelectedItem().toString());
                Integer MM = Integer.parseInt(mSpinnerMM.getSelectedItem().toString());
                Calendar alarmTime;
                alarmTime = DateTimeHandler.getTripEndTime(HH, MM);
                if (AlertHandler.setAlarm(alarmTime, inst)) {
                    if (isTripDataSet && tripData != null) {
                        Log.i(mTag, "Going to save tip into db.");
                        datasource.insertData(tripData);

                        ArrayList<HashMap> list = new ArrayList<HashMap>();
                        list.add(AlertHandler.populateList(tripData));
                        TripviewAdapter adapter = new TripviewAdapter(inst, list);
                        mLVTripDetails.setAdapter(adapter);

                        mSVStartTrip.setVisibility(View.INVISIBLE);
                        mLLStopTrip.setVisibility(View.VISIBLE);
                        mBtnTripStart.setText("Cancel/Stop Trip");
                    } else {
                        Log.i(mTag, "Data not saved into db.");
                    }
                }
            }
        });

    }

    public void InProcessTrip( List<TripData>  pTripdata){

        //Get In-Process trip details
        //disable startTripVeiw and enable StopTripVeiw
        mSVStartTrip.setVisibility(View.INVISIBLE);
        mLLStopTrip.setVisibility(View.VISIBLE);
        mBtnTripStart.setText("Cancel/Stop Trip");

        ArrayList<HashMap> list = new ArrayList<HashMap>();
        Iterator itr = pTripdata.iterator();
        while (itr.hasNext()) {
            list.add(AlertHandler.populateList((TripData) itr.next()));
        }

        TripviewAdapter adapter = new TripviewAdapter(inst, list);
        mLVTripDetails.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d(mTag, "Pic saved");

            int height = mImgView.getHeight();
            int width = mImgView.getWidth() ;
            mBitmap = LoadAndResizeBitmap(mFile.getPath(), width, height);
            if (mBitmap != null) {
                mImgView.setImageBitmap(mBitmap);
                mBitmap = null;
            }
        }

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                final Place place = PlacePicker.getPlace(data, getApplicationContext());
                //Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }

            if (resultCode == RESULT_OK) {
                final Place place = PlacePicker.getPlace(data, getApplicationContext());
                //Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getTripData(){
        isTripDataSet = Boolean.FALSE;

        Log.i(mTag, "Filling object with tip data.");

        tripData = new TripData();
        int nextInt = new Random().nextInt(3);
        // save the new comment to the database
        Log.i(mTag, "Trip ID: "+nextInt);

        tripData.setId(nextInt);

        if (TextUtils.isEmpty(mEtTipName.getText().toString())){
            mEtTipName.setError("Name must not empty");
            return;
        }else{
            tripData.setTripTitle(mEtTipName.getText().toString());
        }

        if (TextUtils.isEmpty(mEtDropLoc.getText().toString())){
            mEtDropLoc.setError("Name must not empty");
            return;
        }else{
            tripData.setTripDrop(mEtDropLoc.getText().toString());
        }

        if (TextUtils.isEmpty(mEtPickupLoc.getText().toString())){
            mEtPickupLoc.setError("Name must not empty");
            return;
        }else{
            tripData.setTripPickup(mEtPickupLoc.getText().toString());
        }

        mEtTripDuration.setText("2");
        if (TextUtils.isEmpty(mEtTripDuration.getText().toString())){
            mEtTripDuration.setError("Name must not empty");
            return;
        }else{
            tripData.setTripDuration(mEtTripDuration.getText().toString());
        }

//        if (mFile.getPath() != null ){
//            tripData.setTripImgUri(mFile.getPath());
//        }
            tripData.setTripImgUri("dummy");


        tripData.setTripStatus("InProcess");
        isTripDataSet = Boolean.TRUE;
    }

    public Bitmap LoadAndResizeBitmap (String fileName, int width, int height)
    {
        // First we get the the dimensions of the file on disk

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;

        BitmapFactory.decodeFile(fileName, options);

        // Next we calculate the ratio that we need to resize the image by
        // in order to fit the requested dimensions.
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int inSampleSize = 1;

        if (outHeight > height || outWidth > width)
        {
            inSampleSize = outWidth > outHeight
                    ? outHeight / height
                    : outWidth / width;
        }

        // Now we will load the image and have BitmapFactory resize it for us.
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap resizedBitmap = BitmapFactory.decodeFile (fileName, options);

        return resizedBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
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



    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(mTag, "Location services connected.");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Log.i(mTag, String.valueOf(mLastLocation.getLatitude()));
            Log.i(mTag, String.valueOf(mLastLocation.getLongitude()));
        }

        if (mLastLocation == null) {
            // Blank for a moment...
        }
        else {
            handleNewLocation(mLastLocation);
        };
    }

    private void handleNewLocation(Location location) {
        Log.d(mTag, location.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(mTag, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        datasource.close();
    }
}
