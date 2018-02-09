package com.bneuts.tarotscorecard.persistence.dataprovider;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.model.User;
import com.bneuts.tarotscorecard.viewmodel.FireDocLiveData;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Calendar;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Benoît Neuts on 04/02/2018.
 * Repository for ScoreCards
 */

public class ScoreCardsRepository {
    private static final String LOG_TAG = "ScoreCardsRepository";

    public LiveData<ScoreCard> getScoreCard(String scoreId) {
        Log.d(LOG_TAG,"Working on ScoreCard ID n°"+scoreId);
        final DocumentReference doc = FireDBService.getCardRef(scoreId);
        Log.d(LOG_TAG,"Working on Database Document ID n°"+doc.getId());
        final FireDocLiveData liveData = new FireDocLiveData(doc);
        return Transformations.map(liveData, new Deserializer());
    }

    public void setCardDate(int year, int month, int dayOfMonth, String scoreId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month);
        FireDBService.getCardRef(scoreId).update("date",calendar.getTime());
    }

    public boolean setCardName(@NonNull String newValue, @NonNull String scoreId) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        int maxPoolSize = Runtime.getRuntime().availableProcessors()-1;
        ThreadPoolExecutor sendDBUpdate = new ThreadPoolExecutor(
                1,       // Initial pool size
                1,       // Max pool size
                3000,
                TimeUnit.MILLISECONDS,
                workQueue);
        //TODO : return false if a thread is already running
        sendDBUpdate.execute(new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(500);
                    }
                } catch (InterruptedException ex) {
                    Log.d(LOG_TAG, "Interrupted Exception in thread while updating FireStore data.");
                }
                FireDBService.getCardRef(scoreId).update("name", newValue);
            }
        });
        sendDBUpdate.shutdown();
        return true;
    }

    private class Deserializer implements Function<DocumentSnapshot, ScoreCard> {
        @Override
        public ScoreCard apply(DocumentSnapshot snapshot) {
            ScoreCard deserialized = snapshot.toObject(ScoreCard.class);
            return deserialized;
        }
    }

    public FirestoreRecyclerOptions<User> getPlayersFSRecycler(String scoreId/*,Lifecycleowner*/) {
        CollectionReference coll = FireDBService.getPlayers(scoreId);
        FirestoreRecyclerOptions<User> s = new FirestoreRecyclerOptions
                .Builder<User>()
                //.setLifecycleOwner() TODO, pass a lifecycle owner so that FireStore
                // automatically manage the lifecycle of the FirestoreRecyclerAdapter
                .setQuery(coll, User.class)
                .build();
        return s;
    }

}
