package com.sakb.spl.ui.help.adapter

import androidx.recyclerview.widget.DiffUtil

class AwardChildDiffCallback : DiffUtil.ItemCallback<String>() {


    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}