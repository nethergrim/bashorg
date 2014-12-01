package com.nethergrim.bashorg.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import io.realm.Realm;

/**
 * Created by nethergrim on 01.12.2014.
 */
public abstract class AbstractFragment extends Fragment {

    protected Realm realm;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.realm = Realm.getInstance(activity.getApplicationContext());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.realm = null;
    }
}
