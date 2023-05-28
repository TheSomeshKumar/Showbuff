package com.thesomeshkumar.showbuff.data.datasource.remote

import com.thesomeshkumar.showbuff.data.response.MovieDTO
import com.thesomeshkumar.showbuff.data.response.TVShowDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    /*-- Movie APIs-- */

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int = 1): Response<MovieDTO>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int = 1): Response<MovieDTO>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): Response<MovieDTO>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): Response<MovieDTO>

    /*-- Tv Show APIs-- */

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(@Query("page") page: Int = 1): Response<TVShowDTO>

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("page") page: Int = 1): Response<TVShowDTO>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(@Query("page") page: Int = 1): Response<TVShowDTO>
}
