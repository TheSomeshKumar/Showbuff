package com.thesomeshkumar.showbuff.ui.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thesomeshkumar.showbuff.data.common.onError
import com.thesomeshkumar.showbuff.data.common.onLoading
import com.thesomeshkumar.showbuff.data.common.onSuccess
import com.thesomeshkumar.showbuff.databinding.FragmentMoviesBinding
import com.thesomeshkumar.showbuff.util.autoCleared
import com.thesomeshkumar.showbuff.util.getError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private val viewModel: MoviesViewModel by viewModels()
    private var binding: FragmentMoviesBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        val adapter = MoviesAdapter { itemView, movie ->
            val transitionExtra = FragmentNavigatorExtras(itemView to movie.name)
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesToDetail(
                    backdropImageUrl = movie.backdropPath,
                    name = movie.name,
                    overview = movie.overview
                ),
                transitionExtra
            )
        }
        binding.rvMovies.adapter = adapter

        val carouselAdapter = CarouselAdapter { itemView, movie ->
            val transitionExtra = FragmentNavigatorExtras(itemView to movie.name)
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesToDetail(
                    backdropImageUrl = movie.backdropPath,
                    name = movie.name,
                    overview = movie.overview
                ),
                transitionExtra
            )
        }
        binding.rvCarousel.adapter = carouselAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.popularMovies.collect { response ->
                        response.onLoading {
                            binding.progressIndicator.show()
                        }.onSuccess {
                            binding.progressIndicator.hide()
                            adapter.differ.submitList(it.toMutableList())
                        }.onError { error ->
                            MaterialAlertDialogBuilder(requireContext())
                                .setMessage(error.getError(requireContext()))
                                .setPositiveButton(getString(android.R.string.ok), null).show()
                            binding.progressIndicator.hide()
                        }
                    }
                }

                launch {
                    viewModel.nowPlayingMovies.collect { response ->
                        response.onLoading {
                            binding.progressIndicator.show()
                        }.onSuccess {
                            binding.progressIndicator.hide()
                            carouselAdapter.differ.submitList(it.toMutableList())
                        }.onError { error ->
                            MaterialAlertDialogBuilder(requireContext())
                                .setMessage(error.getError(requireContext()))
                                .setPositiveButton(getString(android.R.string.ok), null).show()
                            binding.progressIndicator.hide()
                        }
                    }
                }
            }
        }
    }
}
