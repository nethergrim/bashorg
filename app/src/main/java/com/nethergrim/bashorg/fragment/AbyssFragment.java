package com.nethergrim.bashorg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.adapter.AbyssAdapter;
import com.nethergrim.bashorg.utils.RecyclerviewPageScroller;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class AbyssFragment extends AbstractFragment implements RecyclerviewPageScroller.OnRecyclerScrolledToEndCallback {

    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    private AbyssAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_abyss, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(view);
        mAdapter = new AbyssAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnScrollListener(new RecyclerviewPageScroller(3, this));
    }

    @Override
    public void onRecyclerScrolledToEnd() {
        // TODO load next page
    }
}
