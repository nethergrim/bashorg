package com.nethergrim.bashorg.utils;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.nethergrim.bashorg.App;

/**
 * @author andrej on 19.06.15.
 */
public class Utils {

    public static Drawable tintIcon(@DrawableRes int iconResId, @ColorRes int color) {
        color = App.getInstance().getResources().getColor(color);
        Drawable drawable = App.getInstance().getResources().getDrawable(iconResId);


        int red = (color & 0xFF0000) / 0xFFFF;
        int green = (color & 0xFF00) / 0xFF;
        int blue = color & 0xFF;

        float[] matrix = {0, 0, 0, 0, red
                , 0, 0, 0, 0, green
                , 0, 0, 0, 0, blue
                , 0, 0, 0, 1, 0};

        ColorFilter colorFilter = new ColorMatrixColorFilter(matrix);

        drawable.setColorFilter(colorFilter);

        return drawable;
    }

    public static int getColorByResId(@ColorRes int colorResId) {
        return App.getInstance().getResources().getColor(colorResId);
    }
}
