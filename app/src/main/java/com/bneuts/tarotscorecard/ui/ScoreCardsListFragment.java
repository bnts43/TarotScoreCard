package com.bneuts.tarotscorecard.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bneuts.tarotscorecard.R;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;

/**
 * Created by bneut on 07/02/2018.
 */

// TODO : add archiving a card so that it doesn't show in list

public class ScoreCardsListFragment extends Fragment {

    private static final String LOG_TAG = "ScoreCardsListFragment";

    private EditText cardListTitle;
    private RecyclerView scoreCardsList;
    private String SCORE_ID;
    private Button addScoreCard;
    private ScoreCardViewModel scoreCardVM;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SCORE_ID = getArguments().getString("SCORE_ID");
        Log.d(LOG_TAG,"Working on ScoreCard ID n°"+SCORE_ID);
        View view = inflater.inflate(R.layout.fragment_scorecards_list,container,false);
        cardListTitle = view.findViewById(R.id.cardListTitle);
        cardListTitle.setFocusable(false);
        scoreCardsList = view.findViewById(R.id.scoreCardsList);

        addScoreCard = view.findViewById(R.id.addScoreCard);

        scoreCardsList.setHasFixedSize(true);
        LinearLayoutManager playersLayout = new LinearLayoutManager(getContext());
        scoreCardsList.setLayoutManager(playersLayout);

        scoreCardVM = ViewModelProviders.of(getActivity()).get(ScoreCardViewModel.class);
        //scoreCardVM.init(scoreId);

        addScoreCard.setOnClickListener(c -> {
            ScoreCardFragment scoreCardFragment = new ScoreCardFragment();
            Bundle args = new Bundle();
            args.putString("SCORE_ID", SCORE_ID);
            scoreCardFragment.setArguments(args);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, scoreCardFragment,"scorecard_fragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        });
        return view;
    }
}
