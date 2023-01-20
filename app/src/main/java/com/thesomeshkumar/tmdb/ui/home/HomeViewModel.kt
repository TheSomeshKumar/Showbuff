package com.thesomeshkumar.tmdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.repository.TmdbRepository
import com.thesomeshkumar.tmdb.ui.models.Movie
import com.thesomeshkumar.tmdb.ui.models.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var tmdbRepository: TmdbRepository) :
    ViewModel() {

    private val _resultTvShow = Channel<Result<List<TvShow>>>(Channel.BUFFERED)
    val resultTvShow: Flow<Result<List<TvShow>>> = _resultTvShow.receiveAsFlow()

    private val _resultMovies = Channel<Result<List<Movie>>>(Channel.BUFFERED)
    val resultMovies: Flow<Result<List<Movie>>> = _resultMovies.receiveAsFlow()

    fun getPopularTvShow() {
        viewModelScope.launch(Dispatchers.IO) {
            tmdbRepository.getPopularTvShows().collect {
                _resultTvShow.send(it)
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            tmdbRepository.getPopularMovies().collect {
                _resultMovies.send(it)
            }
        }
    }
}
