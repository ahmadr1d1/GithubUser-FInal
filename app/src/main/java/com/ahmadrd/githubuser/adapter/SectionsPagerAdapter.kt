package com.ahmadrd.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ahmadrd.githubuser.data.response.ItemsItem
import com.ahmadrd.githubuser.databinding.FragmentFollowBinding
import com.ahmadrd.githubuser.databinding.ItemUserBinding
import com.ahmadrd.githubuser.ui.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

//    private lateinit var user: ItemsItem

    var username =  ""
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.USER_POSITION, position + 1)
            putString(FollowFragment.USERNAME, username)
        }

        return fragment
    }

//    class UserViewHolder(var binding: FragmentFollowBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(user: ItemsItem) {
//
//        }
//    }

    override fun getItemCount(): Int {
        return 2
    }

}