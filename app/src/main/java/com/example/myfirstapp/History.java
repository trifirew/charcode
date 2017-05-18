package com.example.myfirstapp;

/**
 * Created by Qishen Wu on 2017-05-16.
 */

public class History {
    private long id;
    private int ascii;
    private String ch;

    public long getId() {
        return id;
    }

    public int getAscii() {
        return ascii;
    }

    public String getCh() {
        return ch;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAscii(int ascii) {
        this.ascii = ascii;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    @Override
    public String toString() {
        return Integer.toString(ascii) + ": " + ch;
    }
}
