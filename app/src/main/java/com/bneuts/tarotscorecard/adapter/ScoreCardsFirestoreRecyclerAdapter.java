package com.bneuts.tarotscorecard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bneuts.tarotscorecard.R;
import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * Created by bneut on 09/02/2018.
 */

public class ScoreCardsFirestoreRecyclerAdapter extends FirestoreRecyclerAdapter<ScoreCard, ScoreCardsListViewHolder> {

    private final String LOG_TAG = "CardsFSRecycAdapter";
    private Context context;
    private ScoreCardViewModel vm;

    public ScoreCardsFirestoreRecyclerAdapter(
            @NonNull FirestoreRecyclerOptions<ScoreCard> options,
            Context context, ScoreCardViewModel vm) {
        super(options);
        this.context = context;
        this.vm = vm;
    }

    @Override
    public void onBindViewHolder(ScoreCardsListViewHolder holder, int position, ScoreCard scoreCard) {
        holder.cardName.setText(scoreCard.getName());
        Toast.makeText(context, R.string.toast_data_load, Toast.LENGTH_SHORT).show();

    }

    @Override
    public ScoreCardsListViewHolder onCreateViewHolder(ViewGroup group, int i) {
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.card_item_list, group, false);

        return new ScoreCardsListViewHolder(view);
    }

    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e(LOG_TAG, e.getMessage());
    }
}
