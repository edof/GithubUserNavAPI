package com.edo.githubusernavapi

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GitResponse)

    @Query("SELECT * FROM USER")
    fun load(): LiveData<MutableList<GitResponse>>

    @Query("SELECT * FROM USER WHERE id LIKE :id LIMIT 1")
    fun searchId(id: Int): GitResponse

    @Delete
    fun delete(user: GitResponse)
}