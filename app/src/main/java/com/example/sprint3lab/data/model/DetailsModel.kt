package com.example.sprint3lab.data.model

import com.google.gson.annotations.SerializedName

data class DetailsModel(
    @SerializedName("title")
    var title: String,
    @SerializedName("poster_path")
    var poster: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("genres")
    var genres: List<GenresModel>
)
