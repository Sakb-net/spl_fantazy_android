package com.sakb.spl.ui.news.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sakb.spl.data.model.NewsResponse
import com.sakb.spl.databinding.ListItemFirstNewsBinding
import com.sakb.spl.databinding.ListItemNewssBinding
import com.sakb.spl.ui.newdetails.NewsDetailsActivity
import com.sakb.spl.ui.news.diffcallback.NewsDiffCallback
import com.sakb.spl.constants.Constants


class NewsAdapter : ListAdapter<NewsResponse.Data, RecyclerView.ViewHolder>(
    NewsDiffCallback()
) {

    var onClickListener: ((NewsResponse.Data) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      if (viewType== HEADER_VIEW_TYPE)
          return FirstVideosViewHolder(
              ListItemFirstNewsBinding.inflate(
                  LayoutInflater.from(parent.context),
                  parent,
                  false
              ), onClickListener
          )
        else
          return FixturesViewHolder(
              ListItemNewssBinding.inflate(
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
        private val binding: ListItemFirstNewsBinding,
        private val onNewsListener: ((NewsResponse.Data) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(news : NewsResponse.Data) {
            binding.textViewTitle.text = news.name
            binding.textViewSubtitle.text = news.created_at

            Glide.with(binding.imageViewThumb).load(Constants.baseUrl + news.image)
                .override(150).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.imageViewThumb)

            binding.root.setOnClickListener {
                it.context.startActivity(
                    Intent(it.context, NewsDetailsActivity::class.java)
                        .putExtra("obj",news)
                )
            }
            // binding.setUser(user)
            }
        }


    class FixturesViewHolder(
        private val binding: ListItemNewssBinding,
        private val onNewsListener: ((NewsResponse.Data) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(news : NewsResponse.Data) {
            binding.textViewTitle.text = news.name
            binding.textViewSubtitle.text = news.created_at

            Glide.with(binding.imageViewThumb).load(Constants.baseUrl + news.image)
                .override(150).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.imageViewThumb)

            binding.root.setOnClickListener {
                it.context.startActivity(
                    Intent(it.context, NewsDetailsActivity::class.java)
                        .putExtra("obj",news)
                )
            }
            // binding.setUser(user)
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