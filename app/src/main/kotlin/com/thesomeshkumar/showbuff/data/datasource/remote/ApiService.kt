package com.thesomeshkumar.showbuff.data.datasource.remote

import com.thesomeshkumar.showbuff.data.response.MovieDTO
import com.thesomeshkumar.showbuff.data.response.TVShowDTO
import com.thesomeshkumar.showbuff.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.POPULAR_TV_SHOW_URL)
    suspend fun getPopularTvShows(): Response<TVShowDTO>

    @GET(Constants.POPULAR_MOVIE_URL)
    suspend fun getPopularMovies(): Response<MovieDTO>
}
