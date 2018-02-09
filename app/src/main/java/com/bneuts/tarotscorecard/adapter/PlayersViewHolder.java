package com.bneuts.tarotscorecard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bneuts.tarotscorecard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bneut on 08/02/2018.
 */

public class PlayersViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.player_item_list_tv)
    public EditText playerName;
    @BindView(R.id.player_item_list_id)
    public TextView playerID;

    public PlayersViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }


}