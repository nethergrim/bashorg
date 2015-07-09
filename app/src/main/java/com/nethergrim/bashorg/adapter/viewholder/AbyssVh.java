package com.nethergrim.bashorg.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.nethergrim.bashorg.R;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class AbyssVh extends RecyclerView.ViewHolder {

    @InjectView(R.id.text_body)
    public TextView mTVBody;

    @InjectView(R.id.text_date)
    public TextView mTVDate;

    @InjectView(R.id.text_id)
    public TextView mTVId;

    public AbyssVh(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
