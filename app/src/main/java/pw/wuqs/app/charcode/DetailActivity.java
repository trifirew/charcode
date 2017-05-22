package pw.wuqs.app.charcode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private CharCode charCode;
    private TextView chTV;
    private TextView decUnicodeTV;
    private TextView hexUnicodeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int unicode = intent.getIntExtra(MainActivity.CHARCODE_KEY, 0);
        charCode = new CharCode(unicode);

        chTV = (TextView) findViewById(R.id.chUnicodeTV);
        decUnicodeTV = (TextView) findViewById(R.id.decUnicodeTV);
        hexUnicodeTV = (TextView) findViewById(R.id.hexUnicodeTV);
        chTV.setText(charCode.getCharStr());
        decUnicodeTV.setText(charCode.getDecUnicodeStr());
        hexUnicodeTV.setText(charCode.getHexUnicodeStr());

        // Surrogate pair view
        TextView surrogateTV = (TextView) findViewById(R.id.surrogateTV);
        TextView surrLabel = (TextView) findViewById(R.id.surrLabel);
        ImageButton cpSurrBtn = (ImageButton) findViewById(R.id.cpSurrBtn);

        if (charCode.isSurrogatePair()) {
            surrLabel.setVisibility(View.VISIBLE);
            surrogateTV.setVisibility(View.VISIBLE);
            cpSurrBtn.setVisibility(View.VISIBLE);
            surrogateTV.setText(charCode.getHexSurrogatePairStr());
        } else {
            surrLabel.setVisibility(View.GONE);
            surrogateTV.setVisibility(View.GONE);
            cpSurrBtn.setVisibility(View.GONE);
        }

    }

    public void copy(View view) {
        String clipString;
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);
        switch (view.getId()) {
            case R.id.cpCharBtn:
                clipString = charCode.getCharStr();
                break;
            case R.id.cpDecBtn:
                clipString = charCode.getDecUnicodeStr();
                break;
            case R.id.cpHexBtn:
                clipString = charCode.getHexUnicodeStr();
                break;
            case R.id.cpSurrBtn:
                clipString = charCode.getHexSurrogatePairStr();
                break;
            default:
                clipString = null;
                break;
        }
        if (clipString != null) {
            ClipData clip = ClipData.newPlainText("simple text", clipString);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, getString(R.string.toast_copied) + clipString, Toast.LENGTH_SHORT).show();
        }
    }
}
