package com.example.cognizance.data.remote.models

import com.google.gson.annotations.SerializedName

data class ApiMovieVideos(
    @SerializedName("id") val id: Int?,
    @SerializedName("results") val moviesVideos: List<MovieVideo> = emptyList()
) {
    data class MovieVideo(
        @SerializedName("site") val site: String,
        @SerializedName("size") val size: Int,
        @SerializedName("iso_3166_1") val iso31661: String,
        @SerializedName("name") val name: String,
        @SerializedName("official") val official: Boolean,
        @SerializedName("id") val id: String,
        @SerializedName("type") val type: String,
        @SerializedName("published_at") val publishedAt: String,
        @SerializedName("iso_639_1") val iso6391: String,
        @SerializedName("key") val key: String
    )
}
