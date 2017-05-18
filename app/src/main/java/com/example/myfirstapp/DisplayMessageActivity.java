package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DisplayMessageActivity extends AppCompatActivity {

    private HistoryDataSource dataSource;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        dataSource = new HistoryDataSource(this);
        dataSource.open();

        TextView textView = (TextView) findViewById(R.id.textView);

        listView = (ListView) findViewById(R.id.historyListView);
        List<History> values = dataSource.getAllHistory();

        if (values.size() != 0) {
            ArrayAdapter<History> adapter = new ArrayAdapter<History>(this, android.R.layout.simple_list_item_1, values);
            listView.setAdapter(adapter);
        } else {
            textView.setText("No history");
        }

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String myAscii = bundle.getString("ASCII");
        String myChar = bundle.getString("CHAR");


        // Capture the layout's TextView and set the string as its text
        if (myAscii.length() != 0) {
            textView.setText(getString(R.string.message_ascii) + myAscii + getString(R.string.message_char) + myChar);
        } else {
            textView.setText(R.string.history);
        }
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}
