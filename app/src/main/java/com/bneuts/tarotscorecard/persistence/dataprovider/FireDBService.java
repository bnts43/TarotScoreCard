package com.bneuts.tarotscorecard.persistence.dataprovider;


import android.support.annotation.NonNull;

import com.bneuts.tarotscorecard.model.ScoreCard;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Map;


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

    @NonNull
    public static DocumentReference getRef(String docPath) {
        return getInstance().document(docPath);
    }

    @NonNull
    public static CollectionReference getCollectionRef(String collectionPath) {
        return getInstance().collection(collectionPath);
    }

    @NonNull
    public static DocumentReference getCardRef(@NonNull String cardId) {
        return getInstance().collection("cards").document(cardId);
    }

    public static void createNewDocument(String name, Date date, Map<String,Long> players) {

        getInstance().collection("cards").add(new ScoreCard(name, date, players));
    }
}
