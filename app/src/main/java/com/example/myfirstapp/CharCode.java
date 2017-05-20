package com.example.myfirstapp;

/**
 * Created by Qishen Wu on 2017-05-19.
 */

public class CharCode {
    private int unicode;
    private char ch;
    private String decUnicodeStr;
    private String hexUnicodeStr;
    private String charStr;

    public CharCode(int unicode) {
        this.unicode = unicode;
        this.ch = (char) unicode;
        this.decUnicodeStr = Integer.toString(unicode);
        this.hexUnicodeStr = Integer.toHexString(unicode);
        this.charStr = Character.toString(ch);
    }

    public CharCode(char ch) {
        this.ch = ch;
        this.unicode = (int) ch;
        this.decUnicodeStr = Integer.toString(unicode);
        this.hexUnicodeStr = Integer.toHexString(unicode);
        this.charStr = Character.toString(ch);
    }

    public int getUnicode() {
        return unicode;
    }

    public char getCh() {
        return ch;
    }

    public String getDecUnicodeStr() {
        return decUnicodeStr;
    }

    public String getHexUnicodeStr() {
        return hexUnicodeStr;
    }

    public String getCharStr() {
        return charStr;
    }
}
