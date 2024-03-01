package com.example.cognizance.domain.repositories

import androidx.paging.PagingData
import com.example.cognizance.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    val movies: Flow<PagingData<Movie>>
}