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
            HistoryContract.HistoryEntry.COLUMN_NAME_CHARACTER
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

    public History insertHistory(int ascii, String ch) {
        ContentValues values = new ContentValues();
        values.put(HistoryContract.HistoryEntry.COLUMN_NAME_ASCII, ascii);
        values.put(HistoryContract.HistoryEntry.COLUMN_NAME_CHARACTER, ch);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = database.insert(HistoryContract.HistoryEntry.TABLE_NAME, null, values);
        Cursor cursor = database.query(
                HistoryContract.HistoryEntry.TABLE_NAME,
                allColumns,
                HistoryContract.HistoryEntry._ID + " = " + newRowId,
                null, null, null, null
        );
        cursor.moveToFirst();
        History history = cursorToHistory(cursor);
        cursor.close();
        return history;
    }

    public History getHistoryById(long id) {
        Cursor cursor = database.query(
                HistoryContract.HistoryEntry.TABLE_NAME,
                allColumns,
                HistoryContract.HistoryEntry._ID + " = " + id,
                null, null, null, null
        );
        cursor.moveToFirst();
        History history = cursorToHistory(cursor);
        cursor.close();
        return history;
    }

    public List<History> getAllHistory() {
        List<History> allHistory = new ArrayList<History>();

        Cursor cursor = database.query(
                HistoryContract.HistoryEntry.TABLE_NAME,
                allColumns,
                null, null, HistoryContract.HistoryEntry.COLUMN_NAME_ASCII, null, DESC
                );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            History history = cursorToHistory(cursor);
            allHistory.add(history);
            cursor.moveToNext();
        }
        cursor.close();
        return allHistory;
    }

    private History cursorToHistory(Cursor cursor) {
        History history = new History();
        history.setId(cursor.getLong(0));
        history.setAscii(cursor.getInt(1));
        history.setCh(cursor.getString(2));
        return history;
    }
}
