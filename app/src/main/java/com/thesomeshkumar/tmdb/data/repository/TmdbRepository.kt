package com.thesomeshkumar.tmdb.data.repository

import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.datasource.remote.RemoteDataSource
import com.thesomeshkumar.tmdb.data.response.mapToUI
import com.thesomeshkumar.tmdb.ui.models.MovieUI
import com.thesomeshkumar.tmdb.ui.models.TvShowUI
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TmdbRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getPopularTvShows(): Flow<Result<List<TvShowUI>>> {
        return flow {
            remoteDataSource.getPopularTvShows().run {
                when (this) {
                    is Result.Loading -> emit(Result.Loading)
                    is Result.Success -> emit(Result.Success(response.results.map { it.mapToUI() }))
                    is Result.Error -> emit(Result.Error(exception))
                }
            }
        }
    }

    suspend fun getPopularMovies(): Flow<Result<List<MovieUI>>> {
        return flow {
            remoteDataSource.getPopularMovies().run {
                when (this) {
                    is Result.Loading -> emit(Result.Loading)
                    is Result.Success -> emit(Result.Success(response.results.map { it.mapToUI() }))
                    is Result.Error -> emit(Result.Error(exception))
                }
            }
        }
    }
}
