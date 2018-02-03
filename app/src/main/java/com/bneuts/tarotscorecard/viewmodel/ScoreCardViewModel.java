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



/**
 * Created by Benoît Neuts on 02/02/2018.
 * ViewModel for ScoreCard
 */

public class ScoreCardViewModel extends ViewModel{
    private static final String LOG_TAG = "ScoreCardViewModel";

    private static final DocumentReference CARD_REF = FirebaseFirestore.getInstance().document(MainActivity.pathToDoc);
    private final FireDocLiveData liveData = new FireDocLiveData(CARD_REF);

    private final LiveData<ScoreCard> cardLiveData = Transformations.map(liveData, new Deserializer());

    private class Deserializer implements Function<DocumentSnapshot, ScoreCard> {
        @Override
        public ScoreCard apply(DocumentSnapshot snapshot) {
            return snapshot.toObject(ScoreCard.class);
        }
    }


    @NonNull
    public LiveData<ScoreCard> getScoreCardLiveData() {
        return cardLiveData;
    }

    public void setCardName(String cardName) {
        CARD_REF.update("name", cardName);
    }
}
