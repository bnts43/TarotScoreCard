package com.bneuts.tarotscorecard.viewmodel

import com.bneuts.tarotscorecard.persistence.dataprovider.UserRepository
import com.bneuts.tarotscorecard.model.User

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData

import javax.inject.Inject

/**
 * Created by bneut on 09/01/2018.
 */
class UserProfileViewModel() : ViewModel() {
    private var user: LiveData<User>? = null
    private var userRepo: UserRepository? = null

    @Inject // UserRepository parameter is provided by Dagger 2
    constructor(userRepo: UserRepository) : this() {
        this.userRepo = userRepo
    }

    fun getUser(): LiveData<User>? {
        return user
    }
    fun init(userId: Int) {
        if (this.user != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        user = userRepo?.getUser(userId);
    }

}
