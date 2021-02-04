package com.example.anotherclicker.game.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.anotherclicker.game.GameViewModel
import com.example.anotherclicker.game.screens.ButtonFragment
import com.example.anotherclicker.game.screens.ClickerFragment
import com.example.anotherclicker.game.screens.UpgradeFragment
import java.lang.Exception

class GamePagerAdapter(fragment: Fragment):
    FragmentStateAdapter(fragment) {

    companion object {
        val SCREENS_AMOUNT = 3
    }

    override fun getItemCount(): Int = SCREENS_AMOUNT

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ButtonFragment()
            1 -> ClickerFragment()
            2 -> UpgradeFragment()
            else -> throw Exception("Invalid Page Number")
        }
    }
}