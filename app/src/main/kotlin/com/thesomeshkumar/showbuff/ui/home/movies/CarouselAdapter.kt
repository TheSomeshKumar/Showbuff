package com.thesomeshkumar.showbuff.ui.home.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thesomeshkumar.showbuff.databinding.RowCarouselBinding
import com.thesomeshkumar.showbuff.ui.models.HomeMediaUI
import com.thesomeshkumar.showbuff.util.toFullPosterUrl

class CarouselAdapter(
    private val itemClick: (View, HomeMediaUI) -> Unit
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = RowCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) =
        holder.bindView(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    class CarouselViewHolder(
        private val binding: RowCarouselBinding,
        val itemClick: (View, HomeMediaUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: HomeMediaUI) {
            with(item) {
                Glide.with(binding.ivCarousel)
                    .load(backdropPath.toFullPosterUrl())
                    .into(binding.ivCarousel)
                binding.tvTitle.text = name
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
