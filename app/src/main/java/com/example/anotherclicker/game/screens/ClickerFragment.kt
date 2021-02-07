package com.example.anotherclicker.game.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.anotherclicker.R
import com.example.anotherclicker.database.MoneyDatabase
import com.example.anotherclicker.databinding.FragmentClickerBinding
import com.example.anotherclicker.game.GameViewModel
import com.example.anotherclicker.game.GameViewModelFactory
import com.example.anotherclicker.game.util.ClickerAdapter
import com.example.anotherclicker.game.util.ClickerItemListener

class ClickerFragment : Fragment() {
    private lateinit var viewModelFactory : GameViewModelFactory
    private lateinit var viewModel : GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentClickerBinding>(inflater, R.layout.fragment_clicker, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = MoneyDatabase.getInstance(application).moneyDatabaseDao
        viewModelFactory = GameViewModelFactory(dataSource)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel

        val adapter = ClickerAdapter(ClickerItemListener { id ->
            viewModel.onClickerItemClicked(id)
        })
        binding.clickerList.adapter = adapter

        viewModel.clickers.observe(viewLifecycleOwner, Observer { clickers ->
            adapter.submitList(clickers)
        })

        binding.lifecycleOwner = this
        return binding.root
    }
}