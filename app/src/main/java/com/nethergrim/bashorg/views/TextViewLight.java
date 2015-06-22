package com.nethergrim.bashorg.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nethergrim.bashorg.Constants;

/**
 * Created by andrey_drobyazko on 02.12.14 19:31.
 */

public class TextViewLight extends TextView {

    public TextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public TextViewLight(Context context) {
        super(context);
        init();
    }

    public void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Constants.ROBOTO_LIGHT);
            setTypeface(tf);
        }
    }

}
