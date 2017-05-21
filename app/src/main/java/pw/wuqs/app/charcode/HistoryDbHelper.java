package pw.wuqs.app.charcode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Qishen Wu on 2017-05-16.
 */


class HistoryDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "History.db";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + HistoryContract.HistoryEntry.TABLE_NAME + " (" +
                    HistoryContract.HistoryEntry._ID + " INTEGER PRIMARY KEY," +
                    HistoryContract.HistoryEntry.COLUMN_NAME_ASCII + " INTEGER)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + HistoryContract.HistoryEntry.TABLE_NAME;

    public HistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
