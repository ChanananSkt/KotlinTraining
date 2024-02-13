package com.codemobiles.android.cmauthenmvvm.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user:User)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun query(username:String):User?

    @Delete
    suspend fun delete(user:User)
}