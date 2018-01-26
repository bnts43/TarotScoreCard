package com.bneuts.tarotscorecard.persistence.services

import android.arch.persistence.room.Dao
import com.bneuts.tarotscorecard.model.User
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


/**
 * Created by bneut on 12/01/2018.
 */

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM user WHERE id = :userId")
    fun load(userId: Int): LiveData<User>

    fun hasUser(FRESH_TIMEOUT: String): Boolean {
        // TODO : tester si l'utilisateur a été téléchargé dernièrement
        return false;
    }
}
