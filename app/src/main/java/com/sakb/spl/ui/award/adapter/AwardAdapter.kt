package com.sakb.spl.ui.help.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.internal.g
import com.sakb.spl.R
import com.sakb.spl.data.model.ContentItemsItem
import com.sakb.spl.databinding.ItemAwardBinding
import timber.log.Timber

class AwardAdapter :
    ListAdapter<ContentItemsItem, AwardAdapter.AwardViewHolder>(
        AwardDiffCallback()
    ) {

    var onClickListener: ((position: Int, ContentItemsItem) -> Unit)? = null
    private val adapter by lazy {
        AwardChildAdapter()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AwardViewHolder(
            ItemAwardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickListener
        )

    override fun onBindViewHolder(holder: AwardViewHolder, position: Int) {
        val instructions = getItem(position)
        holder.bind(instructions)
    }

    inner class AwardViewHolder(
        private val binding: ItemAwardBinding,
        private val onClickListener: ((position: Int, ContentItemsItem) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClickListener?.invoke(absoluteAdapterPosition, getItem(absoluteAdapterPosition))
            }
        }

        fun bind(data: ContentItemsItem) = data.run {
            binding.tvTitle.text = title
            binding.rvChild.adapter = adapter
            adapter.submitList(data.content)
        }
    }

    fun onClear() {
        onClickListener = null
    }
}