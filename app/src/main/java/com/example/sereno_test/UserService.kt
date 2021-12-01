package com.example.sereno_test

import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("api/v1/8uk4grufunr28")
    fun getUserInfo(): Call<Array<UserResponse>>
}

