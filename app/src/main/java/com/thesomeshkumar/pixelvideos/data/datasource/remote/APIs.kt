package com.thesomeshkumar.pixelvideos.data.datasource.remote

import com.thesomeshkumar.pixelvideos.data.response.PopularVideosDTO
import com.thesomeshkumar.pixelvideos.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface APIs {
    @GET(Constants.POPULAR_VIDEO_URL)
    suspend fun getPopularVideos(): Response<PopularVideosDTO>
}
