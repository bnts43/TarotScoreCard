package com.bneuts.tarotscorecard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by bneut on 02/02/2018.
 */

public class UserViewModel extends ViewModel{

    // TODO : change path to document to User path
    private static final DocumentReference USER_REF = FirebaseFirestore.getInstance().document("/cards/DG8kCUeybodSNh7fwroy");
    private final FireDocLiveData liveData = new FireDocLiveData(USER_REF);

    @NonNull
    public LiveData<DocumentSnapshot> getDocSnapshotLiveData() {
        return liveData;
    }
}
