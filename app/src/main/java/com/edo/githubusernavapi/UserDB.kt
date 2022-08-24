package com.edo.githubusernavapi

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GitResponse::class], version = 1, exportSchema = false)
abstract class UserDB:RoomDatabase() {
    abstract fun userDao() : UserDao
}