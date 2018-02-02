package com.bneuts.tarotscorecard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;


/**
 * Created by bneut on 02/02/2018.
 */

public class FireDocLiveData extends LiveData<DocumentSnapshot> {
    private static final String LOG_TAG = "FireQueryLiveData";

    private final MyValueEventListener listener = new MyValueEventListener();

    private final DocumentReference doc;

    public FireDocLiveData(DocumentReference doc) {
        this.doc = doc;
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        doc.addSnapshotListener(listener);
    }

    private class MyValueEventListener implements EventListener<DocumentSnapshot> {
        @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // TODO : gérer quand une exception est lancée
                    return;
                }
                setValue(snapshot);
            }
        }
    }

