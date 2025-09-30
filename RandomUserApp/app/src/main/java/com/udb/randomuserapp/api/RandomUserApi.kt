package com.udb.randomuserapp.api

import com.udb.randomuserapp.model.RandomUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("api/")
    fun getUsers(
        @Query("results") results: Int = 20, // Número de usuarios a obtener
        @Query("seed") seed: String = "randomuserapp" // Seed para resultados consistentes
    ): Call<RandomUserResponse>

    // Método adicional para obtener más usuarios (paginación)
    @GET("api/")
    fun getUsersWithPage(
        @Query("results") results: Int = 20,
        @Query("page") page: Int = 1,
        @Query("seed") seed: String = "randomuserapp"
    ): Call<RandomUserResponse>
}