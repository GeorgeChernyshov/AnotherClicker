package com.example.anotherclicker.game.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class GamePagerAdapter(fa: FragmentActivity, private val screens: List<Fragment>):
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}