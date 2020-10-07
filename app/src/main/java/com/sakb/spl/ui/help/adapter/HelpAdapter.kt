package com.sakb.spl.ui.help.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.HowToPlayResponse
import com.sakb.spl.databinding.HelpItemRecyclerBinding
import timber.log.Timber

class HelpAdapter :
    ListAdapter<HowToPlayResponse.ContentHelp, HelpAdapter.InstructionsViewHolder>(
        HelpDiffCallback()
    ) {

    var onClickListener: ((position: Int, HowToPlayResponse.ContentHelp) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        InstructionsViewHolder(
            HelpItemRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickListener
        )

    override fun onBindViewHolder(holder: InstructionsViewHolder, position: Int) {
        val instructions = getItem(position)
        holder.bind(instructions)
    }

    inner class InstructionsViewHolder(
        private val binding: HelpItemRecyclerBinding,
        private val onClickListener: ((position: Int, HowToPlayResponse.ContentHelp) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                Timber.e("role = clicked")
                onClickListener?.invoke(absoluteAdapterPosition, getItem(absoluteAdapterPosition))
            }
        }

        fun bind(data: HowToPlayResponse.ContentHelp) = data.run {
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

        }
    }

    fun onClear() {
        onClickListener = null
    }
}