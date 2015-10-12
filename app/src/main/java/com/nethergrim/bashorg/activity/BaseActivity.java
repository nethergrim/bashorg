package com.nethergrim.bashorg.activity;

import android.support.v4.app.FragmentActivity;

import com.yandex.metrica.YandexMetrica;

/**
 * @author Andrew Drobyazko (andrey.drobyazko@applikeysolutions.com) on 12.10.15.
 */
public class BaseActivity extends FragmentActivity {


    @Override
    protected void onResume() {
        super.onResume();
        YandexMetrica.onResumeActivity(this);
    }

    @Override
    protected void onPause() {
        YandexMetrica.onPauseActivity(this);
        super.onPause();
    }
}
