package com.edo.githubusernavapi

import android.content.Context
import androidx.room.Room

class DBModule(private val context: Context) {
    private val db = Room.databaseBuilder(context, UserDB::class.java, "githubuser.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}