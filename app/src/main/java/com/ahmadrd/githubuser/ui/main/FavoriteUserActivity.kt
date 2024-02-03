package com.ahmadrd.githubuser.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrd.githubuser.data.response.DetailUserResponse
import com.ahmadrd.githubuser.databinding.ActivityFavoriteUserBinding
import com.ahmadrd.githubuser.ui.adapter.FavUserAdapter
import com.ahmadrd.githubuser.ui.viewmodel.FavoriteViewModel
import com.ahmadrd.githubuser.utils.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavUserAdapter
    private val listUser = ArrayList<DetailUserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        setRecyclerViewData()

        favoriteViewModel.getFavUsers().observe(this) { users ->
            val items = arrayListOf<DetailUserResponse>()
            users.map {
                val item = DetailUserResponse(it.id, it.username, it.avatarUrl)
                items.add(item)
            }
            adapter.setListUser(items)
        }
    }

    private fun setRecyclerViewData() {
        with(binding) {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
        }

        adapter = FavUserAdapter(listUser)
        binding.rvUser.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }
}