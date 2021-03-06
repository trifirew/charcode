package pw.wuqs.app.charcode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qishen Wu on 2017-05-16.
 */

class HistoryDataSource {

    // Database fields
    private SQLiteDatabase database;
    private HistoryDbHelper dbHelper;
    private String[] allColumns = {
            HistoryContract.HistoryEntry._ID,
            HistoryContract.HistoryEntry.COLUMN_NAME_ASCII,
    };
    private final String DESC = HistoryContract.HistoryEntry._ID + " DESC";

    public HistoryDataSource(Context context) {
        dbHelper = new HistoryDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertHistory(CharCode charCode) {
        ContentValues values = new ContentValues();
        values.put(HistoryContract.HistoryEntry.COLUMN_NAME_ASCII, charCode.getUnicode());

        // Delete history with same character
        deleteHistory(charCode);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = database.insert(HistoryContract.HistoryEntry.TABLE_NAME, null, values);
        Cursor cursor = database.query(
                HistoryContract.HistoryEntry.TABLE_NAME,
                allColumns,
                HistoryContract.HistoryEntry._ID + " = " + newRowId,
                null, null, null, null
        );
        cursor.moveToFirst();
        CharCode history = cursorToCharCode(cursor);
        cursor.close();
    }

    public void deleteHistory(CharCode charCode) {
        int unicode = charCode.getUnicode();
        database.delete(
                HistoryContract.HistoryEntry.TABLE_NAME,
                HistoryContract.HistoryEntry.COLUMN_NAME_ASCII + " = " + unicode,
                null
        );
    }

    public void deleteAllHistory() {
        database.delete(
                HistoryContract.HistoryEntry.TABLE_NAME,
                null, null
        );
    }

    public CharCode getHistoryById(long id) {
        Cursor cursor = database.query(
                HistoryContract.HistoryEntry.TABLE_NAME,
                allColumns,
                HistoryContract.HistoryEntry._ID + " = " + id,
                null, null, null, null
        );
        cursor.moveToFirst();
        CharCode charCode = cursorToCharCode(cursor);
        cursor.close();
        return charCode;
    }

    public List<CharCode> getAllHistory() {
        List<CharCode> allHistory = new ArrayList<>();

        Cursor cursor = database.query(
                HistoryContract.HistoryEntry.TABLE_NAME,
                allColumns,
                null, null, null, null, DESC
                );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CharCode charCode = cursorToCharCode(cursor);
            allHistory.add(charCode);
            cursor.moveToNext();
        }
        cursor.close();
        return allHistory;
    }

    private CharCode cursorToCharCode(Cursor cursor) {
        return new CharCode(cursor.getInt(1));
    }
}
