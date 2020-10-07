package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.databinding.ItemHomeMatchBinding
import com.sakb.spl.ui.home.diffcallback.FixturesDiffCallback
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class FixturesAdapter : ListAdapter<HomeResponse.MatchGroup, FixturesAdapter.FixturesViewHolder>(
    FixturesDiffCallback()
) {

    var onClickListener: ((HomeResponse.MatchGroup) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FixturesViewHolder(
            ItemHomeMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class FixturesViewHolder(
        private val binding: ItemHomeMatchBinding,
        private val onNewsListener: ((HomeResponse.MatchGroup) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: HomeResponse.MatchGroup) = data.run {

            itemView.setOnClickListener { onNewsListener?.invoke(this) }

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

                if (result != null && result < 0) {
                    binding.dateResult.text = firstGoon?.toString()?.plus(":")?.plus(secondGoon)
                } else {
                    binding.dateResult.text =
                        this.date?.plus("\n")?.plus(time)
                }
            }
        }


    }

    fun onClear() {
        onClickListener = null
    }
}