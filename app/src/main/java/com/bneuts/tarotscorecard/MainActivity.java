package com.bneuts.tarotscorecard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bneuts.tarotscorecard.adapter.PlayersRecyclerViewAdapter;
import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.persistence.dataprovider.FireDBService;
import com.bneuts.tarotscorecard.ui.DatePickerFragment;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Benoît Neuts on 01/02/2018.
 * MainActivity launched at application startup
 */

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    private EditText editCardName;
    private EditText editCardDate;
    private RecyclerView editPlayers;
    private String scoreId;
    private Button sendCard;
    private ScoreCardViewModel scoreCardVM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scoreId = "jRaP3hpMqPcqkp1w9J1P";

        setContentView(R.layout.activity_main);
        editCardName = findViewById(R.id.editCardName);
        editCardDate = findViewById(R.id.editCardDate);
        editPlayers = findViewById(R.id.editPlayers);
        editCardDate.setClickable(true);
        editCardDate.setFocusable(false);
        editCardDate.setOnClickListener(c-> {
            DatePickerFragment dateFragment = new DatePickerFragment();
            Bundle args = new Bundle();
            args.putString("SCORE_ID", scoreId);
            dateFragment.setArguments(args);
            dateFragment.show(getSupportFragmentManager(),"datePicker");
        });
        sendCard = findViewById(R.id.sendCard);

        editPlayers.setHasFixedSize(true);
        LinearLayoutManager playersLayout = new LinearLayoutManager(this);
        editPlayers.setLayoutManager(playersLayout);

        if (scoreId !=null) {
            scoreCardVM = ViewModelProviders.of(this).get(ScoreCardViewModel.class);
            scoreCardVM.init(scoreId);
            LiveData<ScoreCard> liveData = scoreCardVM.getScoreCardLiveData();

            liveData.observe(this, score -> {
                if (score != null) {
                    PlayersRecyclerViewAdapter prva = new PlayersRecyclerViewAdapter(liveData.getValue().getPlayers());
                    editPlayers.setAdapter(prva);
                    if (!editCardName.hasFocus() || editCardName.getText().length() == 0) {
                        int currentCursorPosition = editCardName.getSelectionEnd();
                        editCardName.setText(score.getName());
                        editCardDate.setText(dateToString(score.getDate()));

                        try {
                            editCardName.setSelection(currentCursorPosition);
                        } catch (IndexOutOfBoundsException iobex) {
                            Log.d(LOG_TAG, "Couldn't move cursor to previous position.");
                        }
                        Toast.makeText(getApplicationContext(), R.string.toast_data_load, Toast.LENGTH_SHORT).show();
                    } else {
                        if (!editCardDate.getText().toString().equals(dateToString(score.getDate()))) {
                            editCardDate.setText(dateToString(score.getDate()));
                        }
                    }
                }
            });

            editCardName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals(liveData.getValue().getName())) {
                        Boolean saved = scoreCardVM.setCardName(s.toString(), scoreId);
                        if (saved) {
                            Toast.makeText(getApplicationContext(), R.string.toast_auto_save_ok, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            Map<String,Long> playersMap = new HashMap<>();
            playersMap.put("toto", 3L );
            playersMap.put("taotao", 4L );
            playersMap.put("tata", 5L );
            playersMap.put("tonton", 6L );
            playersMap.put("titi", 7L );


            sendCard.setOnClickListener(c -> createNewScoreScard(editCardName.getText().toString(), new Date(2018-1900,1,5),playersMap));
        }
    }

    private void createNewScoreScard(String name, Date date, Map<String,Long> players) {
        FireDBService.createNewDocument(name, date,players);
    }

    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd",Locale.getDefault());
        return sdf.format(date);
    }

}























