package com.thesomeshkumar.tmdb.ui.details

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.util.Util
import com.thesomeshkumar.tmdb.R
import com.thesomeshkumar.tmdb.databinding.FragmentVideoPlayerBinding
import com.thesomeshkumar.tmdb.ui.home.TvShowViewModel
import com.thesomeshkumar.tmdb.util.ExoHelper
import com.thesomeshkumar.tmdb.util.autoCleared

class VideoPlayerFragment : Fragment(), View.OnClickListener {
    private lateinit var fullscreenButton: ImageButton
    private var fullscreen: Boolean = false
    private lateinit var videoUrl: String
    private lateinit var exoHelper: ExoHelper
    private val args: VideoPlayerFragmentArgs by navArgs()
    private var binding: FragmentVideoPlayerBinding by autoCleared()
    private val viewModel: TvShowViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoUrl = args.videoUrl
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullscreenButton = binding.playerView.findViewById(R.id.exo_fullscreen_icon) as ImageButton
        fullscreenButton.setOnClickListener(this)

        exoHelper = ExoHelper(
            playerView = binding.playerView, isPlayWhenReady = true,
            onError = { exception: PlaybackException ->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            },
            onPlayerStateChanged = { playerState: Int ->
                when (playerState) {
                    Player.STATE_BUFFERING -> Toast.makeText(
                        requireContext(),
                        getString(R.string.buffering),
                        Toast.LENGTH_SHORT
                    ).show()
                    Player.STATE_ENDED -> Toast.makeText(
                        requireContext(),
                        getString(R.string.video_ended),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        lifecycleScope.launchWhenStarted {
            viewModel.isVideoFullScreen.collect { didRequestFullScreen ->
                makeFullScreenLandscape(didRequestFullScreen)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            exoHelper.initializePlayer(videoUrl)
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24) {
            exoHelper.initializePlayer(videoUrl)
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            exoHelper.releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            exoHelper.releasePlayer()
        }
        activity?.let { makeFullScreenLandscape(didRequestFullScreen = false) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.exo_fullscreen_icon -> viewModel.toggleVideoOrientation()
        }
    }

    private fun makeFullScreenLandscape(didRequestFullScreen: Boolean): Unit =
        if (didRequestFullScreen) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            fullscreenButton.setImageResource(R.drawable.ic_fullscreen_exit)
            binding.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            fullscreen = true
        } else {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            fullscreenButton.setImageResource(R.drawable.ic_fullscreen_enter)
            binding.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            fullscreen = false
        }
}
