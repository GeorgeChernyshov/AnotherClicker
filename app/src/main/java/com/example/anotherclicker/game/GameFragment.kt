package com.example.anotherclicker.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.anotherclicker.R
import com.example.anotherclicker.database.MoneyDatabase
import com.example.anotherclicker.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var viewModelFactory : GameViewModelFactory
    private lateinit var viewModel : GameViewModel
    private lateinit var viewPager : ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : FragmentGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = MoneyDatabase.getInstance(application).moneyDatabaseDao
        viewModelFactory = GameViewModelFactory(dataSource)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel

        viewPager = binding.pager

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateMoney()
    }
}