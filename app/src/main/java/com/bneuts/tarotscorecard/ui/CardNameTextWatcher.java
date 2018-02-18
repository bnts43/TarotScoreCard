package com.bneuts.tarotscorecard.ui;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.UiThread;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.bneuts.tarotscorecard.R;
import com.bneuts.tarotscorecard.model.ScoreCard;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

/**
 * Created by bneut on 09/02/2018.
 */

@UiThread
public class CardNameTextWatcher implements TextWatcher {

    private LiveData liveData;
    private Context context;
    private ScoreCardViewModel vm;
    private FirestoreRecyclerAdapter adapter;

    public CardNameTextWatcher(LiveData liveData,
                             Context context, ScoreCardViewModel vm) {
        this.liveData = liveData;
        this.context = context;
        this.vm = vm;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        if ((liveData.getValue() instanceof ScoreCard)&&liveData==null) {
            if (!s.toString().equals(((ScoreCard)liveData.getValue()).getName())) {
                // TODO : manage to not show Toast (i.e. saved is false)
                // for now saved is always true
                Boolean saved = vm.setCardName(s.toString());
                if (saved) {
                    Toast.makeText(context, R.string.toast_auto_save_ok, Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
}
