package com.example.anotherclicker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoneyDatabaseDao {
    @Insert
    fun insert(amount: MoneyAmount)

    @Update
    fun update(amount: MoneyAmount)

    @Query("DELETE FROM money_table")
    fun clear()

    @Query("SELECT * FROM money_table ORDER BY id LIMIT 1")
    fun get(): MoneyAmount?
}