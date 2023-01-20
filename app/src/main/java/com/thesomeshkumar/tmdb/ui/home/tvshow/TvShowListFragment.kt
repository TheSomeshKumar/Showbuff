package com.thesomeshkumar.tmdb.ui.home.tvshow

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
import com.thesomeshkumar.tmdb.databinding.FragmentTvShowListBinding
import com.thesomeshkumar.tmdb.ui.home.HomeViewModel
import com.thesomeshkumar.tmdb.ui.models.TvShow
import com.thesomeshkumar.tmdb.util.autoCleared
import com.thesomeshkumar.tmdb.util.getError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowListFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var binding: FragmentTvShowListBinding by autoCleared()
    private val tvShowList = mutableListOf<TvShow>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TvShowListAdapter(tvShowList) { }
        binding.rvTvShows.adapter = adapter

        viewModel.getPopularTvShow()

        lifecycleScope.launchWhenStarted {
            viewModel.resultTvShow.collect { result ->
                result
                    .onLoading { binding.progressIndicator.show() }
                    .onSuccess {
                        binding.progressIndicator.hide()
                        tvShowList.addAll(it)
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
