package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private HistoryDataSource dataSource;

    EditText charEditText, asciiEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new HistoryDataSource(this);
        dataSource.open();

        charEditText = (EditText) findViewById(R.id.charEditText);
        asciiEditText = (EditText) findViewById(R.id.asciiEditText);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        Bundle bundle = new Bundle();
        String myChar = charEditText.getText().toString();
        String myAscii = asciiEditText.getText().toString();
        bundle.putString("CHAR", myChar);
        bundle.putString("ASCII", myAscii);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void convertToAscii(View view) {
        if (charEditText.getText().toString().length() > 0) {
            char ch = charEditText.getText().toString().charAt(0);
            int ascii = (int) ch;
            History history = dataSource.insertHistory(ascii, Character.toString(ch));

            asciiEditText.setText(Integer.toString(ascii));
            asciiEditText.requestFocus();
            asciiEditText.selectAll();
        } else {
            Toast.makeText(this, R.string.empty_char, Toast.LENGTH_SHORT).show();
        }
    }

    public void convertToChar(View view) {
        if (asciiEditText.getText().toString().length() > 0) {
            int ascii = Integer.parseInt(asciiEditText.getText().toString());
            char ch = (char) ascii;
            History history = dataSource.insertHistory(ascii, Character.toString(ch));

            charEditText.setText(history.getCh());
            charEditText.requestFocus();
            charEditText.selectAll();
        } else {
            Toast.makeText(this, R.string.empty_ascii, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }
}
