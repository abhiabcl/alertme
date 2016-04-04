package alertme.flavortech.com.alertme.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import alertme.flavortech.com.alertme.R;
import alertme.flavortech.com.alertme.util.AlertHandler;
import alertme.flavortech.com.alertme.util.AlertMeConstant;
import alertme.flavortech.com.alertme.util.AlertValidator;


public class ProfileActivity extends Activity {

    private static final String mTag = ProfileActivity.class.getName();
    Boolean isProfileCreated = Boolean.FALSE;
    TextView tv_activity_header;
    Button mSubmitProfile;
    EditText mEtProfileName;
    EditText mEtProfileEmail;
    EditText mEtProfileEmEmail;
    EditText mEtProfileMobile;
    EditText mEtProfileEmMobile;
    EditText mEtProfileAddress;

    HashMap hmProfileData;
    Boolean isDataValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.i(mTag, "In-side onCreate");

        tv_activity_header = (TextView)findViewById(R.id.activity_header);
        tv_activity_header.setText(R.string.profiletitle);
        mSubmitProfile = (Button)findViewById(R.id.btn_submit);
        mEtProfileName = (EditText)findViewById(R.id.et_name);
        mEtProfileEmail = (EditText)findViewById(R.id.et_mail);
        mEtProfileEmEmail = (EditText)findViewById(R.id.et_emeemail);
        mEtProfileMobile = (EditText)findViewById(R.id.et_mobile);
        mEtProfileEmMobile = (EditText)findViewById(R.id.et_ememobno);
        mEtProfileAddress = (EditText)findViewById(R.id.et_address);

        // Checking profile is already created, if not asking to fill the data.
        if ( (AlertHandler.GetSharedPreferences(mTag, this)) != null ) {
            isProfileCreated = true;
            mSubmitProfile.setText(R.string.updatebtntitle);
            HashMap hmProfileData = AlertHandler.GetSharedPreferences(mTag, ProfileActivity.this);

            mEtProfileName.setText(hmProfileData.get(AlertMeConstant.ProfileName).toString());
            mEtProfileEmail.setText(hmProfileData.get(AlertMeConstant.ProfileMail).toString());
            mEtProfileEmEmail.setText(hmProfileData.get(AlertMeConstant.ProfileEmMail).toString());
            mEtProfileMobile.setText(hmProfileData.get(AlertMeConstant.ProfileMobile).toString());
            mEtProfileEmMobile.setText(hmProfileData.get(AlertMeConstant.ProfileEmMobile).toString());
            mEtProfileAddress.setText(hmProfileData.get(AlertMeConstant.ProfileAddress).toString());

            AlertHandler.sendEmail(this,"AlertMe profile updated","Thanks "+mEtProfileName.getText()+" for updating your profile.");
        }else{
            mSubmitProfile.setText(R.string.savebtntitle);
            AlertHandler.sendEmail(this, "AlertMe profile created", "Thanks " + mEtProfileName.getText() + " for creating your profile.");
        }

        mEtProfileMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // pick it from contact
            }

        });

        mEtProfileEmMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pick it from contact

            }

        });


        mSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(mTag, "onButton submit");
                if ( ! isProfileCreated) {
                    Log.i(mTag, "Updating your profile ");
                        setProfileData(false);
                } else {
                    Log.i(mTag, "Creating your profile ");
                    // Create a hash map
                    setProfileData(true);
                }
            }
        });
    }

    private void pickContect(String pEditType){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        intent.putExtra("EditType", pEditType);

        startActivityForResult(intent, AlertMeConstant.PICK_CONTACT);
    }

    private void setProfileData( Boolean pIsNew ){
        hmProfileData = new HashMap();
        isDataValid = Boolean.FALSE;
        // Put elements to the map

        if (TextUtils.isEmpty(mEtProfileName.getText().toString())){
            mEtProfileName.setError("Name must not empty");
            return;
        }else{
            hmProfileData.put(AlertMeConstant.ProfileName, mEtProfileName.getText());
        }

        if (TextUtils.isEmpty(mEtProfileEmail.getText().toString())){
            mEtProfileEmail.setError("Email must not empty");
            return;
        }else{
            if (AlertValidator.isValidEmail(mEtProfileEmail.getText())) {
                hmProfileData.put(AlertMeConstant.ProfileMail, mEtProfileEmail.getText());
            }else{
                mEtProfileEmail.setError("Email is not valid");
                return;
            }
        }

        if (TextUtils.isEmpty(mEtProfileEmEmail.getText().toString())){
            mEtProfileEmEmail.setError("Emergency Mail must not empty");
            return;
        }else{
            if (AlertValidator.isValidEmail( mEtProfileEmEmail.getText())) {
                hmProfileData.put(AlertMeConstant.ProfileEmMail, mEtProfileEmEmail.getText());
            }else {
                mEtProfileEmEmail.setError("Emergency Mail is not valid");
                return;
            }
        }

        if (TextUtils.isEmpty(mEtProfileMobile.getText().toString())){
            mEtProfileMobile.setError("Mobile number must not empty");
            return;
        }else{
            if (AlertValidator.isValidPhoneNumber(mEtProfileMobile.getText())) {
                hmProfileData.put(AlertMeConstant.ProfileMobile, mEtProfileMobile.getText());
            }else{
                mEtProfileMobile.setError("Mobile is not valid");
                return;
            }
        }

        if (TextUtils.isEmpty(mEtProfileEmMobile.getText().toString())){
            mEtProfileEmMobile.setError("Emergency mobile number not empty");
            return;
        }else{
            if ( AlertValidator.isValidPhoneNumber(mEtProfileEmMobile.getText())){
                hmProfileData.put(AlertMeConstant.ProfileEmMobile, mEtProfileEmMobile.getText());
            }else{
                mEtProfileEmMobile.setError("Emergency mobile number is not valid");
                return;
            }
        }

        if (TextUtils.isEmpty(mEtProfileAddress.getText().toString())){
            mEtProfileAddress.setError("Address must not empty");
            return;
        }else{

                hmProfileData.put(AlertMeConstant.ProfileAddress, mEtProfileAddress.getText());

        }

        AlertHandler.SetSharedPreferences(mTag, hmProfileData, ProfileActivity.this, pIsNew);
        isDataValid = Boolean.TRUE;
        Log.i(mTag, "Profile data is set into.............");
        Log.i(mTag, "profile data is saved.");
        isProfileCreated = true;
        mSubmitProfile.setText(R.string.updatebtntitle);
        ProfileActivity.this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (AlertMeConstant.PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    String mobileno;
                    Cursor cursor =  getContentResolver().query(contactData, null, null, null, null);
                    try {
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                        int contactIdIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
                        int nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        int phoneNumberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int photoIdIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
                        String phoneNumber;

                        cursor.moveToFirst();
                        do {
//                            String name = cursor.getString(nameIdx);
//                            String idContact = cursor.getString(contactIdIdx);
                            phoneNumber = cursor.getString(phoneNumberIdx);

                        } while (cursor.moveToNext());

                        if ( data.getStringExtra("EditType").equalsIgnoreCase("EM")){
                            mEtProfileEmMobile.setText(phoneNumber);
                        }else{
                            mEtProfileMobile.setText(phoneNumber);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                }
                break;
        }
    }
}