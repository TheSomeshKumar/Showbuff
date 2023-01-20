package com.thesomeshkumar.tmdb.data.datasource.remote

import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.response.MoviesDTO
import com.thesomeshkumar.tmdb.data.response.TVShowDTO

interface RemoteDataSource {
    suspend fun getPopularTvShows(): Result<List<TVShowDTO>>
    suspend fun getPopularMovies(): Result<List<MoviesDTO>>
}
