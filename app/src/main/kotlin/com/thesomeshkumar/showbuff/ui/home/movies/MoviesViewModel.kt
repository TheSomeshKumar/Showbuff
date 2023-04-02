package com.thesomeshkumar.showbuff.ui.home.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.showbuff.data.common.Result
import com.thesomeshkumar.showbuff.data.repository.ShowbuffRepository
import com.thesomeshkumar.showbuff.ui.models.MovieUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesViewModel @Inject constructor(private var showbuffRepository: ShowbuffRepository) :
    ViewModel() {
    private val _resultMovies = MutableStateFlow<Result<List<MovieUI>>>(Result.Loading)
    val resultMovies: StateFlow<Result<List<MovieUI>>> = _resultMovies.asStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            showbuffRepository.getPopularMovies().collect { movies ->
                _resultMovies.value = movies
            }
        }
    }
}
