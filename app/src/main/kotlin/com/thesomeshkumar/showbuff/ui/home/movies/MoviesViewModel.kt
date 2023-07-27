package com.thesomeshkumar.showbuff.ui.home.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.showbuff.data.common.Result
import com.thesomeshkumar.showbuff.data.repository.ShowbuffRepository
import com.thesomeshkumar.showbuff.ui.models.HomeMediaUI
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
    private val _resultPopularMovies = MutableStateFlow<Result<List<HomeMediaUI>>>(Result.Loading)
    val popularMovies: StateFlow<Result<List<HomeMediaUI>>> = _resultPopularMovies.asStateFlow()

    private val _resultNowPlayingMovies =
        MutableStateFlow<Result<List<HomeMediaUI>>>(Result.Loading)
    val nowPlayingMovies: StateFlow<Result<List<HomeMediaUI>>> =
        _resultNowPlayingMovies.asStateFlow()

    init {
        getPopularMovies()
        getNowPlayingMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            showbuffRepository.getPopularMovies().collect { movies ->
                _resultPopularMovies.value = movies
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            showbuffRepository.getNowPlayingMovies().collect { movies ->
                _resultNowPlayingMovies.value = movies
            }
        }
    }
}
