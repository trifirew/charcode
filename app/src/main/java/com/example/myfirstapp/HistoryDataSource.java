package com.example.myfirstapp;

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

public class HistoryDataSource {

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

    public CharCode insertHistory(CharCode charCode) {
        ContentValues values = new ContentValues();
        values.put(HistoryContract.HistoryEntry.COLUMN_NAME_ASCII, charCode.getUnicode());

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
        return history;
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
        List<CharCode> allHistory = new ArrayList<CharCode>();

        Cursor cursor = database.query(
                HistoryContract.HistoryEntry.TABLE_NAME,
                allColumns,
                null, null, HistoryContract.HistoryEntry.COLUMN_NAME_ASCII, null, DESC
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
