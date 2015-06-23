package com.nethergrim.bashorg.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.nethergrim.bashorg.views.PagerThemeView;

/**
 * @author andrej on 23.06.15.
 */
public class ThemePagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return PagerThemeView.ThemeType.values().length;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        return new PagerThemeView(container.getContext(), PagerThemeView.ThemeType.values()[position]);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
