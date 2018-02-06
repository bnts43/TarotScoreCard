package com.bneuts.tarotscorecard.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Benoît Neuts on 03/02/2018.
 * Display a fragment hosting a datePicker for date selection
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String LOG_TAG = "DatePickerFragment";

    private static String SCORE_ID;
    private static ScoreCardViewModel scoreCardVM;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) throws ActivityNotFoundException {
        scoreCardVM = getScoreCardVM();
        Date date = scoreCardVM.getScoreCardLiveData().getValue().getDate();
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            if (getActivity() != null ) {
                return new DatePickerDialog(getActivity(), this, year, month, day);
            } else {
                Log.d(LOG_TAG, "Root Activity not found to load date picker properly");
                throw new ActivityNotFoundException();
            }
        } else {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            if (getActivity() != null ) {
                return new DatePickerDialog(getActivity(), this, year, month, day);
            } else {
                Log.d(LOG_TAG, "Root Activity not found to load date picker properly");
                throw new ActivityNotFoundException();
            }

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        scoreCardVM = getScoreCardVM();
        scoreCardVM.setCardDate(year,month,dayOfMonth, SCORE_ID);
    }


    private ScoreCardViewModel getScoreCardVM() {
        SCORE_ID = getArguments().getString("SCORE_ID").toString();
        scoreCardVM = ViewModelProviders.of(getActivity()).get(ScoreCardViewModel.class);
        scoreCardVM.init(SCORE_ID);
        return scoreCardVM;
    }

}
