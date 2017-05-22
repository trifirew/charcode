package pw.wuqs.app.charcode;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String CHARCODE_KEY = "pw.wuqs.app.charcode.charcode_key";

    private HistoryDataSource dataSource;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText charEditText;
    private EditText asciiEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open history database
        dataSource = new HistoryDataSource(this);
        dataSource.open();

        // Handle IME "Go" action
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

        TextWatcher charWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || s.toString().codePointAt(0) > 0xffff) {
                    s.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                } else {
                    s.delete(1, s.length());
                    s.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
                }
            }
        };

        // Set listener to EditText
        charEditText = (EditText) findViewById(R.id.charEditText);
        asciiEditText = (EditText) findViewById(R.id.asciiEditText);
        charEditText.setOnEditorActionListener(actionListener);
        asciiEditText.setOnEditorActionListener(actionListener);

        // Set text change listener to charEditText
        charEditText.addTextChangedListener(charWatcher);

        // Setup RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        updateHistory();
    }

    public void convert(View view) {
        if (view.getId() == R.id.toAsciiButton) {
            if (charEditText.getText().length() == 0) {
                Toast.makeText(this, R.string.empty_char, Toast.LENGTH_SHORT).show();
                return;
            }
            CharCode charCode = new CharCode(charEditText.getText().toString());
            charEditText.clearFocus();
            asciiEditText.setText(charCode.getDecUnicodeStr());
            dataSource.insertHistory(charCode);
        } else if (view.getId() == R.id.toCharButton) {
            if (asciiEditText.getText().length() == 0) {
                Toast.makeText(this, R.string.empty_ascii, Toast.LENGTH_SHORT).show();
                return;
            }
            String uniStr = asciiEditText.getText().toString();
            if (isParsable(uniStr) && Integer.parseInt(uniStr) < 0x110000) {
                CharCode charCode = new CharCode(Integer.parseInt(uniStr));
                asciiEditText.clearFocus();
                charEditText.setText(charCode.getCharStr());
                dataSource.insertHistory(charCode);
            } else {
                Toast.makeText(this, R.string.invalid_unicode, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        updateHistory();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void updateHistory() {
        List<CharCode> values = dataSource.getAllHistory();
        // Specify an adapter
        mAdapter = new HistoryAdapter(values);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showDetail(View view) {
        CharCode charCode = (CharCode) view.getTag();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(CHARCODE_KEY, charCode.getUnicode());
        startActivity(intent);
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

    private boolean isParsable(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            parsable = false;
        }
        return parsable;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAbout:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
