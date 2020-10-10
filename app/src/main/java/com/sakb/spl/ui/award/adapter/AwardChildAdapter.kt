package com.sakb.spl.ui.help.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.databinding.ItemAwardChildBinding

class AwardChildAdapter :
    ListAdapter<String, AwardChildAdapter.AwardViewHolder>(
        AwardChildDiffCallback()
    ) {

    var onClickListener: ((position: Int, String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AwardViewHolder(
            ItemAwardChildBinding.inflate(
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
        private val binding: ItemAwardChildBinding,
        private val onClickListener: ((position: Int, String) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClickListener?.invoke(absoluteAdapterPosition, getItem(absoluteAdapterPosition))
            }
        }

        fun bind(data: String) = data.run {
            binding.txtPoint.text = data
            //binding.content.text = content
        }
    }

    fun onClear() {
        onClickListener = null
    }
}