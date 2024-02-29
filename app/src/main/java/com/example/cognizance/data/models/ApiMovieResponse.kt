package com.example.cognizance.data.models

import com.google.gson.annotations.SerializedName

data class ApiMovieResponse(
    @SerializedName("results") var results: List<ApiMovie>,
    @SerializedName("page") var page: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("dates") var dates: Dates,
) {
    data class Dates(
        var maximum: String,
        var minimum: String
    )
}
