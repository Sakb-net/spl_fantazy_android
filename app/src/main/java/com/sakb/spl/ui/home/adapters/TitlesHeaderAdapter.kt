package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.databinding.ItemHomeTitlesBinding
import com.sakb.spl.ui.home.diffcallback.TitlesHeaderDiffCallback

class TitlesHeaderAdapter : ListAdapter<String, TitlesHeaderAdapter.FixturesViewHolder>(
    TitlesHeaderDiffCallback()
) {

    var onClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FixturesViewHolder(
            ItemHomeTitlesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class FixturesViewHolder(
        private val binding: ItemHomeTitlesBinding,
        private val onNewsListener: (() -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: String) = data.run {
            itemView.setOnClickListener { onNewsListener?.invoke() }
            binding.textViewTitle.text = data
        }


    }

    fun onClear() {
        onClickListener = null
    }
}