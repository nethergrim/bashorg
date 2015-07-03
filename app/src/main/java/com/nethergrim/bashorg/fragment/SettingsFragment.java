package com.nethergrim.bashorg.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import com.nethergrim.bashorg.BuildConfig;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.activity.ThemeSelectorActivity;
import com.nethergrim.bashorg.db.DB;
import com.nethergrim.bashorg.fragment.dialogs.FontSizeSelectorDialog;
import com.nethergrim.bashorg.utils.ThemeUtils;
import com.nethergrim.bashorg.web.RunnerService;

/**
 * @author andrej on 29.06.15.
 */
public class SettingsFragment extends PreferenceFragment {

    private Preference prefsDownloadAll;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Preference prefsFontSize = findPreference("prefs_font_size");
        prefsFontSize.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                FontSizeSelectorDialog dialog = new FontSizeSelectorDialog();
                dialog.show(getFragmentManager(), dialog.getClass().getName());
                return true;
            }
        });


        Preference prefsTheme = findPreference("prefs_theme");
        prefsTheme.setSummary(ThemeUtils.getCurrentTheme().getStringThemeNameResourceId());
        prefsTheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ThemeSelectorActivity.start(getActivity());
                return true;
            }
        });


        prefsDownloadAll = findPreference("prefs_download_all_quotes");
        if (BuildConfig.DEBUG) {
            prefsDownloadAll.setSummary("count of quotes in db: " + DB.getInstance().getCountOfLoadedQuotes());
        }
        prefsDownloadAll.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                RunnerService.fetchAllBashorgData();
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    if (BuildConfig.DEBUG) {
                        prefsDownloadAll.setSummary("count of quotes in db: " + DB.getInstance().getCountOfLoadedQuotes());
                    }
                    mHandler.postDelayed(this, 50);
                }
            };
            mHandler.post(r);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
