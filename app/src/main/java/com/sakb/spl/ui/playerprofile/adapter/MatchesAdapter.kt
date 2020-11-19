package com.sakb.spl.ui.playerprofile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.PlayerData
import com.sakb.spl.data.model.StatisticsDataItem
import com.sakb.spl.databinding.ItemResultsMatchsBinding


class MatchesAdapter(var context: Context) :
    ListAdapter<StatisticsDataItem, MatchesAdapter.MatchesViewHolder>(
        MatchesDiffCallback()
    ) {

    var onClickListener: ((StatisticsDataItem) -> Unit)? = null
    var dataPlayerData: PlayerData? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MatchesViewHolder(
            ItemResultsMatchsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        val statisticsDataItem = getItem(position)
        holder.bind(statisticsDataItem, context, dataPlayerData)
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() >= 3) {
            3
        } else {
            super.getItemCount()
        }
    }

    class MatchesViewHolder(
        private val binding: ItemResultsMatchsBinding,
        private val onMatchClick: ((StatisticsDataItem) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StatisticsDataItem, context: Context, dataPlayerData: PlayerData?) =
            data.run {

                binding.btnViewMatch.setOnClickListener { onMatchClick?.invoke(this) }

                binding.team1Tv.text = data.ownTeam
                binding.dateResultTeamOne.text = data.ownTeamResult.toString()

                binding.team2Tv.text = data.againestTeam
                binding.dateResultTeamTwo.text = data.againestTeamResult.toString()

                binding.gwTv.text = data.week.toString()

                binding.pointTv.text = data.points.toString()

                binding.viewMatchDetailsLl.setOnClickListener {
                    if (binding.btnViewMatch.isVisible) {
                        binding.btnViewMatch.visibility = View.GONE
                        binding.pointll.visibility = View.GONE
                        binding.playerGwLl.visibility = View.GONE
                    } else {
                        binding.btnViewMatch.visibility = View.VISIBLE
                        binding.pointll.visibility = View.VISIBLE
                        binding.playerGwLl.visibility = View.GONE
                    }
                }

                dataPlayerData?.let {
                    binding.valueBodyInside.text = it.cost.toString()
                    binding.ictBodyInside.text = "${it.point} ${context.getString(R.string.pts)}"
                    binding.formBodyInside.text = "-"
                }

                data.statistic?.let { list ->
                    if (list.isNullOrEmpty()) {
                        binding.pointll.visibility = View.GONE
                    } else {
                        list.forEach {
                            val vi =
                                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                            val v: View = vi.inflate(R.layout.item_statistics_details, null)

                            val pointBody: AppCompatTextView = v.findViewById(R.id.pointBody)
                            val valueBody: AppCompatTextView = v.findViewById(R.id.valueBody)
                            val ptsBody: AppCompatTextView = v.findViewById(R.id.ptsBody)
                            pointBody.text = it?.langPoint
                            valueBody.text = it?.number.toString()
                            ptsBody.text = it?.points.toString()
                            binding.pointll.addView(v)
                        }
                    }
                }

            }


    }

    fun addDataPlayer(data: PlayerData?) {
        dataPlayerData = data
        notifyDataSetChanged()
    }

    fun onClear() {
        onClickListener = null
    }
}