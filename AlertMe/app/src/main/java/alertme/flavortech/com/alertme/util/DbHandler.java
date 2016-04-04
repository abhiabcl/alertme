package alertme.flavortech.com.alertme.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static alertme.flavortech.com.alertme.util.AlertMeConstant.*;
/**
 * Created by etbdefi on 11/5/2015.
 */
public class DbHandler extends SQLiteOpenHelper {

    private static final String mTag = DbHandler.class.getSimpleName();

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_COMMENTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT 1, "
            + COLUMN_Title + " text not null,"
            + COLUMN_Drop + " text not null,"
            + COLUMN_Pickup + " text not null,"
            + COLUMN_ImgUri + " text not null,"
            + COLUMN_Duration + " text not null,"
            + COLUMN_Status + " text not null);";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i(mTag, "onCreate.... : "+ DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(mTag,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

}