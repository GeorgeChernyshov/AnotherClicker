package com.example.anotherclicker.game.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.anotherclicker.R
import com.example.anotherclicker.database.MoneyDatabase
import com.example.anotherclicker.databinding.FragmentButtonBinding
import com.example.anotherclicker.game.GameFragment
import com.example.anotherclicker.game.GameViewModel
import com.example.anotherclicker.game.GameViewModelFactory

class ButtonFragment : Fragment() {
    private lateinit var viewModelFactory : GameViewModelFactory
    private lateinit var viewModel : GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentButtonBinding>(inflater, R.layout.fragment_button, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = MoneyDatabase.getInstance(application).moneyDatabaseDao
        viewModelFactory = GameViewModelFactory(dataSource)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel

        binding.setLifecycleOwner(this)
        return binding.root
    }
}