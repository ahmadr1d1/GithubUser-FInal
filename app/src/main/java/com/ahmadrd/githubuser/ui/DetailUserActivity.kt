package com.ahmadrd.githubuser.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.ahmadrd.githubuser.R
import com.ahmadrd.githubuser.adapter.SectionsPagerAdapter
import com.ahmadrd.githubuser.data.response.DetailUserResponse
import com.ahmadrd.githubuser.databinding.ActivityDetailUserBinding
import com.ahmadrd.githubuser.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel : DetailViewModel by viewModels()

    companion object {

        private const val TAG = "DetailActivity"
        const val EXTRA_USER = "DETAIL"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataUserName = intent.getStringExtra(EXTRA_USER)
        if (dataUserName != null) {
            detailViewModel.setDetailUser(username = dataUserName)
            detailViewModel.detailUser.observe(this) {
                findUser(it)
            }
        }

        detailViewModel.error.observe(this) {
            if (it) {
                Toast.makeText(this, "Failed to load API", Toast.LENGTH_LONG).show()
            }
            detailViewModel.toastError()
        }


        val sectionsViewPager  = SectionsPagerAdapter(this)
        sectionsViewPager.username = dataUserName.toString()
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsViewPager
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    @SuppressLint("SetTextI18n")
    private fun findUser(user: DetailUserResponse) {
        binding.tvNameUser.text = user.login
        val img = user.avatarUrl
        Glide.with(this)
            .load(img)
            .into(binding.imgUser)
        binding.tvUsernameUser.text = user.name
        binding.tvFollowersUser.text = user.followers.toString() + " Followers"
        binding.tvFollowingUser.text = user.following.toString() + " Following"
    }
}