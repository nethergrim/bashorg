package com.nethergrim.bashorg.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;



/**
 * Created by nethergrim on 01.12.2014.
 */
public abstract class AbstractFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


}
