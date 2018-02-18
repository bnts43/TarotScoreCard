package com.bneuts.tarotscorecard.persistence.dataprovider;


import android.support.annotation.NonNull;

import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;


/**
 * Created by Benoît Neuts on 30/01/2018.
 * Host for FireStore DB Instance
 */

public class FireDBService {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    FireDBService() {
    }

    private static FirebaseFirestore getInstance() {
        return db;
    }

    public static CollectionReference getCollectionRef(@NonNull String collectionPath) {
        return getInstance().collection(collectionPath);
    }

    public static DocumentReference getCardRef(@NonNull String cardId) {
        return getCollectionRef("cards").document(cardId);
    }

    public static void createNewScoreCard(@NonNull String name, Date date, List<User> players) {
        getInstance().collection("cards")
            .add(new ScoreCard(name, date))
            .addOnCompleteListener(
                new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            task.getResult()
                                    .update("cardId",task.getResult().getId());
                            for (User player : players) {
                                task.getResult()
                                        .collection("players")
                                        .add(player).addOnCompleteListener(
                                            new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()) {
                                                        task.getResult()
                                                                .update("userID", task.getResult().getId());
                                                    }
                                                }
                                            }
                                );
                            }
                        }
                    }
                }
            );
    }

    public static CollectionReference getPlayers(@NonNull String cardId) {
        return db.collection("cards").document(cardId).collection("players");
    }

    public static DocumentReference getOnePlayerInScoreCard(@NonNull String cardId, @NonNull String playerId) {
        return getPlayers(cardId).document(playerId);
    }

}
