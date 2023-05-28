package com.thesomeshkumar.showbuff.ui.home.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.showbuff.data.common.Result
import com.thesomeshkumar.showbuff.data.repository.ShowbuffRepository
import com.thesomeshkumar.showbuff.ui.models.HomeMediaUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TvShowViewModel @Inject constructor(private var showbuffRepository: ShowbuffRepository) :
    ViewModel() {

    private val _resultPopularTvShow = MutableStateFlow<Result<List<HomeMediaUI>>>(Result.Loading)
    val resultPopularTvShow: Flow<Result<List<HomeMediaUI>>> = _resultPopularTvShow.asStateFlow()

    private val _resultAiringTodayTvShows =
        MutableStateFlow<Result<List<HomeMediaUI>>>(Result.Loading)
    val airingTodayTvShows: StateFlow<Result<List<HomeMediaUI>>> =
        _resultAiringTodayTvShows.asStateFlow()

    init {
        getPopularTvShow()
        getAiringTodayTvShows()
    }

    private fun getPopularTvShow() {
        viewModelScope.launch(Dispatchers.IO) {
            showbuffRepository.getPopularTvShows().collect { tvShows ->
                _resultPopularTvShow.value = tvShows
            }
        }
    }

    private fun getAiringTodayTvShows() {
        viewModelScope.launch(Dispatchers.IO) {
            showbuffRepository.getAiringTodayTvShows().collect { tvShows ->
                _resultAiringTodayTvShows.value = tvShows
            }
        }
    }
}
