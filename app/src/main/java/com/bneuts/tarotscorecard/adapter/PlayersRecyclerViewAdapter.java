package com.bneuts.tarotscorecard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bneuts.tarotscorecard.R;

import java.util.Map;

/**
 * Created by Benoît Neuts on 04/02/2018.
 * RecyclerView delivering players'listing for ScoreCard Fragment details
 */

public class PlayersRecyclerViewAdapter extends RecyclerView.Adapter<PlayersRecyclerViewAdapter.ViewHolder> {

    private String[] mDataset;


    public PlayersRecyclerViewAdapter(Map<String,Long> list ) {
        // TODO : replace with observing object instead of converting in hard list
        int items = list.size();
        int i =0;
        mDataset = new String[items];
        for (Map.Entry<String, Long> entry : list.entrySet()) {
            mDataset[i] = entry.getKey();
            i++;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PlayersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item_list , parent, false);
        TextView tv = v.findViewById(R.id.player_item_list_tv);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v,tv);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v, TextView tv) {
            super(v);
            mTextView = tv;
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
