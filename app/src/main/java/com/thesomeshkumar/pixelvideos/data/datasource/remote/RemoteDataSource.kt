package com.thesomeshkumar.pixelvideos.data.datasource.remote

import com.thesomeshkumar.pixelvideos.data.common.Result
import com.thesomeshkumar.pixelvideos.data.response.VideoDTO

interface RemoteDataSource {
    suspend fun getPopularVideos(): Result<List<VideoDTO>>
}
