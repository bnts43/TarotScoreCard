package com.bneuts.tarotscorecard.persistence.dataprovider

import com.bneuts.tarotscorecard.model.User
import android.arch.lifecycle.LiveData
import javax.inject.Singleton
import com.bneuts.tarotscorecard.persistence.services.UserDao
import javax.inject.Inject
import java.util.concurrent.Executor


/**
 * Created by bneut on 12/01/2018.
 */
@Singleton
class UserRepository {

    private var webservice: Webservice? = null
    private var userDao: UserDao? = null
    private var executor: Executor? = null

    @Inject
    constructor(webservice: Webservice, userDao: UserDao, executor: Executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
   }

    fun getUser(userId: Int): LiveData<User> {
        refreshUser(userId)
        // return a LiveData directly from the database.
        return userDao!!.load(userId)
    }

    private fun refreshUser(userId: Int) {
        executor?.execute({
            // running in a background thread
            // check if user was fetched recently
            val userExists = userDao!!.hasUser("FRESH_TIMEOUT")
            if (!userExists) {
                // refresh the data
                val response = webservice?.getUser(userId)!!.execute()
                // TODO check for error etc.
                // Update the database.The LiveData will automatically refresh so
                // we don't need to do anything else here besides updating the database
                userDao!!.save(response.body()!!)
            }
        })
    }
}