package pl.training.goodweather.examples

import retrofit2.http.GET
import retrofit2.http.Path

interface MockUsersApi {

    @GET("users")
    suspend fun getUsersIds(): List<Long>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Long): UserDto

}