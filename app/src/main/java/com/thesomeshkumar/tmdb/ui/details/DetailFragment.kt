package com.thesomeshkumar.tmdb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.thesomeshkumar.tmdb.databinding.FragmentDetailBinding
import com.thesomeshkumar.tmdb.util.Constants
import com.thesomeshkumar.tmdb.util.autoCleared

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private var binding: FragmentDetailBinding by autoCleared()

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
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.name
        binding.tvOverview.text = args.overview
        Glide.with(this)
            .load("${Constants.TMDB_POSTER_PATH_URL}${args.backdropImageUrl}")
            .into(binding.ivBackdrop)
    }
}
