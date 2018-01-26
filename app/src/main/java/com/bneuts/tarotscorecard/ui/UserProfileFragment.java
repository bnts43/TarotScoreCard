package com.bneuts.tarotscorecard.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bneuts.tarotscorecard.R;
import com.bneuts.tarotscorecard.model.User;
import com.bneuts.tarotscorecard.viewmodel.UserProfileViewModel;

import io.reactivex.annotations.Nullable;

/**
 * Created by bneut on 19/01/2018.
 * code kept in Java due to issue with observer
 */
public class UserProfileFragment extends Fragment {
    private static final String UID_KEY = "uid";
    private UserProfileViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = getArguments().getString(UID_KEY);
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        viewModel.getUser().observe(this, user -> {
            // UserProfileFragmentLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.user_profile_fragment_layout);
            // User userLocal = new User("Test", "User");
            //binding.setUser(user);
            //getActivity().findViewById(R.id.user_name_input). = user.getUserName();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile_fragment_layout, container, false);
    }
}
