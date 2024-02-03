package com.ahmadrd.githubuser.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmadrd.githubuser.ui.viewmodel.SettingThemeViewModel

class SettingViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingThemeViewModel::class.java)) {
            return SettingThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}