package com.example.anotherclicker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.anotherclicker.database.entities.MoneyAmount

@Dao
interface MoneyDatabaseDao {
    @Insert
    fun insertMoney(amount: MoneyAmount)

    @Update
    fun updateMoney(amount: MoneyAmount)

    @Query("DELETE FROM money_table")
    fun clear()

    @Query("SELECT * FROM money_table ORDER BY id LIMIT 1")
    fun getMoney(): MoneyAmount?
}