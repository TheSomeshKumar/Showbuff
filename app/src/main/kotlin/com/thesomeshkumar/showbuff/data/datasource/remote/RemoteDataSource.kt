package com.thesomeshkumar.showbuff.data.datasource.remote

import com.thesomeshkumar.showbuff.data.common.Result
import com.thesomeshkumar.showbuff.data.response.MovieDTO
import com.thesomeshkumar.showbuff.data.response.TVShowDTO

interface RemoteDataSource {
    suspend fun getPopularTvShows(): Result<TVShowDTO>
    suspend fun getAiringTodayTvShows(): Result<TVShowDTO>
    suspend fun getPopularMovies(): Result<MovieDTO>
    suspend fun getNowPlayingMovies(): Result<MovieDTO>
}
