package pw.wuqs.app.charcode;

import android.text.TextUtils;

/**
 * CharCode class
 * Class of a search item
 */

public class CharCode {
    private int unicode;
    private char ch;
    private String charStr;

    public CharCode(int unicode) {
        this.unicode = unicode;
        this.ch = Character.toChars(unicode)[0];
        this.ch = (char) unicode;
    }

    public CharCode(char ch) {
        this.ch = ch;
        this.unicode = (int) ch;
    }

    public CharCode(String str) {
        char[] chars = str.toCharArray();
        this.unicode = Character.codePointAt(chars, 0);
    }

    public int getUnicode() {
        return unicode;
    }

    public char getCh() {
        return ch;
    }

    public String getDecUnicodeStr() {
        return Integer.toString(unicode);
    }

    public String getHexUnicodeStr() {
        return Integer.toHexString(unicode);
    }

    public String getCharStr() {
        return new String(Character.toChars(unicode));
    }

    public int[] getSurrogatePair() {
        int tempUni = unicode - 0x10000;
        int top10 = (tempUni >> 10) + 0xD800;
        int low10 = (tempUni & 0x3FF) + 0xDC00;
        return new int[]{top10, low10};
    }

    public String getHexSurrogatePairStr() {
        String[] strings = new String[getSurrogatePair().length];
        for (int i=0;i<getSurrogatePair().length;i++) {
            strings[i] = Integer.toHexString(getSurrogatePair()[i]);
        }
        return TextUtils.join(" ", strings);
    }

    public boolean isSurrogatePair() {
        if (unicode < 0x10000) {
            return false;
        }
        return true;
    }

    public String[] toStringArray() {
        return new String[]{getCharStr(), getDecUnicodeStr(), getHexUnicodeStr()};
    }
}
