package com.example.sprint3lab.data.useCase

import com.example.sprint3lab.data.Constant
import com.example.sprint3lab.data.model.DetailsModel
import com.example.sprint3lab.data.retrofit.RetrofitClient
import retrofit2.Call
import javax.inject.Inject

class GetDetailsMovieUseCase @Inject constructor(private val retrofitClient: RetrofitClient) {
    operator fun invoke(id: Int): Call<DetailsModel?> {
        return retrofitClient.moviesApiService.getDetailsMovie(
            id,
            Constant.API_KEY,
            Constant.LANGUAGE
        )
    }
}