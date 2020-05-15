package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.databinding.ItemHomeFixtureTitlesBinding
import com.sakb.spl.ui.home.diffcallback.FixturesHeaderDiffCallback

class FixturesHeaderAdapter : ListAdapter<HomeResponse.Fixture, FixturesHeaderAdapter.FixturesViewHolder>(
    FixturesHeaderDiffCallback()
) {

    var onClickListener: ((HomeResponse.Fixture) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FixturesViewHolder(ItemHomeFixtureTitlesBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickListener)

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class FixturesViewHolder(private val binding: ItemHomeFixtureTitlesBinding,
                         private val onNewsListener: ((HomeResponse.Fixture) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {



        fun bind(data: HomeResponse.Fixture) = data.run {

            itemView.setOnClickListener { onNewsListener?.invoke(this) }
            binding.textViewStart.text = startDateDay?.plus(",")?.plus(startDate)
            binding.textViewEnd.text = endDateDay?.plus(",")?.plus(endDate)
        }







    }

    fun onClear() {
        onClickListener = null
    }
}