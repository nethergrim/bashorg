package com.nethergrim.bashorg.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nethergrim on 01.12.2014.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titles;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public List<Fragment> getFragmentList() {
        if (fragmentList == null)
            fragmentList = new ArrayList<Fragment>();
        return fragmentList;
    }

    public List<String> getTitles() {
        if (titles == null)
            titles = new ArrayList<String>();
        return titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTitles().get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        getFragmentList().add(fragment);
        getTitles().add(title);
        notifyDataSetChanged();
    }

    public void addTitle(String title) {
        getTitles().add(title);
    }

    @Override
    public Fragment getItem(int i) {
        return getFragmentList().get(i);
    }

    @Override
    public int getCount() {
        return getFragmentList().size();
    }
}
