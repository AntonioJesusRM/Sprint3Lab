package com.example.sprint3lab.data.retrofit

import com.example.sprint3lab.data.model.MoviesModel
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    var result: List<MoviesModel>
)
