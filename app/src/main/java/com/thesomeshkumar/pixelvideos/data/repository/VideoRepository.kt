package com.thesomeshkumar.pixelvideos.data.repository

import com.thesomeshkumar.pixelvideos.data.common.Result
import com.thesomeshkumar.pixelvideos.data.common.onFlowStarts
import com.thesomeshkumar.pixelvideos.data.datasource.remote.RemoteDataSource
import com.thesomeshkumar.pixelvideos.data.response.mapToUI
import com.thesomeshkumar.pixelvideos.ui.models.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getPopularVideos(): Flow<Result<List<Video>>> {
        return flow {
            remoteDataSource.getPopularVideos().run {
                when (this) {
                    is Result.Success -> {
                        emit(Result.Success(response.map { it.mapToUI() }))
                    }
                    is Result.Error -> {
                        emit(Result.Error(exception))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
            .onFlowStarts()
    }
}
