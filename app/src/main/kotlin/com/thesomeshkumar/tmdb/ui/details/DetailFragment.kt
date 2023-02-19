package com.thesomeshkumar.tmdb.ui.details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import com.thesomeshkumar.tmdb.R
import com.thesomeshkumar.tmdb.databinding.FragmentDetailBinding
import com.thesomeshkumar.tmdb.util.autoCleared
import com.thesomeshkumar.tmdb.util.toFullPosterUrl

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private var binding: FragmentDetailBinding by autoCleared()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transformation: MaterialContainerTransform =
            MaterialContainerTransform(requireContext(), true).apply {
                fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
                fadeProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f, 0.01f)
                drawingViewId = R.id.nav_host
                scrimColor = Color.TRANSPARENT
            }
        sharedElementEnterTransition = transformation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.transitionName = args.name
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.name
        binding.tvOverview.text = args.overview
        Glide.with(this)
            .load(args.backdropImageUrl.toFullPosterUrl())
            .into(binding.ivBackdrop)
    }
}
