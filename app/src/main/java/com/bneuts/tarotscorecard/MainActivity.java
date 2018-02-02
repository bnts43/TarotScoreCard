package com.bneuts.tarotscorecard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.viewmodel.CardScoreViewModel;
import com.bneuts.tarotscorecard.viewmodel.UserViewModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;


/**
 * Created by bneut on 01/02/2018.
 */


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static final String LOG_TAG = "MainActivity";

    private TextView cardName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        cardName = findViewById(R.id.cardName);

        CardScoreViewModel cardScoreVM = ViewModelProviders.of(this).get(CardScoreViewModel.class);

        LiveData<ScoreCard> liveData = cardScoreVM.getScoreCardLiveData();

        liveData.observe(this, new Observer<ScoreCard>() {
            @Override
            public void onChanged(@Nullable ScoreCard score) {
                if (score != null) {

                    String cardRecord = score.getName();
                    System.out.println("firestore a renvoyé : " + cardRecord);
                    cardName.setText(cardRecord);

                }
            }
        });

    }


/*
    @Override
    protected void onStop() {
        ref.removeEventListener(listener);
        super.onStop();
    }*/

/*
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // update the UI here with values in the snapshot
            String cardRecord = dataSnapshot.child("cardName").getValue(String.class);
            System.out.println("coucou"+cardRecord);
            cardName.setText(cardRecord);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // handle any errors
            Log.e(LOG_TAG, "Database error", databaseError.toException());
        }
    };

*/
}
