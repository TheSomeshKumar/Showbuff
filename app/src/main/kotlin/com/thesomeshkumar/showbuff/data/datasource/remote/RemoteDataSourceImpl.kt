package com.thesomeshkumar.showbuff.data.datasource.remote

import com.thesomeshkumar.showbuff.data.common.Result
import com.thesomeshkumar.showbuff.data.common.handleResponse
import com.thesomeshkumar.showbuff.data.response.MovieDTO
import com.thesomeshkumar.showbuff.data.response.TVShowDTO

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
