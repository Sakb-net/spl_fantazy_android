package com.sakb.spl.ui.videos.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.data.model.VideosResponse
import com.sakb.spl.databinding.ListItemFirstVideoBinding
import com.sakb.spl.databinding.ListItemVideoBinding
import com.sakb.spl.ui.detailsvideo.DetailsVideoActivity
import com.sakb.spl.ui.videos.diffcallback.VideosDiffCallback
import com.sakb.spl.constants.Constants


class VideosAdapter : ListAdapter<VideosResponse.Data, RecyclerView.ViewHolder>(
    VideosDiffCallback()
) {

    var onClickListener: ((HomeResponse.Video) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      if (viewType== HEADER_VIEW_TYPE)
          return FirstVideosViewHolder(
              ListItemFirstVideoBinding.inflate(
                  LayoutInflater.from(parent.context),
                  parent,
                  false
              ), onClickListener
          )
        else
          return FixturesViewHolder(
              ListItemVideoBinding.inflate(
                  LayoutInflater.from(parent.context),
                  parent,
                  false
              ), onClickListener
          )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = getItem(position)
        when(holder){
            is FirstVideosViewHolder ->  holder.bind(news)
            is FixturesViewHolder ->   holder.bind(news)
        }
     //   holder.bind(news)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position==0) HEADER_VIEW_TYPE else NORMAL_VIEW_TYPE
    }

    class FirstVideosViewHolder(
        private val binding: ListItemFirstVideoBinding,
        private val onNewsListener: ((HomeResponse.Video) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(video : VideosResponse.Data) {
            binding.textViewTitle.text = video.name
            binding.textViewSubtitle.text = video.createdAt

            Glide.with(binding.imageViewThumb).load(Constants.baseUrl + video.image)
                .override(150).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.imageViewThumb)


            binding.root.setOnClickListener {
            it.context.startActivity(
            Intent(it.context, DetailsVideoActivity::class.java)
            .putExtra("upload_id",video.uploadId)
            .putExtra("title",video.name)
            .putExtra("created_at",video.createdAt)
            .putExtra("desc",video.content)
            .putExtra("link",video.link)
            .putExtra("video",video.video)
            )
            }
        }

    }
    class FixturesViewHolder(
        private val binding: ListItemVideoBinding,
        private val onNewsListener: ((HomeResponse.Video) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(video : VideosResponse.Data) {
            binding.textViewTitle.text = video.name
            binding.textViewSubtitle.text = video.createdAt

            Glide.with(binding.imageViewThumb).load(Constants.baseUrl + video.image)
                .override(150).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.imageViewThumb)


            binding.root.setOnClickListener {
                it.context.startActivity(
                    Intent(it.context, DetailsVideoActivity::class.java)
                        .putExtra("upload_id",video.uploadId)
                        .putExtra("title",video.name)
                        .putExtra("created_at",video.createdAt)
                        .putExtra("desc",video.content)
                        .putExtra("link",video.link)
                        .putExtra("video",video.video)
                )
            }
        }

    }


    companion object {
     const val HEADER_VIEW_TYPE = 0
     const val NORMAL_VIEW_TYPE = 1
    }

    fun onClear() {
        onClickListener = null
    }
}