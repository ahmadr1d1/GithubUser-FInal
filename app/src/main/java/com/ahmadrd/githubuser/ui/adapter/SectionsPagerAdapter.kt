package com.ahmadrd.githubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ahmadrd.githubuser.ui.main.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username =  ""
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.USER_POSITION, position + 1)
            putString(FollowFragment.USERNAME, username)
        }

        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}