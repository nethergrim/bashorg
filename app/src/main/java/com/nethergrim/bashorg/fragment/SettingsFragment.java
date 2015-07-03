package com.nethergrim.bashorg.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.activity.ThemeSelectorActivity;
import com.nethergrim.bashorg.fragment.dialogs.FontSizeSelectorDialog;
import com.nethergrim.bashorg.utils.ThemeUtils;

/**
 * @author andrej on 29.06.15.
 */
public class SettingsFragment extends PreferenceFragment {

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
    }
}
