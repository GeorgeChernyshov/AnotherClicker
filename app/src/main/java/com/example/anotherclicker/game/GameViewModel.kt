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

    private val _clickers = MutableLiveData<List<Clicker>>()
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

    fun onClickerItemClicked(id: Int) {
        val item: Clicker = clickers.value!!.first { it.id == id }
        if(isAffordable(item)) {
            buyClicker(item)
        }
    }

    fun update() {
        if (_money.value == null)
            throw NullPointerException("Money amount is null")

        uiScope.launch {
            updateMoney()
            updateClickers()
        }
    }

    private fun isAffordable(clicker: Clicker) : Boolean {
        return _money.value!!.amount > clicker.cost
    }

    private fun buyClicker(clicker: Clicker) {
        _money.value = MoneyAmount(_money.value!!.id, _money.value!!.amount.minus(clicker.cost))
        clicker.amount += 1
        var tmp = _clickers.value!!.toList()
        _clickers.value = null
        _clickers.value = tmp
    }

    private suspend fun updateMoney() {
        withContext(Dispatchers.IO) {
            database.updateMoney(_money.value!!)
        }
    }

    private suspend fun updateClickers() {
        withContext(Dispatchers.IO) {
            for(item in _clickers.value!!) {
                database.updateClicker(item)
            }
        }
    }
}