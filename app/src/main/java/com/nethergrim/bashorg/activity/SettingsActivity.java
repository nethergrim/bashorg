package com.nethergrim.bashorg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.fragment.SettingsFragment;
import com.nethergrim.bashorg.utils.ThemeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author andrej on 20.06.15.
 */
public class SettingsActivity extends AppCompatActivity {


    @InjectView(R.id.btn_back)
    ImageButton mButtonBack;
    @InjectView(R.id.action_bar)
    View mActionBar;
    @InjectView(R.id.container)
    FrameLayout mContainer;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

//    @Override
//    public void onRestoreInstanceState(Bundle outState) {
//        super.onRestoreInstanceState(outState);
//
//        if (null != this.mSADView)
//            this.mSADView.restoreInstanceState(outState);
//    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        if (null != this.mSadContainer)
//            this.mSADView.saveInstanceState(outState);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeUtils.getCurrentTheme().getStyleResourceId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
        initActionBar();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SettingsFragment())
                .commit();
    }

//    @Override
//    protected void onDestroy() {
//        if (mSadContainer != null) {
//            mSADView.destroy();
//        }
//        super.onDestroy();
//    }
//
//    private void initAds() {
//        this.mSADView = new SADView(this, Constants.START_AD_APP_ID);
//        mSadContainer.addView(mSADView);
//        mSADView.loadAd(SADView.LANGUAGE_RU);
//    }

    private void initActionBar() {
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
