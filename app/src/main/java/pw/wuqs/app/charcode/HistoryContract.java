package pw.wuqs.app.charcode;

import android.provider.BaseColumns;

/**
 * Created by Qishen Wu on 2017-05-16.
 */

class HistoryContract {
    private HistoryContract() {}

    /* Inner class that defines the table contents */
    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_ASCII = "unicode";
    }

}
