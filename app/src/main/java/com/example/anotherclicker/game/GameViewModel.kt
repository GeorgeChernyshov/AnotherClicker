package com.example.anotherclicker.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anotherclicker.database.MoneyAmount
import com.example.anotherclicker.database.MoneyDatabaseDao
import kotlinx.coroutines.*
import java.lang.NullPointerException

class GameViewModel(val database: MoneyDatabaseDao) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _money = MutableLiveData<MoneyAmount>()
    val money: LiveData<MoneyAmount>
        get() = _money

    init {
        uiScope.launch {
            _money.value = getMoney()
        }
    }

    fun addOne() {
        if (_money.value == null)
            throw NullPointerException("Money amount is null")

        _money.value = MoneyAmount(_money.value!!.id, _money.value!!.amount.plus(1))
    }

    private suspend fun getMoney(): MoneyAmount {
        return withContext(Dispatchers.IO) {
            var moneyAmount = database.get()
            if (moneyAmount == null) {
                val newAmount = MoneyAmount()
                database.insert(newAmount)
                moneyAmount = newAmount
            }

            moneyAmount!!
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun updateMoney() {
        if (_money.value == null)
            throw NullPointerException("Money amount is null")

        uiScope.launch {
            update()
        }
    }

    private suspend fun update() {
        withContext(Dispatchers.IO) {
            database.update(_money.value!!)
            var tmp = database.get()
        }
    }
}