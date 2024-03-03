package com.example.cognizance.data.remote.models

import com.google.gson.annotations.SerializedName

data class ApiMovieDetails(
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    val budget: Long,
    val genres: List<ApiGenre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("production_companies") val productionCompanies: List<ApiProductionCompany>,
    @SerializedName("production_countries") val productionCountries: List<ApiProductionCountry>,
    @SerializedName("release_date") val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    @SerializedName("spoken_languages") val spokenLanguages: List<ApiSpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Long
)

data class ApiGenre(
    val id: Long,
    val name: String
)

data class ApiProductionCompany(
    val id: Long,
    @SerializedName("logo_path") val logoPath: String?,
    val name: String,
    @SerializedName("origin_country") val originCountry: String
)

data class ApiProductionCountry(
    @SerializedName("iso_3166_1") val iso31661: String,
    val name: String
)

data class ApiSpokenLanguage(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso6391: String,
    val name: String
)
