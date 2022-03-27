package com.thesomeshkumar.pixelvideos.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesomeshkumar.pixelvideos.data.common.onError
import com.thesomeshkumar.pixelvideos.data.common.onLoading
import com.thesomeshkumar.pixelvideos.data.common.onSuccess
import com.thesomeshkumar.pixelvideos.databinding.FragmentVideoListBinding
import com.thesomeshkumar.pixelvideos.ui.models.Video
import com.thesomeshkumar.pixelvideos.util.autoCleared
import com.thesomeshkumar.pixelvideos.util.getError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoListFragment : Fragment() {
    private val viewModel: VideosViewModel by activityViewModels()
    private var binding: FragmentVideoListBinding by autoCleared()
    private val videos = mutableListOf<Video>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPopularVideos.layoutManager = LinearLayoutManager(requireContext())

        val adapter = VideoListAdapter(videos) { clickedVideo ->
            findNavController().navigate(
                VideoListFragmentDirections.actionVideoListToPlayer(
                    videoUrl = clickedVideo.videoLink
                )
            )
        }

        binding.rvPopularVideos.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.resultPopularVideos.collect { result ->
                result
                    .onLoading { binding.progressIndicator.show() }
                    .onSuccess {
                        binding.progressIndicator.hide()
                        videos.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                    .onError {
                        Toast.makeText(
                            requireContext(), it.getError(requireContext()), Toast.LENGTH_LONG
                        ).show()
                        binding.progressIndicator.hide()
                    }
            }
        }
    }
}
