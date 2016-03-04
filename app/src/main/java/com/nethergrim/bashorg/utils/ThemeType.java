package com.nethergrim.bashorg.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

import com.nethergrim.bashorg.R;

/**
 * @author andrej on 23.06.15.
 */
public enum ThemeType {
    DARK(R.drawable.dark_theme_small, 0, R.style.My_Theme_Dark, R.string.dark_theme),
    LIGHT(R.drawable.light_theme_small, 1, R.style.My_Theme_Light, R.string.light_theme),
    BLACK(R.drawable.black_theme_small, 2, R.style.My_Theme_Black, R.string.black_theme);

    private int mImageResourceId;
    private int mCode;
    private int mStyleResourceId;
    private int mStringThemeNameResourceId;

    ThemeType(@DrawableRes int imageResouceid, int code, @StyleRes int style, @StringRes int stringNameResourceId) {
        mImageResourceId = imageResouceid;
        mCode = code;
        mStyleResourceId = style;
        mStringThemeNameResourceId = stringNameResourceId;
    }

    public static ThemeType getTypeForCode(int code) throws IllegalArgumentException {
        ThemeType[] values = ThemeType.values();
        for (ThemeType value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("no theme with code: " + code);
    }

    @DrawableRes
    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getCode() {
        return mCode;
    }

    @StyleRes
    public int getStyleResourceId() {
        return mStyleResourceId;
    }

    public int getStringThemeNameResourceId() {
        return mStringThemeNameResourceId;
    }
}