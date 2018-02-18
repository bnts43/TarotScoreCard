package com.bneuts.tarotscorecard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.model.User;
import com.bneuts.tarotscorecard.persistence.dataprovider.ScoreCardsRepository;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


/**
 * Created by Benoît Neuts on 02/02/2018.
 * ViewModel for ScoreCard
 */

public class ScoreCardViewModel extends ViewModel{
    private static final String LOG_TAG = "ScoreCardViewModel";
    private static ScoreCardsRepository scoresRepo = new ScoreCardsRepository();
    private LiveData<ScoreCard> cardLiveData;
    private String SCORE_ID;

    public void init(String scoreId) {
        SCORE_ID = scoreId;
        if (this.cardLiveData != null) {
            return;
        }
        Log.d(LOG_TAG,"Instantiating ViewModel with ScoreCard ID n°"+SCORE_ID);

        cardLiveData = scoresRepo.getScoreCard(SCORE_ID);
    }

    public LiveData<ScoreCard> getScoreCardLiveData() { return cardLiveData; }

    public void setCardDate(int year, int month, int dayOfMonth, String scoreId) {
        Log.d(LOG_TAG,"Setting Card Date in ViewModel for ScoreCard ID n°"+SCORE_ID);
        scoresRepo.setCardDate(year, month, dayOfMonth, scoreId);
    }

    public boolean setCardName(String newValue) {
        Log.d(LOG_TAG,"Setting Card Name in ViewModel for ScoreCard ID n°"+SCORE_ID);

        return scoresRepo.setCardName(newValue, SCORE_ID);
    }

    public FirestoreRecyclerOptions<User> getPlayersFSRecycler() {
        return scoresRepo.getPlayersFSRecycler(SCORE_ID);
    }

    public boolean updateUser(String s, String userID) {
        return scoresRepo.updateUserName(s,userID,SCORE_ID);
    }
}
