package com.ahmadrd.githubuser.di

import android.content.Context
import com.ahmadrd.githubuser.data.database.FavUserDatabase
import com.ahmadrd.githubuser.repository.FavUserRepository

object Injection {
    fun provideRepository(context: Context): FavUserRepository {
        val database = FavUserDatabase.getInstance(context)
        val dao = database.favoriteDao()
        return FavUserRepository.getInstance(dao)
    }
}