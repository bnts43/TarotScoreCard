package com.bneuts.tarotscorecard.persistence

import android.arch.persistence.room.RoomDatabase
import com.bneuts.tarotscorecard.model.User
import android.arch.persistence.room.Database
import com.bneuts.tarotscorecard.persistence.services.UserDao





/**
 * Created by bneut on 12/01/2018.
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}