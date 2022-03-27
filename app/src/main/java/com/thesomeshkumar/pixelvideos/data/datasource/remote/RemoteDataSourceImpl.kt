package com.thesomeshkumar.pixelvideos.data.datasource.remote

import com.thesomeshkumar.pixelvideos.R
import com.thesomeshkumar.pixelvideos.data.common.RemoteSourceException
import com.thesomeshkumar.pixelvideos.data.common.RequestErrorHandler
import com.thesomeshkumar.pixelvideos.data.common.Result
import com.thesomeshkumar.pixelvideos.data.response.VideoDTO

class RemoteDataSourceImpl(private val apis: APIs) : RemoteDataSource {
    override suspend fun getPopularVideos(): Result<List<VideoDTO>> {
        return try {
            val result = apis.getPopularVideos()
            if (result.isSuccessful) {
                result.body()?.let {
                    if (it.videos.isNotEmpty()) {
                        Result.Success(it.videos)
                    } else {
                        Result.Error(RemoteSourceException.Server(R.string.no_results_found))
                    }
                } ?: run {
                    Result.Error(
                        RemoteSourceException.Unexpected(R.string.error_unexpected_message)
                    )
                }
            } else {
                Result.Error(RemoteSourceException.Server(R.string.error_server_unexpected_message))
            }
        } catch (e: Exception) {
            Result.Error(RequestErrorHandler.getRequestError(e))
        }
    }
}
