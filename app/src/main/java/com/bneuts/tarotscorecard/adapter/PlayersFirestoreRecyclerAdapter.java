package com.bneuts.tarotscorecard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bneuts.tarotscorecard.R;
import com.bneuts.tarotscorecard.model.User;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * Created by bneut on 09/02/2018.
 */

public class PlayersFirestoreRecyclerAdapter extends FirestoreRecyclerAdapter<User, PlayersViewHolder> {

    private final String LOG_TAG = "PlayersFSRecycAdapter";
    private Context context;
    private ScoreCardViewModel vm;

    public PlayersFirestoreRecyclerAdapter(
            @NonNull FirestoreRecyclerOptions<User> options,
            Context context, ScoreCardViewModel vm) {
        super(options);
        this.context = context;
        this.vm = vm;
    }

    @Override
    public void onBindViewHolder(PlayersViewHolder holder, int position, User player) {

        holder.playerName.setText(player.getName());

        try {
            holder.playerName.setSelection(player.getName().length());
        } catch (IndexOutOfBoundsException iobex) {
            Log.d(LOG_TAG, "Couldn't move cursor to previous position.");
        }
        Toast.makeText(context, R.string.toast_data_load, Toast.LENGTH_SHORT).show();

        //holder.playerName.addTextChangedListener(
        //            new PlayerNameTextWatcher(context,player,vm));
    }

    @Override
    public PlayersViewHolder onCreateViewHolder(ViewGroup group, int i) {
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.player_item_list, group, false);

        return new PlayersViewHolder(view);
    }

    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e(LOG_TAG, e.getMessage());
    }
}
