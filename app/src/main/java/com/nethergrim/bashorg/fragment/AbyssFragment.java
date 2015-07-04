package com.nethergrim.bashorg.fragment;

import android.content.Context;
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
import com.nethergrim.bashorg.web.AbyssParser;
import com.nethergrim.bashorg.web.MyIntentService;
import io.realm.Realm;
import io.realm.RealmChangeListener;


/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class AbyssFragment extends AbstractFragment implements RecyclerviewPageScroller.OnRecyclerScrolledToEndCallback, RealmChangeListener {

    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    private AbyssAdapter mAdapter;
    private Realm realm;

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
        MyIntentService.getAbyssPage(AbyssParser.FIRST_PAGE, view.getContext());
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
        realm.addChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (realm != null) {
            realm.close();
        }
    }

    @Override
    public void onRecyclerScrolledToEnd() {
        String nextPage = mAdapter.getNextPageFromLast();
        Context context = getActivity();
        if (nextPage != null && context != null) {
            MyIntentService.getAbyssPage(nextPage, context);
        }
    }

    @Override
    public void onChange() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
}