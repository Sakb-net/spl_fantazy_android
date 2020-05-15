package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.databinding.ItemHomeHeaderBinding

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    var onChooseTeamClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HeaderViewHolder(
            ItemHomeHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            ),
            onChooseTeamClickListener
        )

    override fun onBindViewHolder(holderHeader: HeaderViewHolder, position: Int) {
        holderHeader.bind()
    }

    override fun getItemCount() = HEADER

    class HeaderViewHolder(
        private val binding: ItemHomeHeaderBinding,
        private val onChooseTeamClickListener: (() -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            itemView.run {
                binding.buttonChooseTeam.setOnClickListener {
                    onChooseTeamClickListener?.invoke()
                }
            }
        }
    }

    fun onClear() {
        onChooseTeamClickListener = null
    }

    companion object {
        private const val HEADER = 1
    }
}