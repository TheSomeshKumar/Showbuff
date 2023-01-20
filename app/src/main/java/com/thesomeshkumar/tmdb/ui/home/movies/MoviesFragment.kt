package com.thesomeshkumar.tmdb.ui.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.thesomeshkumar.tmdb.data.common.onError
import com.thesomeshkumar.tmdb.data.common.onLoading
import com.thesomeshkumar.tmdb.data.common.onSuccess
import com.thesomeshkumar.tmdb.databinding.FragmentMoviesBinding
import com.thesomeshkumar.tmdb.ui.home.HomeViewModel
import com.thesomeshkumar.tmdb.ui.models.Movie
import com.thesomeshkumar.tmdb.util.autoCleared
import com.thesomeshkumar.tmdb.util.getError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var binding: FragmentMoviesBinding by autoCleared()
    private val movieList = mutableListOf<Movie>()

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

        val adapter = MoviesAdapter(movieList) { }
        binding.rvMovies.adapter = adapter

        viewModel.getPopularMovies()

        lifecycleScope.launchWhenStarted {
            viewModel.resultMovies.collect { result ->
                result
                    .onLoading { binding.progressIndicator.show() }
                    .onSuccess {
                        binding.progressIndicator.hide()
                        movieList.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                    .onError {
                        Toast.makeText(
                            requireContext(),
                            it.getError(requireContext()),
                            Toast.LENGTH_LONG
                        ).show()
                        binding.progressIndicator.hide()
                    }
            }
        }
    }
}
