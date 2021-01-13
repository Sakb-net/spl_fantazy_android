package com.sakb.spl.ui.howtoplay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.HowToPlayResponse
import com.sakb.spl.databinding.InstructionsItemRecyclerBinding
import timber.log.Timber

class InstructionsAdapter :
    ListAdapter<HowToPlayResponse.ContentRole, InstructionsAdapter.InstructionsViewHolder>(
        InstructionsDiffCallback()) {

    var onClickListener: ((position: Int, HowToPlayResponse.ContentRole) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        InstructionsViewHolder(
            InstructionsItemRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickListener
        )

    override fun onBindViewHolder(holder: InstructionsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news, position)
    }

    class InstructionsViewHolder(
        private val binding: InstructionsItemRecyclerBinding,
        private val onClickListener: ((position: Int, HowToPlayResponse.ContentRole) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HowToPlayResponse.ContentRole, position: Int) = data.run {
            binding.title.text = title
            binding.content.text = content
            if (isActivated == true) {
                binding.content.visibility = View.VISIBLE
                binding.img.setImageResource(R.drawable.ic_expand_more_white_24dp)
            } else {
                binding.content.visibility = View.GONE
                binding.img.setImageResource(R.drawable.ic_navigate_next_black_24dp)
            }
            binding.executePendingBindings()
            itemView.setOnClickListener {
                Timber.e("role = clicked")
                onClickListener?.invoke(position, this)
            }
        }
    }

    fun onClear() {
        onClickListener = null
    }
}