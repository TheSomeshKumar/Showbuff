package com.thesomeshkumar.tmdb.data.datasource.remote

import com.thesomeshkumar.tmdb.R
import com.thesomeshkumar.tmdb.data.common.RemoteSourceException
import com.thesomeshkumar.tmdb.data.common.RequestErrorHandler
import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.response.MoviesDTO
import com.thesomeshkumar.tmdb.data.response.TVShowDTO

class RemoteDataSourceImpl(private val apis: ApiService) : RemoteDataSource {
    override suspend fun getPopularTvShows(): Result<List<TVShowDTO>> {
        return try {
            val result = apis.getPopularTvShows()
            if (result.isSuccessful) {
                result.body()?.let {
                    if (it.results.isNotEmpty()) {
                        Result.Success(it.results)
                    } else {
                        Result.Error(RemoteSourceException.Server(R.string.no_results_found))
                    }
                } ?: run {
                    Result.Error(
                        RemoteSourceException.Unexpected(R.string.error_unexpected_message)
                    )
                }
            } else {
                Result.Error(RemoteSourceException.Server(R.string.error_server_unexpected_message))
            }
        } catch (e: Exception) {
            Result.Error(RequestErrorHandler.getRequestError(e))
        }
    }

    override suspend fun getPopularMovies(): Result<List<MoviesDTO>> {
        return try {
            val result = apis.getPopularMovies()
            if (result.isSuccessful) {
                result.body()?.let {
                    if (it.results.isNotEmpty()) {
                        Result.Success(it.results)
                    } else {
                        Result.Error(RemoteSourceException.Server(R.string.no_results_found))
                    }
                } ?: run {
                    Result.Error(
                        RemoteSourceException.Unexpected(R.string.error_unexpected_message)
                    )
                }
            } else {
                Result.Error(RemoteSourceException.Server(R.string.error_server_unexpected_message))
            }
        } catch (e: Exception) {
            Result.Error(RequestErrorHandler.getRequestError(e))
        }
    }
}
