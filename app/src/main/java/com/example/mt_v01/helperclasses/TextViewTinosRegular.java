package com.example.mt_v01.helperclasses;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewTinosRegular extends TextView {
    public TextViewTinosRegular(Context context) {
        super(context);
        setFont();
    }
    public TextViewTinosRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public TextViewTinosRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}