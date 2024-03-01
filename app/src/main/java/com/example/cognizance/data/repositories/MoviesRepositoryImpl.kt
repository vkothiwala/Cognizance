package com.example.cognizance.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cognizance.data.mappers.toMovie
import com.example.cognizance.data.models.ApiMovie
import com.example.cognizance.data.models.EntityMovie
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class MoviesRepositoryImpl @Inject constructor(
    @Named("movies_paging_source") pagingSource: Pager<Int, ApiMovie>,
    @Named("movies_remote_mediator") remoteMediator: Pager<Int, EntityMovie>
) : MoviesRepository {

    override val movies: Flow<PagingData<Movie>> = remoteMediator.flow
        .map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }
}
