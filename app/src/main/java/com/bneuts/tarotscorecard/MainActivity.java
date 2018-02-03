package com.bneuts.tarotscorecard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.ui.DatePickerFragment;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by Benoît Neuts on 01/02/2018.
 * MainActivity launched at application startup
 */


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static final String LOG_TAG = "MainActivity";
    public static Boolean okSaved = false;

    public static String pathToDoc;

    private TextView cardName;
    private EditText editCardName;
    //private Button sendCardName;
    private EditText editCardDate;
    private EditText chooseDate;

    private ExecutorService es = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        cardName = findViewById(R.id.cardName);
        editCardName = findViewById(R.id.editCardName);
        editCardDate = findViewById(R.id.editCardDate);
        //sendCardName = findViewById(R.id.sendCardName);

        editCardDate.setOnFocusChangeListener((v,c) -> {
            if (c) {
                showDatePickerDialog(v);
            }
        });

        editCardDate.setText(new SimpleDateFormat("yy/m/d", Locale.getDefault()).format(new Date()));

        pathToDoc = "/cards/nvpzNTd1Neh86fXMw8IS";
        ScoreCardViewModel scoreCardVM = ViewModelProviders.of(this).get(ScoreCardViewModel.class);

        LiveData<ScoreCard> liveData = scoreCardVM.getScoreCardLiveData();

        liveData.observe(this, score -> {
            if (score != null) {

                String cardRecord = score.getName();
                System.out.println("firestore a renvoyé : " + cardRecord);
                cardName.setText(cardRecord);
                int currentCursorPosition = editCardName.getSelectionEnd();
                editCardName.setText(cardRecord);
                editCardName.setSelection(currentCursorPosition);
                // TODO : use strings.xml instead of plain text inside code
                if (editCardName.getSelectionEnd() <1) {
                    Toast.makeText(getApplicationContext(), "Les données ont été chargées.", Toast.LENGTH_SHORT).show();
                }
                okSaved = false;
            }
        });

        editCardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String modifiedText = s.toString();
                BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
                ThreadPoolExecutor sendDBUpdate = new ThreadPoolExecutor(
                        1,       // Initial pool size
                        1,       // Max pool size
                        2000,
                        TimeUnit.MILLISECONDS,
                        workQueue);
                sendDBUpdate.execute(new Thread() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(1000);
                            }
                        } catch (InterruptedException ex) {
                            // TODO : use strings.xml instead of plain text inside code
                            Log.d(LOG_TAG, "Interrupted Exception in thread updating FireStore data");
                        }
                        if (modifiedText.equals(s.toString())) {
                            scoreCardVM.setCardName(s.toString());
                            okSaved = true;
                        }
                    }});
                sendDBUpdate.shutdown();
                if (okSaved) {
                    Toast.makeText(getApplicationContext(), "Les données ont été enregistrées.",Toast.LENGTH_SHORT).show();
                }
                // TODO : use strings.xml instead of plain text inside code

            }
        });






    }

    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(),"datePicker");
    }

}
