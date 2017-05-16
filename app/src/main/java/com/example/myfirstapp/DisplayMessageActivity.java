package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    private HistoryDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        dataSource = new HistoryDataSource(this);
        dataSource.open();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String myAscii = bundle.getString("ASCII");
        String myChar = bundle.getString("CHAR");

        myChar = dataSource.getHistoryById(1).getCh();

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(getString(R.string.message_ascii) + myAscii + getString(R.string.message_char) + myChar);
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}
