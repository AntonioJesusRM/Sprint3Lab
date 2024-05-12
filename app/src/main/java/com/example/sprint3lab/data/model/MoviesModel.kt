package com.example.sprint3lab.data.model

import com.google.gson.annotations.SerializedName

data class MoviesModel(
    @SerializedName("id")
    var id: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("release_date")
    var date: String,
    @SerializedName("poster_path")
    var poster: String,
    @SerializedName("vote_average")
    var note: String,
)
