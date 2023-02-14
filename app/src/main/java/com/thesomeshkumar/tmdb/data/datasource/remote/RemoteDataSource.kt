package com.thesomeshkumar.tmdb.data.datasource.remote

import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.response.MovieDTO
import com.thesomeshkumar.tmdb.data.response.TVShowDTO

interface RemoteDataSource {
    suspend fun getPopularTvShows(): Result<TVShowDTO>
    suspend fun getPopularMovies(): Result<MovieDTO>
}
