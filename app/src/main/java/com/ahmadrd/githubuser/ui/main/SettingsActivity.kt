package com.ahmadrd.githubuser.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.ahmadrd.githubuser.databinding.ActivitySettingsBinding
import com.ahmadrd.githubuser.ui.viewmodel.SettingThemeViewModel
import com.ahmadrd.githubuser.utils.SettingPreferences
import com.ahmadrd.githubuser.utils.SettingViewModelFactory
import com.ahmadrd.githubuser.utils.dataStore

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = binding.switchTheme

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingThemeViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[SettingThemeViewModel::class.java]
        settingThemeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingThemeViewModel.saveThemeSetting(isChecked)
        }
    }
}