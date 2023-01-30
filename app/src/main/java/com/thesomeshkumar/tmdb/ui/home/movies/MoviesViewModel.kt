package com.thesomeshkumar.tmdb.ui.home.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.repository.TmdbRepository
import com.thesomeshkumar.tmdb.ui.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private var tmdbRepository: TmdbRepository) :
    ViewModel() {
    private val _resultMovies = MutableStateFlow<Result<List<Movie>>>(Result.Loading)
    val resultMovies: StateFlow<Result<List<Movie>>> = _resultMovies.asStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            tmdbRepository.getPopularMovies().collect { movies ->
                _resultMovies.value = movies
            }
        }
    }
}
