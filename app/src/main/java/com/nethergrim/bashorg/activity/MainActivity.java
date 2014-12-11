package com.nethergrim.bashorg.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.FragmentAdapter;
import com.nethergrim.bashorg.fragment.BestQuotesFragment;
import com.nethergrim.bashorg.fragment.LastQuotesFragment;
import com.nethergrim.bashorg.fragment.RandomQuotesFragment;
import com.nethergrim.bashorg.web.MyIntentService;

public class MainActivity extends FragmentActivity {

    private ViewPager pager;
    private FragmentAdapter adapter;
    private PagerSlidingTabStrip tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new FragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(6);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        loadFragments();
        initTabs();
    }

    private void initTabs() {
        tabs.setIndicatorHeight((int) (2 * Constants.density));
        tabs.setIndicatorColor(getResources().getColor(R.color.pink_a200));
        tabs.setBackgroundColor(getResources().getColor(R.color.dark_purple_a200));
        tabs.setTextColor(Color.WHITE);
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setUnderlineColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= 21) {
            tabs.setTranslationZ(8 * Constants.density);
        } else {
            findViewById(R.id.shadow).setVisibility(View.VISIBLE);
        }
    }

    private void loadFragments() {
        adapter.addFragment(new LastQuotesFragment(), getString(R.string.last));
        adapter.addFragment(new RandomQuotesFragment(), getString(R.string.random));
        adapter.addFragment(new BestQuotesFragment(), getString(R.string.best));
        // TODO here add another pages
        tabs.setViewPager(pager);
        MyIntentService.getPageAndSaveQuotes(this, 100000);
    }


}
