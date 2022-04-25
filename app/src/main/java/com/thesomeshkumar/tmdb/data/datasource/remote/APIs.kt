package com.thesomeshkumar.tmdb.data.datasource.remote

import com.thesomeshkumar.tmdb.data.response.BaseResponse
import com.thesomeshkumar.tmdb.data.response.TVShowDTO
import com.thesomeshkumar.tmdb.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface APIs {
    @GET(Constants.POPULAR_TV_SHOW_URL)
    suspend fun getPopularVideos(): Response<BaseResponse<TVShowDTO>>
}
