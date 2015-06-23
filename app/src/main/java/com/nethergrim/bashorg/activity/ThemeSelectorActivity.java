package com.nethergrim.bashorg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.nethergrim.bashorg.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author andrej on 22.06.15.
 */
public class ThemeSelectorActivity extends FragmentActivity implements View.OnClickListener {

    @InjectView(R.id.pager)
    ViewPager mPager;

    @InjectView(R.id.button)
    Button mButton;


    public static void start(Context context) {
        context.startActivity(new Intent(context, ThemeSelectorActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selector);
        ButterKnife.inject(this);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
