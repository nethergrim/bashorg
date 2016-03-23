package com.nethergrim.bashorg.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.kenny.snackbar.SnackBarItem;
import com.kenny.snackbar.SnackBarListener;
import com.mopub.mobileads.MoPubView;
import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.FragmentAdapter;
import com.nethergrim.bashorg.fragment.AbyssFragment;
import com.nethergrim.bashorg.fragment.BestQuotesFragment;
import com.nethergrim.bashorg.fragment.LastQuotesFragment;
import com.nethergrim.bashorg.fragment.LikedQuotesFragment;
import com.nethergrim.bashorg.fragment.RandomQuotesFragment;
import com.nethergrim.bashorg.utils.AdsHelper;
import com.nethergrim.bashorg.utils.Prefs;
import com.nethergrim.bashorg.utils.ThemeUtils;
import com.startad.lib.SADView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity
        implements TabLayout.OnTabSelectedListener, View.OnClickListener, SnackBarListener {

    @InjectView(R.id.tabs)
    TabLayout mTabs;
    @InjectView(R.id.pager)
    ViewPager mPager;
    @InjectView(R.id.shadow)
    View mShadow;
    @InjectView(R.id.fab)
    FloatingActionButton mFab;
    @InjectView(R.id.mopub_sample_ad)
    MoPubView mMopubSampleAd;

    private ViewPager pager;
    private FragmentAdapter adapter;
    private TabLayout tabs;
    private IntentFilter filter = new IntentFilter(Constants.ACTION_SHARE_QUOTE);
    private BroadcastReceiver receiver;
    private SADView mSADView;

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Integer id = (Integer) tab.getTag();
        pager.setCurrentItem(id, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onSnackBarStarted(Object o) {
        mFab.animate().translationY(-48 * Constants.density).setDuration(250).start();
        if (mSADView != null) {

            mSADView = null;
        }
    }

    @Override
    public void onSnackBarFinished(Object o, boolean b) {
        mFab.animate().translationY(0).setDuration(250).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeUtils.getCurrentTheme().getStyleResourceId());
        super.onCreate(savedInstanceState);
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        Prefs.incrementLaunchCount();
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new FragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(6);
        tabs = (TabLayout) findViewById(R.id.tabs);
        mFab.setOnClickListener(this);
        loadFragments();
        initTabs();
        mMopubSampleAd.setAdUnitId("ad82cd50469342dc870c7e94e3336302");
        mMopubSampleAd.loadAd();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMopubSampleAd.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showSnackBar();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String quote = intent.getStringExtra(Constants.EXTRA_QUOTE);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, quote);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        };
        registerReceiver(receiver, filter);

    }

    private void showSnackBar() {
        mFab.animate().translationY(0).setDuration(250).start();
        if (!Prefs.isSnackBarDisabled()) {
            new SnackBarItem.Builder(this)
                    .setMessageResource(R.string.our_vk_group)
                    .setActionMessageResource(R.string.go)
                    .setActionClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AdsHelper.showVkGroupAds(v.getContext());
                            Prefs.setSnackBarDisabled(true);
                        }
                    })
                    .setActionMessageColorResource(R.color.accent)
                    .setSnackBarMessageColorResource(android.R.color.white)
                    .setInterpolatorResource(android.R.interpolator.accelerate_decelerate)
                    .setDuration(5000)
                    .setSnackBarListener(this)
                    .show();
        }
    }

    private void initTabs() {
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        if (Build.VERSION.SDK_INT >= 21) {
            tabs.setTranslationZ(8 * Constants.density);
        } else {
            findViewById(R.id.shadow).setVisibility(View.VISIBLE);
        }
    }

    private void loadFragments() {
        tabs.setTabTextColors(getResources().getColor(R.color.white_alpha_50), Color.WHITE);
        adapter.addFragment(new LastQuotesFragment(), getString(R.string.last));
        adapter.addFragment(new RandomQuotesFragment(), getString(R.string.random));
        adapter.addFragment(new BestQuotesFragment(), getString(R.string.best));
        adapter.addFragment(new LikedQuotesFragment(), getString(R.string.liked));
        adapter.addFragment(new AbyssFragment(), getString(R.string.best_of_abyss));
        tabs.addTab(tabs.newTab().setText(R.string.last).setTag(Integer.valueOf(0)));
        tabs.addTab(tabs.newTab().setText(R.string.random).setTag(Integer.valueOf(1)));
        tabs.addTab(tabs.newTab().setText(R.string.best).setTag(Integer.valueOf(2)));
        tabs.addTab(tabs.newTab().setText(R.string.liked).setTag(Integer.valueOf(3)));
        tabs.addTab(tabs.newTab().setText(R.string.best_of_abyss).setTag(Integer.valueOf(4)));
        tabs.setOnTabSelectedListener(this);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

    @Override
    public void onClick(View view) {
        SettingsActivity.start(this);
    }
}
