package com.nethergrim.bashorg.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import com.github.clans.fab.FloatingActionButton;
import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.FragmentAdapter;
import com.nethergrim.bashorg.fragment.BestQuotesFragment;
import com.nethergrim.bashorg.fragment.LastQuotesFragment;
import com.nethergrim.bashorg.fragment.LikedQuotesFragment;
import com.nethergrim.bashorg.fragment.RandomQuotesFragment;
import com.nethergrim.bashorg.utils.FileUtils;
import com.nethergrim.bashorg.utils.ThemeUtils;


public class MainActivity extends FragmentActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private ViewPager pager;
    private FragmentAdapter adapter;
    private TabLayout tabs;
    private IntentFilter filter = new IntentFilter(Constants.ACTION_SHARE_QUOTE);
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeUtils.getCurrentTheme().getStyleResourceId());
        super.onCreate(savedInstanceState);
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new FragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(6);
        tabs = (TabLayout) findViewById(R.id.tabs);
        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        loadFragments();
        initTabs();
        decompressZipFileAndPersistToDb();
    }

    private void decompressZipFileAndPersistToDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                boolean success = FileUtils.unpackAssetFileAndUnzip(FileUtils.BASHORG_JSON_FILE_NAME + FileUtils.ZIP_FILE_POSTFIX);
                Log.e("TAG", "unpacked zip in: " + String.valueOf(System.currentTimeMillis() - start) + " success: " + success);
            }
        }).start();
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
        tabs.addTab(tabs.newTab().setText(R.string.last).setTag(Integer.valueOf(0)));
        tabs.addTab(tabs.newTab().setText(R.string.random).setTag(Integer.valueOf(1)));
        tabs.addTab(tabs.newTab().setText(R.string.best).setTag(Integer.valueOf(2)));
        tabs.addTab(tabs.newTab().setText(R.string.liked).setTag(Integer.valueOf(3)));
        tabs.setOnTabSelectedListener(this);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

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
    public void onClick(View view) {
        SettingsActivity.start(this);
    }
}
