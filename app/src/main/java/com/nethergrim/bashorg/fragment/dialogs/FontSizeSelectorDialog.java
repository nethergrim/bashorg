package com.nethergrim.bashorg.fragment.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.utils.ThemeUtils;
import com.nethergrim.bashorg.views.FontSettingsController;

/**
 * @author andrej on 30.06.15.
 */
public class FontSizeSelectorDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder b = new MaterialDialog.Builder(getActivity())
                .title(R.string.font_size)
                .positiveText(android.R.string.ok)
                .titleColorAttr(R.attr.myColorPrimaryColor)
                .customView(new FontSettingsController(getActivity()), false);
        switch (ThemeUtils.getCurrentTheme()) {
            case DARK:
                b.backgroundColorAttr(R.attr.myColorWindowBackground);
                break;
            case LIGHT:
                b.backgroundColorRes(android.R.color.white);
                break;
        }
        return b.build();
    }
}
