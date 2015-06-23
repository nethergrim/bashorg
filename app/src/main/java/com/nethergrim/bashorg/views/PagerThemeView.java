package com.nethergrim.bashorg.views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nethergrim.bashorg.utils.ThemeType;

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
        ImageView mImageView = new ImageView(context);
        addView(mImageView);
        RelativeLayout.LayoutParams params = (LayoutParams) mImageView.getLayoutParams();
        params.height = LayoutParams.MATCH_PARENT;
        params.width = LayoutParams.MATCH_PARENT;
        params.addRule(CENTER_IN_PARENT);
        mImageView.setLayoutParams(params);
        mImageView.setImageResource(mThemeType.getImageResourceId());
        mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }


}
