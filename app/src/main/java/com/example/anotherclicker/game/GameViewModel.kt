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

    private var _clickers = MutableLiveData<ArrayList<Clicker>>()
    val clickers : LiveData<ArrayList<Clicker>>
        get() = _clickers

    init {
        uiScope.launch {
            _money.value = getMoney()
            _clickers.value = getClickers()
        }
    }

    fun addOne() {
        if (_money.value == null)
            throw NullPointerException("Money amount is null")

        _money.value!!.amount = _money.value!!.amount.plus(1)
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

    private suspend fun getClickers(): ArrayList<Clicker> {
        val clickers = ArrayList<Clicker>()

        val clicker1 = Clicker()
        val clicker2 = Clicker()
        val clicker3 = Clicker()

        clicker1.id = 0
        clicker2.id = 1
        clicker3.id = 2

        clicker1.name = "White Clicker"
        clicker2.name = "Green Clicker"
        clicker3.name = "Orange Clicker"

        clicker1.amount = 0
        clicker2.amount = 0
        clicker3.amount = 0

        clicker1.cost = 100
        clicker2.cost = 600
        clicker3.cost = 4000

        clicker1.procTime = 1.0
        clicker2.procTime = 2.0
        clicker3.procTime = 4.0

        clicker1.procValue = 1
        clicker2.procValue = 8
        clicker3.procValue = 80

        clickers.add(clicker1)
        clickers.add(clicker2)
        clickers.add(clicker3)

        return clickers
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
            var tmp = database.getMoney()
        }
    }
}