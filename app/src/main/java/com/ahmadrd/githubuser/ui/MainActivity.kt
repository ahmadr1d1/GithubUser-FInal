package com.ahmadrd.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.githubuser.R
import com.ahmadrd.githubuser.adapter.GetUserAdapter
import com.ahmadrd.githubuser.data.response.DetailUserResponse
import com.ahmadrd.githubuser.data.response.ItemsItem
import com.ahmadrd.githubuser.databinding.ActivityMainBinding
import com.ahmadrd.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var rvUser: RecyclerView

    companion object {
        const val EXTRA_DATA = "DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.userList.observe(this){ userList->
            setUserData(userList)
            /**
             * Ini catatan
             */
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

//        rvUser = binding.rvUser
//        rvUser.setHasFixedSize(true)
//        showRecyclerList()

        // SearchBar
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    mainViewModel.getSearchUser(searchBar.text.toString())
                    searchView.hide()
                    Toast.makeText(this@MainActivity, "Searching " + searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }

        // Settings Activity
        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_settings -> {
                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    private fun setUserData(userList:List<ItemsItem>){
        val adapter = GetUserAdapter()
        adapter.submitList(userList)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

//    private fun showSelectedUser(user: ItemsItem) {
//        val intentToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
//        intentToDetail.putExtra("DETAIL", user.login)
//        startActivity(intentToDetail)
//    }

    private fun showRecyclerList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = GetUserAdapter()
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : GetUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailUserResponse) {
                val intentDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                intentDetail.putExtra(DetailUserActivity.EXTRA_USER, data.login)
                startActivity(intentDetail)
            }
        })
    }
}