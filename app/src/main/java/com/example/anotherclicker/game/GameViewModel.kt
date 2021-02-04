package com.example.anotherclicker.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anotherclicker.database.entities.MoneyAmount
import com.example.anotherclicker.database.MoneyDatabaseDao
import com.example.anotherclicker.database.entities.Clicker
import kotlinx.coroutines.*
import java.lang.NullPointerException

class GameViewModel(val database: MoneyDatabaseDao) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _money = MutableLiveData<MoneyAmount>()
    val money: LiveData<MoneyAmount>
        get() = _money

    private var _clickers = MutableLiveData<List<Clicker>>()
    val clickers : LiveData<List<Clicker>>
        get() = _clickers

    init {
        uiScope.launch {
            _money.value = getMoney()
            _clickers.value = getClickers()
            if(_clickers.value!!.size == 0) {
                initClickers()
                _clickers.value = getClickers()
            }
        }
    }

    fun addOne() {
        if (_money.value == null)
            throw NullPointerException("Money amount is null")

        _money.value = MoneyAmount(_money.value!!.id, _money.value!!.amount.plus(1))
    }

    private suspend fun getMoney(): MoneyAmount {
        return withContext(Dispatchers.IO) {
            var moneyAmount = database.getMoney()
            if (moneyAmount == null) {
                val newAmount = MoneyAmount()
                database.insertMoney(newAmount)
                moneyAmount = newAmount
            }

            moneyAmount!!
        }
    }

    private suspend fun getClickers(): List<Clicker> {
        return withContext(Dispatchers.IO) {
            val clickers = database.getClickers()
            clickers
        }
    }

    private suspend fun initClickers() {
        withContext(Dispatchers.IO) {
            database.insertClicker(Clicker(1, "White Clicker", 100, 0, 1.0, 1))
            database.insertClicker(Clicker(2, "Green Clicker", 600, 0, 2.0, 8))
            database.insertClicker(Clicker(3, "Orange Clicker", 4000, 0, 4.0, 80))
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
            database.updateMoney(_money.value!!)
        }
    }
}