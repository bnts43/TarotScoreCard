package com.bneuts.tarotscorecard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.persistence.dataprovider.ScoreCardsRepository;


/**
 * Created by Benoît Neuts on 02/02/2018.
 * ViewModel for ScoreCard
 */

public class ScoreCardViewModel extends ViewModel{
    private static final String LOG_TAG = "ScoreCardViewModel";
    private ScoreCardsRepository scoresRepo = new ScoreCardsRepository();
    private LiveData<ScoreCard> cardLiveData;

    public void init(String scoreId) {
        if (this.cardLiveData != null) {
            return;
        }
        cardLiveData = scoresRepo.getScoreCard(scoreId);
    }
    @NonNull
    public LiveData<ScoreCard> getScoreCardLiveData() { return cardLiveData; }

    public void setCardDate(int year, int month, int dayOfMonth, String scoreId) {
        scoresRepo.setCardDate(year, month, dayOfMonth, scoreId);
    }

    public boolean setCardName(String newValue, String scoreId) {
        return scoresRepo.setCardName(newValue, scoreId);
    }

}
