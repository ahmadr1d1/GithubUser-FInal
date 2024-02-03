package com.ahmadrd.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.ahmadrd.githubuser.data.database.FavoriteUser
import com.ahmadrd.githubuser.data.response.DetailUserResponse
import com.ahmadrd.githubuser.data.retrofit.ApiConfig
import com.ahmadrd.githubuser.repository.FavUserRepository
import com.ahmadrd.githubuser.utils.Result
import kotlinx.coroutines.*

class FavoriteViewModel(private val favUserRepository: FavUserRepository) : ViewModel() {

    private val _result = MutableLiveData<Result<DetailUserResponse>>()
    val result: LiveData<Result<DetailUserResponse>> = _result
    fun getDetailFavoriteUser(username: String) {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = ApiConfig.getApiService().getDetailFavoriteUser(username)
                _result.value = Result.Success(response)
            } catch (e: Exception) {
                Log.e(TAG, "getDetailUser : ${e.message.toString()}")
            }
        }
    }

    fun getFavUsers(): LiveData<List<FavoriteUser>> = favUserRepository.getFavUsers()

    fun setFavUsers(username: String): LiveData<List<FavoriteUser>> =
        favUserRepository.setFavUsers(username)

    fun insertUser(user: FavoriteUser) {
        viewModelScope.launch {
            favUserRepository.insertUser(user)
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            favUserRepository.deleteUser(id)
        }
    }

    companion object {
        const val TAG = "FavoriteViewModel"
    }
}