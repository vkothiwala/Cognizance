package com.example.cognizance.data.remote.models

import com.google.gson.annotations.SerializedName

data class ApiMoviesResponse(
    @SerializedName("results") var results: List<ApiMovie>,
    @SerializedName("page") var page: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
)
