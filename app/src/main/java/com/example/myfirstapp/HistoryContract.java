package com.example.myfirstapp;

import android.provider.BaseColumns;

/**
 * Created by Qishen Wu on 2017-05-16.
 */

public class HistoryContract {
    private HistoryContract() {}

    /* Inner class that defines the table contents */
    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_ASCII = "ascii";
        public static final String COLUMN_NAME_CHARACTER = "character";
    }

}
