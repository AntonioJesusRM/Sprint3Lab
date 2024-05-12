package com.example.sprint3lab.data.retrofit

import com.example.sprint3lab.data.Constant
import retrofit2.Call
import javax.inject.Inject

class GetListMoviesUseCase @Inject constructor(private val retrofitClient: RetrofitClient) {
    operator fun invoke(page: Int): Call<MoviesResponse?> {
        return retrofitClient.moviesApiService.getPopularityMovies(
            Constant.API_KEY,
            page,
            Constant.LANGUAGE
        )
    }
}