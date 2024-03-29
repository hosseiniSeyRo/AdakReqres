package com.rhosseini.adakreqres.model.webService


import com.rhosseini.adakreqres.model.webService.model.AddNewUserResponse
import com.rhosseini.adakreqres.model.webService.model.UpdateUserResponse
import com.rhosseini.adakreqres.model.webService.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ReqresService {

    @GET("/api/users")
    fun getAllUser(@Query("page") page: Int): Call<UserResponse>

    @DELETE("/api/users/{id}")
    fun deleteUser(@Path("id") userId: Int): Call<Void>

    @POST("/api/users")
    @FormUrlEncoded
    fun addNewUser(@Field("name") name: String, @Field("job") job: String): Call<AddNewUserResponse>

    @PUT("/api/users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: UpdateUserResponse): Call<UpdateUserResponse>
}