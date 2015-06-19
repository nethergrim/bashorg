package com.nethergrim.bashorg.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.ThemeUtils;
import com.nethergrim.bashorg.Utils;
import com.nethergrim.bashorg.db.DB;
import com.nethergrim.bashorg.db.QuoteInflater;
import com.nethergrim.bashorg.model.Quote;

/**
 * @author andrey_drobyazko on 09.12.14 20:21.
 */
public class QuoteCursorAdapter extends CursorAdapter {

    private Drawable mDrawbleShareButton;
    private Drawable mDrawbleLikeButtonOff;
    private Drawable mDrawbleLikeButtonOn;

    public QuoteCursorAdapter(Context context) {
        super(context, null, 0);
        mDrawbleShareButton = Utils.tintIcon(R.drawable.ic_social_share, R.color.dark_theme_buttons_color);
        mDrawbleLikeButtonOff = Utils.tintIcon(R.drawable.ic_action_favorite_outline, R.color.dark_theme_buttons_color);
        mDrawbleLikeButtonOn = Utils.tintIcon(R.drawable.ic_action_favorite, R.color.dark_accent);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_quote, parent, false);
        QuoteViewHolder quoteViewHolder = new QuoteViewHolder(v);
        v.setTag(quoteViewHolder);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final QuoteViewHolder vh = (QuoteViewHolder) view.getTag();
        Quote q = new QuoteInflater().inflateEntityAtCurrentPosition(cursor);

        final String quoteText = q.getText();
        final long quoteId = q.getId();
        final boolean quoteIsLiked = !q.isLiked();

        vh.textId.setText("#" + String.valueOf(q.getId()));
        vh.textBody.setText(q.getText());
        vh.textDate.setText(q.getDate());
        vh.textRating.setText(String.valueOf(q.getRating()));
        setLikeIcon(vh.btnLike, q.isLiked());
        vh.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Constants.ACTION_SHARE_QUOTE);
                intent.putExtra(Constants.EXTRA_QUOTE, quoteText);
                context.sendBroadcast(intent);
            }
        });
        if (ThemeUtils.isADarkTheme()) {
            vh.btnShare.setImageDrawable(mDrawbleShareButton);
        }
        vh.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.getInstance().setQuoteLiked(quoteId, quoteIsLiked);
                setLikeIcon(vh.btnLike, quoteIsLiked);
            }
        });
        q.recycle();
    }

    private void setLikeIcon(ImageButton ib, boolean liked) {
        if (liked) {
            if (ThemeUtils.isADarkTheme()) {
                ib.setImageDrawable(mDrawbleLikeButtonOn);
            } else {
                ib.setImageResource(R.drawable.ic_action_favorite);
            }

        } else {
            if (ThemeUtils.isADarkTheme()) {
                ib.setImageDrawable(mDrawbleLikeButtonOff);
            } else {
                ib.setImageResource(R.drawable.ic_action_favorite_outline);
            }

        }
    }

    public static class QuoteViewHolder {

        public TextView textId;
        public TextView textDate;
        public TextView textBody;
        public ImageButton btnShare;
        public ImageButton btnLike;
        public TextView textRating;

        public QuoteViewHolder(View v) {
            textId = (TextView) v.findViewById(R.id.text_id);
            textBody = (TextView) v.findViewById(R.id.text_body);
            textDate = (TextView) v.findViewById(R.id.text_date);
            textRating = (TextView) v.findViewById(R.id.text_rating);
            btnShare = (ImageButton) v.findViewById(R.id.btn_share);
            btnLike = (ImageButton) v.findViewById(R.id.btn_like);
        }
    }
}
