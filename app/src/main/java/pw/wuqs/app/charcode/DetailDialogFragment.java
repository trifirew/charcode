package pw.wuqs.app.charcode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Qishen Wu on 2017/5/20.
 */

public class DetailDialogFragment extends DialogFragment {

    TextView labelDec;
    TextView labelHex;
    TextView decUnicodeTV;
    TextView hexUnicodeTV;
    TextView charTV;
    Button copyChar;
    Button copyDec;
    Button copyHex;
    Bundle args;
    String[] strings;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View context = inflater.inflate(R.layout.dialog_detail, null);
        builder.setView(context);
        labelDec = (TextView) context.findViewById(R.id.labelDecUnicodeTV);
        labelHex = (TextView) context.findViewById(R.id.labelHexUnicodeTV);
        labelDec.setText(R.string.edit_unicode);
        labelHex.setText(R.string.edit_hex_unicode);

        args = getArguments();
        strings = args.getStringArray(MainActivity.CHARCODE_KEY);
        decUnicodeTV = (TextView) context.findViewById(R.id.detailDecUnicodeTV);
        hexUnicodeTV = (TextView) context.findViewById(R.id.detailHexUnicodeTV);
        charTV = (TextView) context.findViewById(R.id.detailCharTextView);

        decUnicodeTV.setText(strings[1]);
        hexUnicodeTV.setText(strings[2]);
        charTV.setText(strings[0]);

        copyChar = (Button) context.findViewById(R.id.detailCopyCharBtn);
        copyDec = (Button) context.findViewById(R.id.detailCopyDecBtn);
        copyHex = (Button) context.findViewById(R.id.detailCopyHexBtn);

        return builder.create();
    }
}
