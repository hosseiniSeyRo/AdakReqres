package com.rhosseini.adakreqres.model.webService


import com.rhosseini.adakreqres.model.webService.model.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ReqresService {

    @GET("/api/users")
    fun getAllUser(@Query("page") page: Int): Call<UserResponse>
}