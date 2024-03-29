package com.sakb.spl.ui.matches.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.constants.Constants
import com.sakb.spl.databinding.RoundNextMatchItemBinding
import com.sakb.spl.databinding.RoundPrevMatchItemBinding
import com.sakb.spl.ui.matches.diffcallback.FixturesDiffCallback
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class MatchesAdapter : ListAdapter<HomeResponse.MatchGroup, RecyclerView.ViewHolder>(
    FixturesDiffCallback()
) {

    var onClickListener: ((HomeResponse.MatchGroup) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        when(viewType){
NEXT_MATCHE-> return  NextMatchesViewHolder(
    RoundNextMatchItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ), onClickListener
)
    else->   return PrevMatchesViewHolder(
        RoundPrevMatchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), onClickListener
    )
        }

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = getItem(position)
        when(holder) {
            is PrevMatchesViewHolder -> holder.bind(news)
            is NextMatchesViewHolder -> holder.bind(news)

        }

    }

    class PrevMatchesViewHolder(private val binding: RoundPrevMatchItemBinding,
                                private val onNewsListener: ((HomeResponse.MatchGroup) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeResponse.MatchGroup) = data.run {

            itemView.setOnClickListener { onNewsListener?.invoke(this) }

                binding.team1Tv.text = nameFirst
                binding.team2Tv.text = nameSecond
                Glide.with(binding.team1Iv).load(Constants.baseUrl+imageFirst).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).override(150) .into( binding.team1Iv)
                Glide.with(binding.team2Iv).load(Constants.baseUrl+imageSecond).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).override(150) .into( binding.team2Iv)
            binding.dateResultTeamOne.text = firstGoon?.toString()
            binding.dateResultTeamTwo.text = secondGoon?.toString()
            }
    }

    class NextMatchesViewHolder(private val binding: RoundNextMatchItemBinding,
                                private val onNewsListener: ((HomeResponse.MatchGroup) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeResponse.MatchGroup) = data.run {

            itemView.setOnClickListener { onNewsListener?.invoke(this) }

            binding.team1Tv.text = nameFirst
            binding.team2Tv.text = nameSecond
            Glide.with(binding.team1Iv).load(Constants.baseUrl+imageFirst).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).override(150) .into( binding.team1Iv)
            Glide.with(binding.team2Iv).load(Constants.baseUrl+imageSecond).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).override(150) .into( binding.team2Iv)
            binding.dateResult.text = this.date?.plus("\n")?.plus(time)

        }
    }




    override fun getItemViewType(position: Int): Int {


            val date = getItem(position).date
            var type = PREV_MATCHE

            /*
                  convert date to mili seconds*/

            Timber.e("---------${date}")
            date?.let {
                    myDate->
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val dateParse: Date? = sdf.parse(myDate)

                val today  : String = sdf.format(Date())
                Timber.e("---------$today")
                val todayDate : Date? = sdf.parse(today)


                val result = dateParse?.compareTo(todayDate)

                Timber.e("##$result")

                if (result!=null && result<0){
                   type = PREV_MATCHE
                }
                else{
                  type = NEXT_MATCHE
                }
            }


        return type
    }

    companion object {
        const val PREV_MATCHE = 0
        const val NEXT_MATCHE = 1
    }

    fun onClear() {
        onClickListener = null
    }
}