package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
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

        TextView.OnEditorActionListener actionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    if (v == findViewById(R.id.charEditText)) {
                        convert(findViewById(R.id.toAsciiButton));
                    } else if (v == findViewById(R.id.asciiEditText)) {
                        convert(findViewById(R.id.toCharButton));
                    }
                    handled = true;
                }
                return handled;
            }
        };


        charEditText.setOnEditorActionListener(actionListener);
        asciiEditText.setOnEditorActionListener(actionListener);
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

//    public void convertToAscii(View view) {
//        if (charEditText.getText().toString().length() > 0) {
//            char ch = charEditText.getText().toString().charAt(0);
//            int ascii = (int) ch;
//            CharCode history = dataSource.insertHistory(ascii);
//            asciiEditText.setText(Integer.toString(ascii));
//        } else {
//            Toast.makeText(this, R.string.empty_char, Toast.LENGTH_SHORT).show();
//        }
//    }

//    public void convertToChar(View view) {
//        if (asciiEditText.getText().toString().length() > 0) {
//            int ascii = Integer.parseInt(asciiEditText.getText().toString());
//            char ch = (char) ascii;
//            CharCode history = dataSource.insertHistory(ascii);
//            charEditText.setText(history.getCh());
//        } else {
//            Toast.makeText(this, R.string.empty_ascii, Toast.LENGTH_SHORT).show();
//        }
//    }

    public void convert(View view) {
        if (view == findViewById(R.id.toAsciiButton)) {
            if (charEditText.getText().length() == 0) {
                Toast.makeText(this, R.string.empty_char, Toast.LENGTH_SHORT).show();
                return;
            }
            CharCode charCode = new CharCode(charEditText.getText().charAt(0));
            charEditText.clearFocus();
            asciiEditText.setText(charCode.getDecUnicodeStr());
            dataSource.insertHistory(charCode);
        } else if (view == findViewById(R.id.toCharButton)) {
            if (asciiEditText.getText().length() == 0) {
                Toast.makeText(this, R.string.empty_ascii, Toast.LENGTH_SHORT).show();
                return;
            }
            CharCode charCode = new CharCode(Integer.parseInt(asciiEditText.getText().toString()));
            asciiEditText.clearFocus();
            charEditText.setText(charCode.getCharStr());
            dataSource.insertHistory(charCode);
        } else {
            return;
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
