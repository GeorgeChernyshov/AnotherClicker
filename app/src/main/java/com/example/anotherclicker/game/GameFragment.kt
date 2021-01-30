package com.example.anotherclicker.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.anotherclicker.R
import com.example.anotherclicker.database.MoneyDatabase
import com.example.anotherclicker.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var viewModelFactory : GameViewModelFactory
    private lateinit var viewModel : GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : FragmentGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = MoneyDatabase.getInstance(application).moneyDatabaseDao
        viewModelFactory = GameViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.updateMoney()
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateMoney()
    }
}