package com.example.anotherclicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.anotherclicker.database.MoneyDatabase
import com.example.anotherclicker.databinding.FragmentGameBinding
import com.example.anotherclicker.game.GameViewModel
import com.example.anotherclicker.game.GameViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}