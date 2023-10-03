package com.example.food_recipes.core.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter (
    val bundle: Bundle,
    private val fragments: ArrayList<Fragment>,
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(pos: Int): Fragment {
        fragments[pos].arguments = bundle
        return fragments[pos]
    }
}