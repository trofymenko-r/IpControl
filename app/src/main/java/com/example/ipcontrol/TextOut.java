package com.example.ipcontrol;

import android.widget.TextView;

public class TextOut {
    private TextView mControl;

    public TextOut(TextView control) {
        mControl = control;
    }

    public void Out(String text, boolean newLine) {
        mControl.append(text);
        if (newLine)
            mControl.append(System.getProperty("line.separator"));
    }

    public void Out(String text) {
        Out(text, true);
    }

}
