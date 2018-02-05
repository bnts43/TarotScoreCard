package com.bneuts.tarotscorecard.persistence.dataprovider;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.viewmodel.FireDocLiveData;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.Map;
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
        final DocumentReference doc = FireDBService.getCardRef(scoreId);
        final FireDocLiveData liveData = new FireDocLiveData(doc);
        return Transformations.map(liveData, new Deserializer());
    }

    public void setCardDate(int year, int month, int dayOfMonth, String scoreId) {
        // Constructor deprecated but FireStore waits a Date object :(
        @SuppressWarnings("deprecation") Date date = new Date(year-1900,month,dayOfMonth);
        FireDBService.getCardRef(scoreId).update("date",date);
    }

    public boolean setCardName(@NonNull String newValue, @NonNull String scoreId) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor sendDBUpdate = new ThreadPoolExecutor(
                1,       // Initial pool size
                1,       // Max pool size
                3000,
                TimeUnit.MILLISECONDS,
                workQueue);
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
            deserialized.setDate(snapshot.getDate("date"));
            deserialized.setPlayers((Map<String,Long>)snapshot.get("players"));
            return deserialized;
        }
    }
}
