package com.nethergrim.bashorg.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.nethergrim.bashorg.callback.OnTopBarHeightListener;
import com.nethergrim.bashorg.fragment.BestQuotesFragment;
import com.nethergrim.bashorg.fragment.LastQuotesFragment;
import com.nethergrim.bashorg.fragment.LikedQuotesFragment;
import com.nethergrim.bashorg.fragment.RandomQuotesFragment;
import com.nethergrim.bashorg.model.Quote;
import com.nethergrim.bashorg.web.RunnerService;

public class MainActivity extends FragmentActivity implements OnTopBarHeightListener {

    private ViewPager pager;
    private FragmentAdapter adapter;
    private PagerSlidingTabStrip tabs;
    private IntentFilter filter = new IntentFilter(Constants.ACTION_SHARE_QUOTE);
    private BroadcastReceiver receiver;
    private int maxHeight;
    private boolean collapsed = false;
    private boolean animating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maxHeight = (int) (getResources().getDimension(R.dimen.top_bar_opened));
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new FragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(6);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        loadFragments();
        initTabs();
        RunnerService.start(this.getApplicationContext());
    }

    private void initTabs() {
        tabs.setIndicatorHeight((int) (2 * Constants.density));
        tabs.setIndicatorColor(getResources().getColor(R.color.accent));
        tabs.setBackgroundColor(getResources().getColor(R.color.main_color));
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
        adapter.addFragment(new LikedQuotesFragment(), getString(R.string.liked));
        tabs.setViewPager(pager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Quote quote = (Quote) intent.getSerializableExtra(Constants.EXTRA_QUOTE);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, quote.getText());
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
    public void onTopBarHeightChanged(boolean scrollDown) {
        if (!animating){
            ValueAnimator animator = null;
            if (!collapsed && scrollDown){
                //scroliing down, collapsing
                animator = ValueAnimator.ofFloat(1f, 0f);
            } else if (collapsed && !scrollDown){
                //scrolling up, appearing
                animator = ValueAnimator.ofFloat(0f, 1f);
            }
            if (animator == null) return;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    changeTabsHeight((Float) animation.getAnimatedValue());
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    animating = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animating = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }

    private void changeTabsHeight(float value){
        float newHeight = value * maxHeight;
        tabs.getLayoutParams().height = (int) newHeight;
        tabs.requestLayout();
        collapsed = value <= 0.99;
    }
}
