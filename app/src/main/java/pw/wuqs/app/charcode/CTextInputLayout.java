package pw.wuqs.app.charcode;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Custom TextInputLayout with baseline support
 */

public class CTextInputLayout extends TextInputLayout {
    public CTextInputLayout(Context context) {
        super(context);
    }

    public CTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getBaseline()
    {
        EditText editText = getEditText();
        return editText.getPaddingBottom() + editText.getBaseline();
    }
}