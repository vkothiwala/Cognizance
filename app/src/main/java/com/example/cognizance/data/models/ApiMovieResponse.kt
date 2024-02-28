package com.example.cognizance.data.models

import com.google.gson.annotations.SerializedName

data class ApiMovieResponse(
    @SerializedName("dates") var dates: Dates,
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: List<ApiMovie>,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
) {
    data class Dates(
        var maximum: String,
        var minimum: String
    )
}
