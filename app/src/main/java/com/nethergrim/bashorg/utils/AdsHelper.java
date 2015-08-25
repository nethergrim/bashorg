package com.nethergrim.bashorg.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.nethergrim.bashorg.Constants;

/**
 * @author andrej on 25.08.15.
 */
public class AdsHelper {

    public static void showVkGroupAds(Context context) {
        if (!Constants.shouldDisplayVkAds()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ADS_VK_GROUP_URL));
        context.startActivity(i);
    }

}
