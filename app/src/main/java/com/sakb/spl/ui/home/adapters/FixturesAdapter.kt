package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.MatchGroupItem
import com.sakb.spl.databinding.ItemHomeMatchBinding
import com.sakb.spl.ui.home.diffcallback.FixturesDiffCallback
import com.sakb.spl.utils.ConvertDateTimeUtils
import com.sakb.spl.utils.ConvertDateTimeUtils.TIME_24_FORMAT
import com.sakb.spl.utils.ConvertDateTimeUtils.TIME_FORMAT
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class FixturesAdapter : ListAdapter<MatchGroupItem, FixturesAdapter.FixturesViewHolder>(
    FixturesDiffCallback()
) {

    var onClickListener: ((MatchGroupItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FixturesViewHolder(
            ItemHomeMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        val matchGroupItem = getItem(position)
        holder.bind(matchGroupItem)
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() >= 3) {
            3
        } else {
            super.getItemCount()
        }
    }

    class FixturesViewHolder(
        private val binding: ItemHomeMatchBinding,
        private val onMatchClick: ((MatchGroupItem) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: MatchGroupItem) = data.run {

            itemView.setOnClickListener { onMatchClick?.invoke(this) }

            binding.team1Tv.text = nameFirst
            binding.team2Tv.text = nameSecond
            Glide.with(binding.team1Iv).load(Constants.baseUrl + imageFirst).override(150)
                .into(binding.team1Iv)
            Glide.with(binding.team2Iv).load(Constants.baseUrl + imageSecond).override(150)
                .into(binding.team2Iv)
            /*
            convert date to mili seconds*/

            Timber.e("---------${date}")
            date?.let { myDate ->
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val date: Date? = sdf.parse(myDate)

                val today: String = sdf.format(Date())
                Timber.e("---------$today")
                val todayDate: Date? = sdf.parse(today)


                val result = date?.compareTo(todayDate)

                Timber.e("##$result")

                val timeNew =
                    ConvertDateTimeUtils.changeFormat(this.time, TIME_24_FORMAT, TIME_FORMAT)

                if (result != null && result < 0) {
                    binding.dateResult.text = firstGoon?.toString()?.plus(":")?.plus(secondGoon)
                } else {
                    binding.dateResult.text = timeNew
                    //this.date?.plus("\n")?.plus(time)
                }
            }
        }


    }

    fun onClear() {
        onClickListener = null
    }
}