package com.bneuts.tarotscorecard.persistence.dataprovider;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * Created by Benoît Neuts on 30/01/2018.
 * Host for FireStore DB Instance
 */

public class FireDBService {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

}
