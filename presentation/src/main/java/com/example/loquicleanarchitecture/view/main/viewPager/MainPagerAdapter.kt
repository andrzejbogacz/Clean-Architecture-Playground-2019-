package com.example.loquicleanarchitecture.view.main.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.loquicleanarchitecture.view.main.viewPager.RandomChatsFragment
import com.example.loquicleanarchitecture.view.main.viewPager.FriendsFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return RandomChatsFragment()
        } else{
            return FriendsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position)
        {
            0 -> "Random"
            1 -> "Friends"
            else -> "none"
        }
    }

    override fun getCount(): Int {
        return 2
    }
}