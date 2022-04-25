package com.thesomeshkumar.tmdb.data.repository

import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.common.onFlowStarts
import com.thesomeshkumar.tmdb.data.datasource.remote.RemoteDataSource
import com.thesomeshkumar.tmdb.data.response.mapToUI
import com.thesomeshkumar.tmdb.ui.models.TvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TmdbRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getPopularTvShow(): Flow<Result<List<TvShow>>> {
        return flow {
            remoteDataSource.getPopularTvShow().run {
                when (this) {
                    is Result.Success -> {
                        emit(Result.Success(response.map { it.mapToUI() }))
                    }
                    is Result.Error -> {
                        emit(Result.Error(exception))
                    }
                }
            }
        }.onFlowStarts()
    }
}
