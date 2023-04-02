package com.thesomeshkumar.showbuff.ui.home.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thesomeshkumar.showbuff.databinding.RowMovieBinding
import com.thesomeshkumar.showbuff.ui.models.MovieUI
import com.thesomeshkumar.showbuff.util.toFullPosterUrl

class MoviesAdapter(
    private val itemClick: (View, MovieUI) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) =
        holder.bindView(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    class MoviesViewHolder(
        private val binding: RowMovieBinding,
        val itemClick: (View, MovieUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: MovieUI) {
            with(item) {
                Glide.with(binding.ivThumb)
                    .load(backdropPath.toFullPosterUrl())
                    .into(binding.ivThumb)
                binding.tvName.text = name
                binding.root.transitionName = name
                binding.root.setOnClickListener { itemClick(binding.root, this) }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<MovieUI>() {
        override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
