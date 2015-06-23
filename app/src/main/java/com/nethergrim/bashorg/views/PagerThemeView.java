package com.nethergrim.bashorg.views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.RelativeLayout;

import com.nethergrim.bashorg.R;

/**
 * @author andrej on 23.06.15.
 */
public class PagerThemeView extends RelativeLayout {

    private ThemeType mThemeType;

    public PagerThemeView(Context context, ThemeType themeType) {
        super(context);
        this.mThemeType = themeType;
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode()) {
            return;
        }
    }

    public enum ThemeType {
        LIGHT(R.drawable.theme_light), DARK(R.drawable.theme_dark);

        private int mImageResourceId;

        ThemeType(@DrawableRes int imageResouceid) {
            mImageResourceId = imageResouceid;
        }

        public int getImageResourceId() {
            return mImageResourceId;
        }
    }
}
