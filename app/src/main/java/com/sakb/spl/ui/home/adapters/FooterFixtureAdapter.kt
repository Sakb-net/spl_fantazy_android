package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.data.model.DataItem
import com.sakb.spl.databinding.ItemHomeShowAllMatchesBinding

class FooterFixtureAdapter : RecyclerView.Adapter<FooterFixtureAdapter.FooterViewHolder>() {

    var onClickListener: (() -> Unit)? = null
    var data: List<DataItem?>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FooterViewHolder(
            ItemHomeShowAllMatchesBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            ),
            onClickListener
        )

    override fun onBindViewHolder(holderHeader: FooterViewHolder, position: Int) {
        holderHeader.bind(data)
    }

    override fun getItemCount() = HEADER

    class FooterViewHolder(
        private val binding: ItemHomeShowAllMatchesBinding,
        private val onChooseTeamClickListener: (() -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: List<DataItem?>?) {
            data?.let { list ->
                if (list.isEmpty()) {
                    binding.cardFooter.visibility = View.GONE
                } else {
                    binding.cardFooter.visibility = View.VISIBLE
                }
            }
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

    fun hideFooter(dataSend: List<DataItem?>?) {
        data = dataSend
        notifyDataSetChanged()
    }

    companion object {
        private const val HEADER = 1
    }
}