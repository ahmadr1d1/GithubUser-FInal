package com.ahmadrd.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadrd.githubuser.data.response.ItemsItem
import com.ahmadrd.githubuser.data.response.ResponseUserGithub
import com.ahmadrd.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    // Encapsulation Variable
    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList : LiveData<List<ItemsItem>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchUser= MutableLiveData<ResponseUserGithub>()
    val searchUser: LiveData<ResponseUserGithub> = _searchUser

    companion object{
        private const val TAG = "MainViewModel"
//        private const val SEARCH_USER = "Fikri"
    }

    init {
        getUser()
    }

    fun getUser(){
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
                    Log.e(TAG, "onFailure Pertama getUser: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure Kedua getUser: ${t.message}")
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
                    Log.e(TAG, "onFailure Pertama getSearchUser: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUserGithub>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure Kedua getSearchUser: ${t.message}")
            }
        })
    }

}