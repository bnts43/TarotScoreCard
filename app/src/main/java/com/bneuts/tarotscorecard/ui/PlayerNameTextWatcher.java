package com.bneuts.tarotscorecard.ui;

import android.content.Context;
import android.support.annotation.UiThread;
import android.text.Editable;
import android.text.TextWatcher;

import com.bneuts.tarotscorecard.model.User;
import com.bneuts.tarotscorecard.viewmodel.ScoreCardViewModel;

/**
 * Created by bneut on 09/02/2018.
 */

@UiThread
public class PlayerNameTextWatcher implements TextWatcher {

    private User player;
    private ScoreCardViewModel vm;
    private Context context;


    public PlayerNameTextWatcher(Context context, User player,
                                 ScoreCardViewModel vm) {
        this.player = player;
        this.context = context;
        this.vm = vm;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        /*
        textBeingChanged = s.toString();

        if (player!=null||s.toString().equals(player.getName())) {
            // TODO : manage to not show Toast (i.e. saved is false)
            // for now saved is always true
            Boolean saved = vm.updateUser(s.toString(),player.getUserID());
            if (saved) {
                Toast.makeText(context, R.string.toast_auto_save_ok, Toast.LENGTH_SHORT).show();
            }
        }*/
    }
}
