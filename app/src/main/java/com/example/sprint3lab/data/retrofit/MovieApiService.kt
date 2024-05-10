package com.example.sprint3lab.data.retrofit

import com.example.sprint3lab.data.model.PopularityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    @GET("popular")
    fun getPopularityMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<PopularityResponse?>
}
