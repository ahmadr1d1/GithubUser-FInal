package com.ahmadrd.githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.ahmadrd.githubuser.R
import com.ahmadrd.githubuser.ui.adapter.GetUserAdapter
import com.ahmadrd.githubuser.data.response.ItemsItem
import com.ahmadrd.githubuser.databinding.ActivityMainBinding
import com.ahmadrd.githubuser.ui.viewmodel.MainViewModel
import com.ahmadrd.githubuser.ui.viewmodel.SettingThemeViewModel
import com.ahmadrd.githubuser.utils.SettingPreferences
import com.ahmadrd.githubuser.utils.SettingViewModelFactory
import com.ahmadrd.githubuser.utils.dataStore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.userList.observe(this){ userList->
            if (userList != null) {
                setUserData(userList)
            }
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.error.observe(this) {
            if (it) {
                Toast.makeText(this, "Failed to load API", Toast.LENGTH_LONG).show()
            }
            mainViewModel.toastError()
        }

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

        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_settings -> {
                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(intent)
                }
                R.id.action_favorite -> {
                    val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        // For Dark Theme Const
        val settingPreferences = SettingPreferences.getInstance(dataStore)
        val themeViewModel =
            ViewModelProvider(this,
                SettingViewModelFactory(settingPreferences))[SettingThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
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

}