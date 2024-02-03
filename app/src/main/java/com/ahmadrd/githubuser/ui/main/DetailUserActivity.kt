package com.ahmadrd.githubuser.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.ahmadrd.githubuser.R
import com.ahmadrd.githubuser.data.database.FavoriteUser
import com.ahmadrd.githubuser.ui.adapter.SectionsPagerAdapter
import com.ahmadrd.githubuser.data.response.DetailUserResponse
import com.ahmadrd.githubuser.databinding.ActivityDetailUserBinding
import com.ahmadrd.githubuser.ui.viewmodel.DetailViewModel
import com.ahmadrd.githubuser.ui.viewmodel.FavoriteViewModel
import com.ahmadrd.githubuser.utils.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ahmadrd.githubuser.utils.Result

class DetailUserActivity : AppCompatActivity() {

    private var _detailUserBinding: ActivityDetailUserBinding? = null
    private val detailUserBinding get() = _detailUserBinding!!
    private val detailViewModel : DetailViewModel by viewModels()
    private var favoriteUser: FavoriteUser? = null
    private var ivFavoriteState = false

    companion object {

        const val EXTRA_USER = "DETAIL"
        const val FOLLOWERS = "Followers"
        const val FOLLOWING = "Following"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

        val dataUserName = intent.getStringExtra(EXTRA_USER)
        if (dataUserName != null) {
            detailViewModel.setDetailUser(username = dataUserName)
            detailViewModel.detailUser.observe(this) { user ->
                findUser(user)
                favoriteUser.let {
                    it?.id = user.id
                    it?.username = user.login!!
                    it?.avatarUrl = user.avatarUrl!!
                }
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
        val viewPager = detailUserBinding.viewPager
        viewPager.adapter = sectionsViewPager
        val tabs = detailUserBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        // For Favorite User
        viewModel.getDetailFavoriteUser(dataUserName.toString())
        viewModel.result.observe(this) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        detailUserBinding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        detailUserBinding.progressBar.visibility = View.GONE
                        detailUserBinding.ivFavorite.setOnClickListener {
                            if (!ivFavoriteState) {
                                ivFavoriteState = true
                                detailUserBinding.ivFavorite.setImageResource(R.drawable.baseline_favorite_24)

                                favoriteUser = FavoriteUser(
                                    result.data.id,
                                    result.data.login,
                                    result.data.avatarUrl
                                )
                                viewModel.insertUser(favoriteUser as FavoriteUser)
                                showToast(getString(R.string.add))
                            } else {
                                ivFavoriteState = false
                                detailUserBinding.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                                viewModel.deleteUser(result.data.id)
                                showToast(getString(R.string.delete))
                            }
                        }
                    }
                    is Result.Error -> {
                        detailUserBinding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Something went wrong " + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }



        if (dataUserName != null) {
            viewModel.setFavUsers(dataUserName).observe(this) { users ->
                ivFavoriteState = users.isNotEmpty()
                if (ivFavoriteState) {
                    detailUserBinding.ivFavorite.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    detailUserBinding.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }
    }


    private fun findUser(user: DetailUserResponse) {
        detailUserBinding.tvNameUser.text = user.login
        val img = user.avatarUrl
        Glide.with(this)
            .load(img)
            .into(detailUserBinding.imgUser)
        detailUserBinding.tvUsernameUser.text = user.name
        detailUserBinding.tvFollowers.text = FOLLOWERS
        detailUserBinding.tvFollowing.text = FOLLOWING
        detailUserBinding.tvFollowersUser.text = user.followers.toString()
        detailUserBinding.tvFollowingUser.text = user.following.toString()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _detailUserBinding = null
    }
}