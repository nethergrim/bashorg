package com.nethergrim.bashorg.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nethergrim.bashorg.Constants;

/**
 * Created by andrey_drobyazko on 02.12.14 19:32.
 */
public class TextViewRegular extends TextView {

    public TextViewRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public TextViewRegular(Context context) {
        super(context);
        init();
    }

    public void init() {
        if (!isInEditMode()){
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Constants.ROBOTO_REGULAR);
            setTypeface(tf ,1);
        }
    }

}

