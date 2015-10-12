package com.nethergrim.bashorg.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;

import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.ThemePagerAdapter;
import com.nethergrim.bashorg.purchases.InAppBillingServiceHolder;
import com.nethergrim.bashorg.purchases.PurchasesUtils;
import com.nethergrim.bashorg.utils.ThemeType;
import com.nethergrim.bashorg.utils.ThemeUtils;
import com.yandex.metrica.YandexMetrica;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author andrej on 22.06.15.
 */
public class ThemeSelectorActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final int REQUEST_BUY_THEME = 1212;
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
        mPager.setAdapter(new ThemePagerAdapter());
        mPager.addOnPageChangeListener(this);
        onPageSelected(0);
    }

    @Override
    public void onClick(View v) {
        ThemeType mSelectedTheme = ThemeType.values()[mPager.getCurrentItem()];
        if (ThemeUtils.isThemeBought(mSelectedTheme)) {
            ThemeUtils.setCurrentTheme(mSelectedTheme);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            YandexMetrica.reportEvent("Trying to buy " + mSelectedTheme.name() + " theme.");
            try {
                startIntentSenderForResult(PurchasesUtils.getBuyIntentSender(PurchasesUtils.ID_DARK_THEME), REQUEST_BUY_THEME, new Intent(), 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_BUY_THEME) {
//            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
//            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    if (InAppBillingServiceHolder.mBoughtSkus == null) {
                        InAppBillingServiceHolder.mBoughtSkus = new ArrayList<>();
                    }
                    InAppBillingServiceHolder.mBoughtSkus.add(sku);
                    onClick(null);
                    YandexMetrica.reportEvent("Successful purchase.");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        ThemeType currentType = ThemePagerAdapter.getTypeForPage(position);
        boolean isEnabledNow = ThemeUtils.isThemeEnabledNow(currentType);
        if (isEnabledNow) {
            mButton.animate().alpha(0f).scaleY(0f).scaleX(0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mButton.setVisibility(View.INVISIBLE);
                }
            }).setInterpolator(new AnticipateOvershootInterpolator()).start();
        } else {
            mButton.animate().alpha(1f).scaleY(1f).scaleX(1f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mButton.setVisibility(View.VISIBLE);
                }
            }).setInterpolator(new AnticipateOvershootInterpolator()).start();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
