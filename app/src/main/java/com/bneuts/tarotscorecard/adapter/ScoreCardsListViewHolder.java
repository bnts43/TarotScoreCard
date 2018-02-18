package com.bneuts.tarotscorecard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bneuts.tarotscorecard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bneut on 09/02/2018.
 */

public class ScoreCardsListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.scorecard_item_list_tv)
    public TextView cardName;

    public ScoreCardsListViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }


}