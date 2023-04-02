package com.thesomeshkumar.showbuff.ui.home.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.showbuff.data.common.Result
import com.thesomeshkumar.showbuff.data.repository.ShowbuffRepository
import com.thesomeshkumar.showbuff.ui.models.TvShowUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TvShowViewModel @Inject constructor(private var showbuffRepository: ShowbuffRepository) :
    ViewModel() {

    private val _resultTvShow = MutableStateFlow<Result<List<TvShowUI>>>(Result.Loading)
    val resultTvShow: Flow<Result<List<TvShowUI>>> = _resultTvShow.asStateFlow()

    init {
        getPopularTvShow()
    }

    private fun getPopularTvShow() {
        viewModelScope.launch(Dispatchers.IO) {
            showbuffRepository.getPopularTvShows().collect { tvShows ->
                _resultTvShow.value = tvShows
            }
        }
    }
}
