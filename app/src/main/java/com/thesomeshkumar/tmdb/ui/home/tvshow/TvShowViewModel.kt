package com.thesomeshkumar.tmdb.ui.home.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.repository.TmdbRepository
import com.thesomeshkumar.tmdb.ui.models.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private var tmdbRepository: TmdbRepository) :
    ViewModel() {

    private val _resultTvShow = MutableStateFlow<Result<List<TvShow>>>(Result.Loading)
    val resultTvShow: Flow<Result<List<TvShow>>> = _resultTvShow.asStateFlow()

    init {
        getPopularTvShow()
    }

    private fun getPopularTvShow() {
        viewModelScope.launch(Dispatchers.IO) {
            tmdbRepository.getPopularTvShows().collect { tvShows ->
                _resultTvShow.value = tvShows
            }
        }
    }
}
