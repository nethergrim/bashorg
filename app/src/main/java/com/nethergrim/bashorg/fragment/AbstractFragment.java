package com.nethergrim.bashorg.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nethergrim.bashorg.callback.OnTopBarHeightListener;


/**
 * Created by nethergrim on 01.12.2014.
 */
public abstract class AbstractFragment extends Fragment {

    protected OnTopBarHeightListener onTopBarHeightListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.onTopBarHeightListener = (OnTopBarHeightListener) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onTopBarHeightListener = null;
    }
}
