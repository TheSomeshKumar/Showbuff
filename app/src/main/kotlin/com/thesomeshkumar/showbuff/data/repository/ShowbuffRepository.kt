package com.thesomeshkumar.showbuff.data.repository

import com.thesomeshkumar.showbuff.data.common.Result
import com.thesomeshkumar.showbuff.data.datasource.remote.RemoteDataSource
import com.thesomeshkumar.showbuff.data.response.mapToUI
import com.thesomeshkumar.showbuff.ui.models.MovieUI
import com.thesomeshkumar.showbuff.ui.models.TvShowUI
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShowbuffRepository @Inject constructor(
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
