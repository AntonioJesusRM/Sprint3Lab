package com.example.sprint3lab.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    @GET("popular")
    fun getPopularityMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<MoviesResponse?>
}