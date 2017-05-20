package pw.wuqs.app.charcode;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

        // Set listener to EditText
        charEditText.setOnEditorActionListener(actionListener);
        asciiEditText.setOnEditorActionListener(actionListener);


        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        updateHistory();
    }

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
        }
        updateHistory();
    }

    private void updateHistory() {
        List<CharCode> values = dataSource.getAllHistory();
        // Specify an adapter
        mAdapter = new HistoryAdapter(values);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showDetail(View view) {
        CharCode charCode = (CharCode) view.getTag();
        FragmentManager fm = getFragmentManager();
        DialogFragment dialog = new DetailDialogFragment();
        Bundle args = new Bundle();
        args.putStringArray(CHARCODE_KEY, charCode.toStringArray());
        dialog.setArguments(args);
        dialog.show(fm, "1");
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
