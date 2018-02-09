package com.bneuts.tarotscorecard.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
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
        Log.d(LOG_TAG, "VM = "+getScoreCardVM());
        Log.d(LOG_TAG, "LD = "+getScoreCardVM().getScoreCardLiveData());
        Log.d(LOG_TAG, "Val = "+getScoreCardVM().getScoreCardLiveData().getValue());
        SCORE_ID = getArguments().getString("SCORE_ID");
        Date date = null;

        if (SCORE_ID != null) {
             date = getScoreCardVM().getScoreCardLiveData().getValue().getDate();
        }
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        return datePickerDialog(c);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (SCORE_ID != null) {
            scoreCardVM = getScoreCardVM();
            scoreCardVM.setCardDate(year, month, dayOfMonth, SCORE_ID);
        } else {
            Intent intent = new Intent();
            intent.putExtra("PICKED_YEAR", year);
            intent.putExtra("PICKED_MONTH", month);
            intent.putExtra("PICKED_DAY", dayOfMonth);

            getTargetFragment().onActivityResult(getTargetRequestCode(), AppCompatActivity.RESULT_OK,intent);
        }
    }



    private ScoreCardViewModel getScoreCardVM() {
        Log.d(LOG_TAG, "Reaching scorecard ID = "+SCORE_ID);
        Log.d(LOG_TAG, "GET ACTIVITY = "+getActivity());

        return ViewModelProviders.of(getActivity()).get(ScoreCardViewModel.class);
    }

    private DatePickerDialog datePickerDialog(Calendar c) {
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
