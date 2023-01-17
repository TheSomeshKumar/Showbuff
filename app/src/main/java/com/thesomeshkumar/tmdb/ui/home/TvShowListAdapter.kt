package com.thesomeshkumar.tmdb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thesomeshkumar.tmdb.databinding.RowTvShowBinding
import com.thesomeshkumar.tmdb.ui.models.TvShow
import com.thesomeshkumar.tmdb.util.Constants

class TvShowListAdapter(private val items: List<TvShow>, private val itemClick: (TvShow) -> Unit) :
    RecyclerView.Adapter<TvShowListAdapter.TvShowListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowListViewHolder {
        val binding = RowTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: TvShowListViewHolder, position: Int) =
        holder.bindView(items[position])

    override fun getItemCount(): Int = items.size

    class TvShowListViewHolder(
        private val binding: RowTvShowBinding,
        val itemClick: (TvShow) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: TvShow) {
            with(item) {
                Glide.with(binding.ivThumb)
                    .load("${Constants.TMDB_POSTER_PATH_URL}$backdropPath")
                    .into(binding.ivThumb)
                binding.tvName.text = name
                binding.root.setOnClickListener { itemClick(this) }
            }
        }
    }
}
