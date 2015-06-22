package com.nethergrim.bashorg.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.R;
import com.nethergrim.bashorg.ThemeUtils;
import com.nethergrim.bashorg.Utils;
import com.nethergrim.bashorg.db.DB;
import com.nethergrim.bashorg.model.Quote;

/**
 * @author andrej on 21.06.15.
 */
public class QuoteViewHolder {

    public TextView textId;
    public TextView textDate;
    public TextView textBody;
    public ImageButton btnShare;
    public ImageButton btnLike;
    public TextView textRating;

    private Drawable mDrawbleShareButton;
    private Drawable mDrawbleLikeButtonOff;
    private Drawable mDrawbleLikeButtonOn;

    public QuoteViewHolder(View v) {
        textId = (TextView) v.findViewById(R.id.text_id);
        textBody = (TextView) v.findViewById(R.id.text_body);
        textDate = (TextView) v.findViewById(R.id.text_date);
        textRating = (TextView) v.findViewById(R.id.text_rating);
        btnShare = (ImageButton) v.findViewById(R.id.btn_share);
        btnLike = (ImageButton) v.findViewById(R.id.btn_like);

        if (ThemeUtils.isADarkTheme()) {
            mDrawbleShareButton = Utils.tintIcon(R.drawable.ic_social_share, R.color.dark_theme_buttons_color);
            mDrawbleLikeButtonOff = Utils.tintIcon(R.drawable.ic_action_favorite_outline, R.color.dark_theme_buttons_color);
            mDrawbleLikeButtonOn = Utils.tintIcon(R.drawable.ic_action_favorite, R.color.dark_accent);
        } else {
            mDrawbleShareButton = Utils.tintIcon(R.drawable.ic_social_share, R.color.main_color);
            mDrawbleLikeButtonOff = Utils.tintIcon(R.drawable.ic_action_favorite_outline, R.color.accent);
            mDrawbleLikeButtonOn = Utils.tintIcon(R.drawable.ic_action_favorite, R.color.accent);
        }

    }

    public void bindQuouteData(Quote q, final Context context) {
        final String quoteText = q.getText();
        final long quoteId = q.getId();
        final boolean quoteIsLiked = !q.isLiked();
        textId.setText("#" + String.valueOf(q.getId()));
        textBody.setText(q.getText());
        textDate.setText(q.getDate());
        textRating.setText(String.valueOf(q.getRating()));
        setLikeIcon(btnLike, q.isLiked());
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Constants.ACTION_SHARE_QUOTE);
                intent.putExtra(Constants.EXTRA_QUOTE, quoteText);
                context.sendBroadcast(intent);
            }
        });
        if (ThemeUtils.isADarkTheme()) {
            btnShare.setImageDrawable(mDrawbleShareButton);
        }
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.getInstance().setQuoteLiked(quoteId, quoteIsLiked);
                setLikeIcon(btnLike, quoteIsLiked);
            }
        });
        q.recycle();
        changeTextSize(ThemeUtils.getFontSize());
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

    public void changeTextSize(int textSizeSP) {
        if (textBody != null) {
            textBody.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSP);
        }
    }
}
