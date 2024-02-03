package com.ahmadrd.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.ahmadrd.githubuser.data.response.ItemsItem
import com.ahmadrd.githubuser.data.response.ResponseUserGithub
import com.ahmadrd.githubuser.data.retrofit.ApiConfig
import retrofit2.*

class MainViewModel : ViewModel() {

    private val _userList = MutableLiveData<List<ItemsItem>?>()
    val userList : LiveData<List<ItemsItem>?> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean> = _error

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        getUser()
    }

    private fun getUser(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userList.postValue(responseBody)
                    }
                }else {
                    Log.e(TAG, "onFailure 1 getUser: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure 2 getUser: ${t.message}")
            }
        })
    }

    fun getSearchUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<ResponseUserGithub>{
            override fun onResponse(call: Call<ResponseUserGithub>, response: Response<ResponseUserGithub>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _userList.value = response.body()?.items
                }else {
                    Log.e(TAG, "onFailure 1 getSearchUser: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUserGithub>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure 2 getSearchUser: ${t.message}")
            }
        })
    }

    fun toastError(){
        _error.value = false
    }

}