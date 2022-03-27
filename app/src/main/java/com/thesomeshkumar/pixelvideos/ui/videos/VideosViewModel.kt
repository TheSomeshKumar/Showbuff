package com.thesomeshkumar.pixelvideos.ui.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.pixelvideos.data.common.Result
import com.thesomeshkumar.pixelvideos.data.repository.VideoRepository
import com.thesomeshkumar.pixelvideos.data.response.VideoDTO
import com.thesomeshkumar.pixelvideos.ui.models.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(private var videoRepository: VideoRepository) :
    ViewModel() {
    private var _isVideoFullScreen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isVideoFullScreen: StateFlow<Boolean> = _isVideoFullScreen.asStateFlow()

    init {
        getPopularVideos()
    }

    private val _resultPopularVideos =
        Channel<Result<List<Video>>>(Channel.BUFFERED)
    val resultPopularVideos: Flow<Result<List<Video>>> = _resultPopularVideos.receiveAsFlow()

    private fun getPopularVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.getPopularVideos().collect { _resultPopularVideos.send(it) }
        }
    }

    fun toggleVideoOrientation() {
        _isVideoFullScreen.value = _isVideoFullScreen.value.not()
    }
}
