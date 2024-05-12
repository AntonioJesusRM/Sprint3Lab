package com.example.sprint3lab.data.retrofit

import com.example.sprint3lab.data.model.DetailsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("popular")
    fun getPopularityMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<MoviesResponse?>

    @GET("{movie_id}")
    fun getDetailsMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<DetailsModel?>
}