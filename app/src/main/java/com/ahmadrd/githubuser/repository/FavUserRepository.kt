package com.ahmadrd.githubuser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ahmadrd.githubuser.data.database.*

class FavUserRepository private constructor( private val favoriteDao: FavoriteDao ) {

    fun getFavUsers(): LiveData<List<FavoriteUser>> = favoriteDao.getFavUsers().asLiveData()

    fun setFavUsers(username: String): LiveData<List<FavoriteUser>> =
        favoriteDao.setFavUsers(username).asLiveData()

    suspend fun insertUser(user: FavoriteUser) {
        favoriteDao.insert(user)
    }

    suspend fun deleteUser(id: Int) {
        favoriteDao.delete(id)
    }

    companion object {
        @Volatile
        private var instance: FavUserRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao
        ): FavUserRepository = instance ?: synchronized(this) {
            instance ?: FavUserRepository(favoriteDao)
        }.also { instance = it }
    }

}