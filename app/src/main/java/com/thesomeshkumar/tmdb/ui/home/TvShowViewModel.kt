package com.thesomeshkumar.tmdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.tmdb.data.common.Result
import com.thesomeshkumar.tmdb.data.repository.TmdbRepository
import com.thesomeshkumar.tmdb.ui.models.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private var tmdbRepository: TmdbRepository) :
    ViewModel() {
    private var _isVideoFullScreen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isVideoFullScreen: StateFlow<Boolean> = _isVideoFullScreen.asStateFlow()

    init {
        getPopularTvShow()
    }

    private val _resultTvShow = Channel<Result<List<TvShow>>>(Channel.BUFFERED)
    val resultTvShow: Flow<Result<List<TvShow>>> = _resultTvShow.receiveAsFlow()

    private fun getPopularTvShow() {
        viewModelScope.launch(Dispatchers.IO) {
            tmdbRepository.getPopularTvShow().collect {
                _resultTvShow.send(it)
            }
        }
    }

    fun toggleVideoOrientation() {
        _isVideoFullScreen.value = _isVideoFullScreen.value.not()
    }

}
