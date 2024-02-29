package com.example.cognizance.domain.repositories

import androidx.paging.PagingData
import com.example.cognizance.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    val movies: Flow<PagingData<Movie>>
    suspend fun onFavouriteClick(movieId: Int)
}
