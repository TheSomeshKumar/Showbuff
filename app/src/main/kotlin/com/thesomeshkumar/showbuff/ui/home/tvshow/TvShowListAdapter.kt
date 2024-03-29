package com.thesomeshkumar.showbuff.ui.home.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thesomeshkumar.showbuff.databinding.RowTvShowBinding
import com.thesomeshkumar.showbuff.ui.models.HomeMediaUI
import com.thesomeshkumar.showbuff.util.toFullPosterUrl

class TvShowListAdapter(
    private val itemClick: (View, HomeMediaUI) -> Unit
) : RecyclerView.Adapter<TvShowListAdapter.TvShowListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowListViewHolder {
        val binding = RowTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: TvShowListViewHolder, position: Int) =
        holder.bindView(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    class TvShowListViewHolder(
        private val binding: RowTvShowBinding,
        val itemClick: (View, HomeMediaUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: HomeMediaUI) {
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

    private val differCallback = object : DiffUtil.ItemCallback<HomeMediaUI>() {
        override fun areItemsTheSame(oldItem: HomeMediaUI, newItem: HomeMediaUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeMediaUI, newItem: HomeMediaUI): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
