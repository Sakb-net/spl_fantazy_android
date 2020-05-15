package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.databinding.ItemHomeShowAllMatchesBinding

class FooterFixtureAdapter : RecyclerView.Adapter<FooterFixtureAdapter.FooterViewHolder>() {

    var onClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FooterViewHolder(
            ItemHomeShowAllMatchesBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            ),
            onClickListener
        )

    override fun onBindViewHolder(holderHeader: FooterViewHolder, position: Int) {
        holderHeader.bind()
    }

    override fun getItemCount() = HEADER

    class FooterViewHolder(
        private val binding: ItemHomeShowAllMatchesBinding,
        private val onChooseTeamClickListener: (() -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            itemView.run {
                setOnClickListener {
                    onChooseTeamClickListener?.invoke()
                }
            }
        }
    }

    fun onClear() {
        onClickListener = null
    }

    companion object {
        private const val HEADER = 1
    }
}