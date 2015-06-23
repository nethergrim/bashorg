package com.nethergrim.bashorg.views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nethergrim.bashorg.R;

/**
 * @author andrej on 23.06.15.
 */
public class PagerThemeView extends RelativeLayout {

    private ThemeType mThemeType;
    private ImageView mImageView;

    public PagerThemeView(Context context, ThemeType themeType) {
        super(context);
        this.mThemeType = themeType;
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode()) {
            return;
        }
        mImageView = new ImageView(context);
        addView(mImageView);
        RelativeLayout.LayoutParams params = (LayoutParams) mImageView.getLayoutParams();
        params.height = LayoutParams.MATCH_PARENT;
        params.width = LayoutParams.MATCH_PARENT;
        params.addRule(CENTER_IN_PARENT);
        mImageView.setLayoutParams(params);
        mImageView.setImageResource(mThemeType.getImageResourceId());
        mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public enum ThemeType {
        LIGHT(R.drawable.light_theme_small), DARK(R.drawable.dark_theme_small);

        private int mImageResourceId;

        ThemeType(@DrawableRes int imageResouceid) {
            mImageResourceId = imageResouceid;
        }

        public int getImageResourceId() {
            return mImageResourceId;
        }
    }
}
