package alertme.flavortech.com.alertme.util;


/**
 * Created by etbdefi on 1/12/2016.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TripDataSource {

    // Database fields
    private SQLiteDatabase database;
    private DbHandler dbHelper;
    private String[] allColumns = { AlertMeConstant.COLUMN_ID, AlertMeConstant.COLUMN_Title, AlertMeConstant.COLUMN_Drop, AlertMeConstant.COLUMN_Pickup,
                                    AlertMeConstant.COLUMN_Duration, AlertMeConstant.COLUMN_ImgUri, AlertMeConstant.COLUMN_Status};
    private String mTag = "TripDataSource";

    public TripDataSource(Context context) {
        dbHelper = new DbHandler(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertData(TripData tripData) {

        Log.i(mTag, "Data is going to insert into DB..");
        ContentValues values = new ContentValues();
        values.put(AlertMeConstant.COLUMN_ID, tripData.getId());
        values.put(AlertMeConstant.COLUMN_Title, tripData.getTripTitle());
        values.put(AlertMeConstant.COLUMN_Drop, tripData.getTripDrop());
        values.put(AlertMeConstant.COLUMN_Pickup, tripData.getTripPickup());
        values.put(AlertMeConstant.COLUMN_ImgUri, tripData.getTripImgUri());
        values.put(AlertMeConstant.COLUMN_Duration, tripData.getTripDuration());
        values.put(AlertMeConstant.COLUMN_Status, tripData.getTripStatus());

        long insertId = database.insert(AlertMeConstant.TABLE_COMMENTS, null,
                values);

        database.close();
        return insertId;
    }

    public void deleteTrip(TripData tripdata) {
        Log.i(mTag, "deleteTrip");
        long id = tripdata.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(AlertMeConstant.TABLE_COMMENTS, AlertMeConstant.COLUMN_ID
                + " = " + id, null);
    }

    public List<TripData> getAllData() {
        Log.i(mTag, "getAllData");
        List<TripData> tips = new ArrayList<TripData>();

        Cursor cursor = database.query(AlertMeConstant.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TripData tripdata = cursorToTripData(cursor);
            tips.add(tripdata);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tips;
    }

    private TripData cursorToTripData(Cursor cursor) {
        Log.i(mTag, "cursorToTripData");
        TripData data = new TripData();
        data.setId(cursor.getLong(0));
        data.setTripTitle(cursor.getString(1));
        data.setTripDrop(cursor.getString(2));
        data.setTripPickup(cursor.getString(3));
        data.setTripImgUri(cursor.getString(4));
        data.setTripDuration(cursor.getString(5));
        data.setTripDuration(cursor.getString(6));
        return data;
    }

    public int getTripCount() {
        Log.i(mTag, "getTripCount");
        String countQuery = "SELECT  * FROM " + AlertMeConstant.TABLE_COMMENTS;
        Cursor cursor = database.rawQuery(countQuery, null);
//        cursor.close();

        // return count
        return cursor.getCount();
    }

    public List<TripData> getInProcessTrip() {
        Log.i(mTag, "getInProcessTrip");
        List<TripData> tips = new ArrayList<TripData>();

        String WHERE = AlertMeConstant.COLUMN_Status + "=" + "\'InProcess\'";

        Cursor cursor = database.query(AlertMeConstant.TABLE_COMMENTS,
                        allColumns, WHERE, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TripData tripdata = cursorToTripData(cursor);
            tips.add(tripdata);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tips;
    }

    public boolean updateTripInProcessStatus(String pStatus) {
        Log.i(mTag, "updateTripInProcessStatus");
        ContentValues args = new ContentValues();
        args.put(AlertMeConstant.COLUMN_Status, pStatus);

        return database.update(AlertMeConstant.TABLE_COMMENTS, args, AlertMeConstant.COLUMN_Status + "=" + "\'InProcess\'", null) > 0;
    }
}
