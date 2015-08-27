package com.nethergrim.bashorg.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.web.GistParser;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author andrej on 25.08.15.
 */
public class AdsHelper {

    public static final String VK_MAIN_APP = "com.vkontakte.android";

    public interface AdsHelperCallback {

        void shouldShowStartADS(String show);
    }

    public static void showVkGroupAds(Context context) {
        if (!Constants.shouldDisplayVkAds()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ADS_VK_GROUP_URL));

        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(i, 0);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {

            String vkPackageName = getVkAppPackagename(activities);
            if (!TextUtils.isEmpty(vkPackageName)) {
                i.setPackage(vkPackageName);
            }

            context.startActivity(i);
        }
    }

    public static void shouldIShowStartADS(final AdsHelperCallback callback) {
        final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                String rawUrl = GistParser.getMyRawUrl(GistParser.SHOW_ADS_GIST_URL);
                final String gistBody = GistParser.getGistBody(rawUrl);
                if (gistBody != null) {
                    Prefs.setShowStartADS(gistBody);
                }
                if (callback != null) {
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.shouldShowStartADS(gistBody);
                        }
                    });
                }
            }
        });
    }

    public static boolean shouldShowStartADS(String rawGist) {
        if (rawGist == null) {
            return true;
        } else
            return "1".equals(rawGist);
    }

    public static String getDefaultStateForStartADS() {
        return "1";
    }

    private static String getVkAppPackagename(List<ResolveInfo> infos) {
        for (int i = 0, size = infos.size(); i < size; i++) {
            String packageName = getPackageName(infos.get(i));
            if (TextUtils.equals(VK_MAIN_APP, packageName)) {
                return VK_MAIN_APP;
            }
        }
        return null;
    }

    private static String getPackageName(ResolveInfo resolveInfo) {
        if (resolveInfo.activityInfo != null) {
            return resolveInfo.activityInfo.packageName;
        } else {
            return null;
        }
    }

}
