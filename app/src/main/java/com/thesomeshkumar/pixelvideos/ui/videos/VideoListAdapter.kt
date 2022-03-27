package com.thesomeshkumar.pixelvideos.ui.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thesomeshkumar.pixelvideos.R
import com.thesomeshkumar.pixelvideos.databinding.RowVideoListBinding
import com.thesomeshkumar.pixelvideos.ui.models.Video

class VideoListAdapter(private val items: List<Video>, private val itemClick: (Video) -> Unit) :
    RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding =
            RowVideoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) =
        holder.bindView(items[position])

    override fun getItemCount(): Int = items.size

    class VideoViewHolder(
        private val binding: RowVideoListBinding,
        val itemClick: (Video) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Video) {
            with(item) {
                Glide.with(binding.ivThumb).load(thumbUrl).centerCrop().into(binding.ivThumb)
                binding.tvUser.text =
                    binding.tvUser.context.getString(R.string.username, userName)
                binding.root.setOnClickListener { itemClick(this) }
            }
        }
    }
}
