package com.thesomeshkumar.tmdb.ui.home.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thesomeshkumar.tmdb.databinding.RowMovieBinding
import com.thesomeshkumar.tmdb.ui.models.Movie
import com.thesomeshkumar.tmdb.util.Constants

class MoviesAdapter(private val items: List<Movie>, private val itemClick: (View, Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) =
        holder.bindView(items[position])

    override fun getItemCount(): Int = items.size

    class MoviesViewHolder(
        private val binding: RowMovieBinding,
        val itemClick: (View, Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(item) {
                Glide.with(binding.ivThumb)
                    .load("${Constants.TMDB_POSTER_PATH_URL}$backdropPath")
                    .into(binding.ivThumb)
                binding.tvName.text = name
                binding.root.transitionName = name
                binding.root.setOnClickListener { itemClick(binding.root, this) }
            }
        }
    }
}
