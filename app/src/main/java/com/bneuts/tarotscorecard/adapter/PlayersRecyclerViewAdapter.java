package com.bneuts.tarotscorecard.adapter;

import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bneuts.tarotscorecard.R;
import com.bneuts.tarotscorecard.model.ScoreCard;

import java.util.List;

/**
 * Created by bneut on 04/02/2018.
 */

public class PlayersRecyclerViewAdapter extends RecyclerView.Adapter<PlayersRecyclerViewAdapter.ViewHolder> {

    private String[] mDataset;

    public PlayersRecyclerViewAdapter(LiveData<ScoreCard> myDataset) {
        // TODO : replace with observing object instead of converting in hard list
        List<String> list = (List<String>) myDataset.getValue().getPlayers();
        int items = list.size();
        for (int i = 0 ; i < items; i++) {
            mDataset[i] = list.get(i);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PlayersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
