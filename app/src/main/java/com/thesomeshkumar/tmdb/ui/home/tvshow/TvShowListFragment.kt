package com.thesomeshkumar.tmdb.ui.home.tvshow

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
import com.thesomeshkumar.tmdb.data.common.onError
import com.thesomeshkumar.tmdb.data.common.onLoading
import com.thesomeshkumar.tmdb.data.common.onSuccess
import com.thesomeshkumar.tmdb.databinding.FragmentTvShowListBinding
import com.thesomeshkumar.tmdb.ui.models.TvShow
import com.thesomeshkumar.tmdb.util.autoCleared
import com.thesomeshkumar.tmdb.util.getError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowListFragment : Fragment() {
    private val viewModel: TvShowViewModel by viewModels()
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

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        val adapter = TvShowListAdapter(tvShowList) { itemView, tvShow ->
            val transitionExtra = FragmentNavigatorExtras(itemView to tvShow.name)
            findNavController().navigate(
                TvShowListFragmentDirections.actionTvShowToDetail(
                    backdropImageUrl = tvShow.backdropPath,
                    name = tvShow.name,
                    overview = tvShow.overview
                ),
                transitionExtra
            )
        }
        binding.rvTvShows.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.resultTvShow.collect { response ->
                    response.onLoading {
                        binding.progressIndicator.show()
                    }.onSuccess {
                        binding.progressIndicator.hide()
                        tvShowList.clear()
                        tvShowList.addAll(it)
                        adapter.notifyDataSetChanged()
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
