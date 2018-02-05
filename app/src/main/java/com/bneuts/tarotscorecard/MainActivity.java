package com.bneuts.tarotscorecard;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bneuts.tarotscorecard.adapter.PlayersRecyclerViewAdapter;
import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.ui.DatePickerFragment;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Benoît Neuts on 01/02/2018.
 * MainActivity launched at application startup
 */

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String LOG_TAG = "MainActivity";
    public static String pathToDoc;
    private EditText editCardName;
    private EditText editCardDate;
    private RecyclerView editPlayers;
    private String scoreId = "5aCRdSBzx2OzZpqConVK";
    //private PlayersRecyclerViewAdapter playersListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editCardName = findViewById(R.id.editCardName);
        editCardDate = findViewById(R.id.editCardDate);
        editPlayers = findViewById(R.id.editPlayers);
        editCardDate.setClickable(true);
        editCardDate.setFocusable(false);
        editCardDate.setOnClickListener(this::showDatePickerDialog);

        //checkShowEmptyView();


        editPlayers.setHasFixedSize(true);
        LinearLayoutManager playersLayout = new LinearLayoutManager(this);
        editPlayers.setLayoutManager(playersLayout);


        ScoreCardViewModel scoreCardVM = ViewModelProviders.of(this).get(ScoreCardViewModel.class);
        scoreCardVM.init(scoreId);
        LiveData<ScoreCard> liveData = scoreCardVM.getScoreCardLiveData();
        PlayersRecyclerViewAdapter prva = new PlayersRecyclerViewAdapter(liveData);
        editPlayers.setAdapter(prva);
        liveData.observe(this, score -> {
            if (score != null) {
                if (!editCardName.hasFocus() || editCardName.getText().length()==0) {
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(liveData.getValue().getName())) {
                    Boolean saved = scoreCardVM.setCardName(s.toString(),scoreId);
                    if (saved) {
                        Toast.makeText(getApplicationContext(), R.string.toast_auto_save_ok, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void checkShowEmptyView() {
        /* TODO : manage if there's any player in list
        if (playersListAdapter.getItemCount() > 0) {
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }*/
    }


    public void showDatePickerDialog(View v) {
        // TODO : share liveData with dialog or at least scoreId
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(),"datePicker");
    }

    @SuppressLint("DefaultLocale")
    public static String todayWeReOn() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return String.format("%d/%d/%d", day, month, year);
    }

    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd",Locale.getDefault());
        return sdf.format(date);

    }

}























