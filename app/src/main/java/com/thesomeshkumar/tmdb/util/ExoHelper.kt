package com.thesomeshkumar.tmdb.util

import androidx.core.net.toUri
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.thesomeshkumar.tmdb.R

class ExoHelper(
    private val playerView: PlayerView,
    private val isPlayWhenReady: Boolean = true,
    onError: (PlaybackException) -> Unit,
    onPlayerStateChanged: (Int) -> Unit
) {

    private var currentPosition: Long = 0
    private var exoPlayer: ExoPlayer? = null
    private var mediaSource: ProgressiveMediaSource? = null

    private val playerListener: Player.Listener = object : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            onError(error)
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            onPlayerStateChanged(playbackState)
        }
    }

    fun initializePlayer(url: String) {
        if (exoPlayer == null) {
            exoPlayer = SimpleExoPlayer.Builder(playerView.context).build()
            exoPlayer!!.addListener(playerListener)
            playerView.player = exoPlayer
            exoPlayer!!.seekTo(currentPosition)
        }
        val userAgent =
            Util.getUserAgent(playerView.context, playerView.context.getString(R.string.app_name))
        mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSourceFactory(playerView.context, userAgent),
            DefaultExtractorsFactory()
        )
            .createMediaSource(MediaItem.fromUri(url.toUri()))

        exoPlayer!!.prepare(mediaSource!!, true, false)
        exoPlayer!!.playWhenReady = isPlayWhenReady
    }

    fun releasePlayer() {
        if (exoPlayer != null) {
            currentPosition = exoPlayer!!.currentPosition
            exoPlayer!!.release()
            exoPlayer = null
            mediaSource = null
            playerView.player = null
        }
    }
}
