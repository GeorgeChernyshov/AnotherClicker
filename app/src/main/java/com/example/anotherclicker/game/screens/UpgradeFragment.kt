package com.example.anotherclicker.game.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.anotherclicker.R
import com.example.anotherclicker.databinding.FragmentButtonBinding
import com.example.anotherclicker.databinding.FragmentUpgradeBinding

class UpgradeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentUpgradeBinding>(inflater, R.layout.fragment_upgrade, container, false)
        return binding.root
    }
}