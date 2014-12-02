package com.nethergrim.bashorg.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nethergrim.bashorg.Constants;

/**
 * Created by andrey_drobyazko on 02.12.14 19:31.
 */

public class TextViewThin extends TextView {

    public TextViewThin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewThin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public TextViewThin(Context context) {
        super(context);
        init();
    }

    public void init() {
        if (!isInEditMode()){
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + Constants.ROBOTO_THIN);
            setTypeface(tf ,1);
        }
    }

}
