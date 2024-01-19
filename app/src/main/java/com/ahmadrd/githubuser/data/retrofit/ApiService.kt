package com.ahmadrd.githubuser.data.retrofit

import com.ahmadrd.githubuser.data.response.ItemsItem
import com.ahmadrd.githubuser.data.response.DetailUserResponse
import com.ahmadrd.githubuser.data.response.ResponseUserGithub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(@Query("q") q: String): Call<ResponseUserGithub>

    @GET("users")
    fun getUsers(): Call<List<ItemsItem>>

    @GET("users/{username}")
    fun getDetailUsers(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}