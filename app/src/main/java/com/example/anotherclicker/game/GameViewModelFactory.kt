package com.example.anotherclicker.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anotherclicker.database.MoneyDatabaseDao

class GameViewModelFactory(val datasource: MoneyDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(datasource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}