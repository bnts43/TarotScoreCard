package com.bneuts.tarotscorecard.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bneuts.tarotscorecard.R;
import com.bneuts.tarotscorecard.adapter.PlayersFirestoreRecyclerAdapter;
import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.model.User;
import com.bneuts.tarotscorecard.persistence.dataprovider.FireDBService;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Benoît Neuts on 06/02/2018.
 * Fragment to hold ScoreCard details
 */

public class ScoreCardFragment extends Fragment {

    private static final String LOG_TAG = "ScoreCardFragment";
    private static final int REQUEST_DATE = 0;

    private String SCORE_ID;

    private EditText editCardName;
    private EditText editCardDate;
    private RecyclerView editPlayers;
    private Button sendCard;

    private ScoreCardViewModel scoreCardVM;
    private Date currentDate;

    private FirestoreRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SCORE_ID = null;
        SCORE_ID = getArguments().getString("SCORE_ID");
        Log.d(LOG_TAG,"Working on ScoreCard ID n°"+SCORE_ID);

        View view = inflater.inflate(R.layout.fragment_scorecard_single,container,false);

        editCardName = view.findViewById(R.id.editCardName);
        editCardDate = view.findViewById(R.id.editCardDate);
        editPlayers = view.findViewById(R.id.editPlayers);
        sendCard = view.findViewById(R.id.sendCard);


        editCardDate.setClickable(true);
        editCardDate.setFocusable(false);
        editCardDate.setOnClickListener(c-> showDatePicker());

        editPlayers.setHasFixedSize(true);
        LinearLayoutManager playersLayout = new LinearLayoutManager(getContext());
        editPlayers.setLayoutManager(playersLayout);

        if (SCORE_ID !=null) {
            scoreCardVM = ViewModelProviders.of(getActivity()).get(ScoreCardViewModel.class);

            LiveData<ScoreCard> liveData = scoreCardVM.getScoreCardLiveData();

            adapter = new PlayersFirestoreRecyclerAdapter(
                    scoreCardVM.getPlayersFSRecycler(),getContext(),scoreCardVM);

            adapter.notifyDataSetChanged();
            editPlayers.setAdapter(adapter);

            liveData.observe(this, score ->  updateCardInfo(score));

            editCardName.addTextChangedListener(new CardNameTextWatcher(liveData,getContext(),scoreCardVM));
        } else {
            // TODO : gérer la création des joueurs d'après la saisie dans la liste
            stubCreateNewScoreScard();
        }
        return view;
    }

    private void stubCreateNewScoreScard() {
        List<User> playersMap = new ArrayList<>();
        playersMap.add(new User("toto"));
        playersMap.add(new User("tata"));
        playersMap.add(new User("tonton"));
        playersMap.add(new User("titi"));
        playersMap.add(new User("taotao"));

        sendCard.setOnClickListener(c -> createNewScoreScard(editCardName.getText().toString(), currentDate,playersMap));
    }

    private void updateCardInfo(ScoreCard score) {
        if (score != null) {
            //adapter.notifyDataSetChanged();
            if (!editCardName.hasFocus() || editCardName.getText().length() == 0) {
                int currentCursorPosition = editCardName.getSelectionEnd();
                editCardName.setText(score.getName());
                editCardDate.setText(dateToString(score.getDate()));

                try {
                    editCardName.setSelection(currentCursorPosition);
                } catch (IndexOutOfBoundsException iobex) {
                    Log.d(LOG_TAG, "Couldn't move cursor to previous position.");
                }
                Toast.makeText(getContext(), R.string.toast_data_load, Toast.LENGTH_SHORT).show();
            } else {
                if (!editCardDate.getText().toString().equals(dateToString(score.getDate()))) {
                    editCardDate.setText(dateToString(score.getDate()));
                }
            }
        }
    }

    private void showDatePicker() {
        DatePickerFragment dateFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString("SCORE_ID", SCORE_ID);
        dateFragment.setArguments(args);
        dateFragment.setTargetFragment(this, REQUEST_DATE);
        dateFragment.show(getFragmentManager(),"datePicker");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != AppCompatActivity.RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_DATE:
                updateDate(data);
                break;
        }
    }

    private void updateDate(Intent data) {
        Bundle extras = data.getExtras();
        int year = extras.getInt("PICKED_YEAR");
        int month = extras.getInt("PICKED_MONTH");
        int day = extras.getInt("PICKED_DAY");
        Calendar c = Calendar.getInstance();
        c.set(year,month,day);
        currentDate = c.getTime();
        editCardDate.setText(dateToString(currentDate));
    }

    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd", Locale.getDefault());
        return sdf.format(date);
    }

    private void createNewScoreScard(String name, Date date, List<User> players) {
        FireDBService.createNewScoreCard(name, date, players);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }

    }
}
