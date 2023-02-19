package com.thesomeshkumar.tmdb.data.datasource.remote

import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.common.handleResponse
import com.thesomeshkumar.tmdb.data.response.MovieDTO
import com.thesomeshkumar.tmdb.data.response.TVShowDTO

class RemoteDataSourceImpl(private val apis: ApiService) : RemoteDataSource {
    override suspend fun getPopularTvShows(): Result<TVShowDTO> =
        handleResponse {
            apis.getPopularTvShows()
        }

    override suspend fun getPopularMovies(): Result<MovieDTO> =
        handleResponse {
            apis.getPopularMovies()
        }
}
