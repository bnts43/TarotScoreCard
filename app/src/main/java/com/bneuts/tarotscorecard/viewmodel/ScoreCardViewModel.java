package com.bneuts.tarotscorecard.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import android.support.annotation.NonNull;

import android.util.Log;

import com.bneuts.tarotscorecard.MainActivity;
import com.bneuts.tarotscorecard.model.ScoreCard;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by Benoît Neuts on 02/02/2018.
 * ViewModel for ScoreCard
 */

public class ScoreCardViewModel extends ViewModel{
    private static final String LOG_TAG = "ScoreCardViewModel";

    private static final DocumentReference CARD_REF = FirebaseFirestore.getInstance().document(MainActivity.pathToDoc);
    private final FireDocLiveData liveData = new FireDocLiveData(CARD_REF);

    private final LiveData<ScoreCard> cardLiveData = Transformations.map(liveData, new Deserializer());

    public void setCardDate(int year, int month, int dayOfMonth) {
        // Constructor deprecated but FireStore takes a Date object :(
        @SuppressWarnings("deprecation") Date date = new Date(year-1900,month,dayOfMonth);
        CARD_REF.update("date",date);
    }

    private class Deserializer implements Function<DocumentSnapshot, ScoreCard> {
        @Override
        public ScoreCard apply(DocumentSnapshot snapshot) {
            ScoreCard deserialized = snapshot.toObject(ScoreCard.class);
            deserialized.setDate(snapshot.getDate("date"));
            return deserialized;
        }
    }

    @NonNull
    public LiveData<ScoreCard> getScoreCardLiveData() {
        return cardLiveData;
    }

    public boolean setCardName(String newValue) {
        String currentValue = null;
        try {
            //noinspection ConstantConditions // managed with try/catch
            currentValue = cardLiveData.getValue().getName();
        } catch (NullPointerException npex) {
            Log.d(LOG_TAG, "NullPointerException receiving null object from LiveData");
        }
        if (!newValue.equals(currentValue)&&currentValue!=null) {
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
                    CARD_REF.update("name", newValue).isComplete();
                }});
            sendDBUpdate.shutdown();
            return true;
        } else {
            return false;
        }
    }

}
