package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.data.model.DataItem
import com.sakb.spl.databinding.ItemHomeFixtureTitlesBinding
import com.sakb.spl.ui.home.diffcallback.FixturesHeaderDiffCallback
import com.sakb.spl.utils.ConvertDateTimeUtils
import com.sakb.spl.utils.ConvertDateTimeUtils.DATE_CHAR_FORMAT_AR
import com.sakb.spl.utils.ConvertDateTimeUtils.DATE_CHAR_FORMAT_EN
import com.sakb.spl.utils.ConvertDateTimeUtils.DATE_DEFAULT_FORMAT
import com.sakb.spl.utils.LanguageUtil

class FixturesHeaderAdapter :
    ListAdapter<DataItem, FixturesHeaderAdapter.FixturesViewHolder>(
        FixturesHeaderDiffCallback()
    ) {

    var onClickListener: ((DataItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FixturesViewHolder(
            ItemHomeFixtureTitlesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)
    }

    class FixturesViewHolder(
        private val binding: ItemHomeFixtureTitlesBinding,
        private val onDataClick: ((DataItem) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: DataItem) = data.run {
            val dateNew = if (LanguageUtil.isArabic()) {
                ConvertDateTimeUtils.changeFormat(
                    startDate, DATE_DEFAULT_FORMAT,
                    DATE_CHAR_FORMAT_AR
                )
            } else {
                ConvertDateTimeUtils.changeFormat(
                    startDate, DATE_DEFAULT_FORMAT,
                    DATE_CHAR_FORMAT_EN
                )
            }
            itemView.setOnClickListener { onDataClick?.invoke(this) }
            binding.textViewStart.text = langNumWeek
            binding.textViewEnd.text = if (LanguageUtil.isArabic()) {
                startDateDay?.plus(",")?.plus(dateNew)
            } else {
                dateNew?.plus(",")?.plus(startDateDay)
            }
        }


    }

    fun onClear() {
        onClickListener = null
    }
}