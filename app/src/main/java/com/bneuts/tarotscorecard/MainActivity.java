package com.bneuts.tarotscorecard;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.bneuts.tarotscorecard.model.User;
import com.bneuts.tarotscorecard.persistence.dataprovider.FireDBService;
import com.bneuts.tarotscorecard.ui.ScoreCardsListFragment;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Benoît Neuts on 01/02/2018.
 * MainActivity launched at application startup
 */

public class MainActivity extends FragmentActivity {
    private static final String LOG_TAG = "MainActivity";

    private String SCORE_ID;
    private ScoreCardViewModel scoreCardVM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SCORE_ID = "quzN2v0gpMbtcj5aVCKu";
        Log.d(LOG_TAG,"Working on ScoreCard ID n°"+SCORE_ID);

        setContentView(R.layout.activity_main);

        scoreCardVM = ViewModelProviders.of(this).get(ScoreCardViewModel.class);
        scoreCardVM.init(SCORE_ID);

        Log.d(LOG_TAG,"Working on ViewModel n°"+scoreCardVM);
        //Log.d(LOG_TAG,"Working on ScoreCard ID n°"+scoreCardVM.getScoreCardLiveData().getValue().getName());


        ScoreCardsListFragment scoreCardsListFragment = new ScoreCardsListFragment();
        Bundle args = new Bundle();
        args.putString("SCORE_ID", SCORE_ID);
        scoreCardsListFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, scoreCardsListFragment, "fragment_container").commit();


    }

    // TODO : method should return SCORE_ID as a String from new created Document in FireStore
    private void createNewScoreScard(String name, Date date, List<User> players) {
        FireDBService.createNewScoreCard(name, date, players);
    }


}























