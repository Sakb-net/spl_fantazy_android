package com.sakb.spl.ui.leagueorder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.RankingEldwryItem
import com.sakb.spl.databinding.ItemLeagueRankingBinding
import com.sakb.spl.ui.leagueorder.diffcallback.LeagueRankingDiffCallback

class LeagueRankingAdapter(var context: Context) :
    ListAdapter<RankingEldwryItem, LeagueRankingAdapter.RankingViewHolder>(
        LeagueRankingDiffCallback()
    ) {

    var onClickListener: ((RankingEldwryItem, Int) -> Unit)? = null
    var expand = true
    var pos = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RankingViewHolder(
            ItemLeagueRankingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )

    override fun onBindViewHolder(holder: LeagueRankingAdapter.RankingViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem, position)
    }

    inner class RankingViewHolder(
        private val binding: ItemLeagueRankingBinding,
        private val onDataClick: ((RankingEldwryItem, Int) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RankingEldwryItem, position: Int) = data.run {
            binding.tvRanking.text = "${position + 1}"
            binding.tvNameTeam.text = data.teamCode
            Glide.with(context)
                .load(Constants.baseUrl + data.teamImage)
                .override(39)
                .into(binding.ivName)
            binding.tvPlay.text = data.countPlayed
            binding.tvWin.text = data.won
            binding.tvBalance.text = data.draw
            binding.tvLose.text = data.loss
            binding.tvRem.text = data.goalsDiff
            binding.tvPoint.text = data.points
            binding.more.setOnClickListener {
                onDataClick?.invoke(this, position)
            }
            if (pos == position) {
                if (expand) {
                    binding.infoGame.visibility = View.GONE
                    binding.more.rotation = 0f
                    data.expandList = false
                } else {
                    binding.infoGame.visibility = View.VISIBLE
                    binding.more.rotation = 180f
                    data.expandList = true
                }
            }
            data.form?.let { list ->
                for (i in list.indices) {
                    when (i) {
                        0 -> {
                            binding.matchOne.visibility = View.VISIBLE
                            binding.matchOne.text = list[i]?.stateLang
                            binding.matchOne.background = when (list[i]?.state) {
                                "w" -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                                "l" -> {
                                    ContextCompat.getDrawable(context, R.drawable.lose)
                                }
                                "d" -> {
                                    ContextCompat.getDrawable(context, R.drawable.tie)
                                }
                                else -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                            }
                        }
                        1 -> {
                            binding.matchTwo.visibility = View.VISIBLE
                            binding.matchTwo.text = list[i]?.stateLang
                            binding.matchTwo.background = when (list[i]?.state) {
                                "w" -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                                "l" -> {
                                    ContextCompat.getDrawable(context, R.drawable.lose)
                                }
                                "d" -> {
                                    ContextCompat.getDrawable(context, R.drawable.tie)
                                }
                                else -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                            }
                        }
                        2 -> {
                            binding.matchThree.visibility = View.VISIBLE
                            binding.matchThree.text = list[i]?.stateLang
                            binding.matchThree.background = when (list[i]?.state) {
                                "w" -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                                "l" -> {
                                    ContextCompat.getDrawable(context, R.drawable.lose)
                                }
                                "d" -> {
                                    ContextCompat.getDrawable(context, R.drawable.tie)
                                }
                                else -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                            }
                        }
                        3 -> {
                            binding.matchFour.visibility = View.VISIBLE
                            binding.matchFour.text = list[i]?.stateLang
                            binding.matchFour.background = when (list[i]?.state) {
                                "w" -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                                "l" -> {
                                    ContextCompat.getDrawable(context, R.drawable.lose)
                                }
                                "d" -> {
                                    ContextCompat.getDrawable(context, R.drawable.tie)
                                }
                                else -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                            }
                        }
                        4 -> {
                            binding.matchFive.visibility = View.VISIBLE
                            binding.matchFive.text = list[i]?.stateLang
                            binding.matchFive.background = when (list[i]?.state) {
                                "w" -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                                "l" -> {
                                    ContextCompat.getDrawable(context, R.drawable.lose)
                                }
                                "d" -> {
                                    ContextCompat.getDrawable(context, R.drawable.tie)
                                }
                                else -> {
                                    ContextCompat.getDrawable(context, R.drawable.win)
                                }
                            }
                        }
                    }
                }
            }

            data.currentMatch?.let { currentMatch ->
                binding.lastMatchLL.visibility = View.VISIBLE
                binding.lastTeam1Tv.text = currentMatch.firstTeamName
                Glide.with(context)
                    .load(Constants.baseUrl + currentMatch.firstTeamImage)
                    .override(39)
                    .into(binding.lastTeam1Iv)
                binding.lastDateResultTeamOne.text = currentMatch.firstTeamGoon
                binding.lastTeam2Tv.text = currentMatch.secondTeamName
                Glide.with(context)
                    .load(Constants.baseUrl + currentMatch.secondTeamImage)
                    .override(39)
                    .into(binding.lastTeam2Iv)
                binding.lastDateResultTeamTwo.text = currentMatch.secondTeamGoon
                binding.lastDate.text = currentMatch.dateDay
            }

            data.nextMatch?.let { nextMatch ->
                binding.nextMatchLL.visibility = View.VISIBLE
                binding.nextTeam1Tv.text = data.teamCode
                Glide.with(context)
                    .load(Constants.baseUrl + data.teamImage)
                    .override(39)
                    .into(binding.nextTeam1Iv)

                binding.nextTeam2Tv.text = nextMatch.secondTeamName
                Glide.with(context)
                    .load(Constants.baseUrl + nextMatch.secondTeamImage)
                    .override(39)
                    .into(binding.nextTeam2Iv)

                binding.nextDateResult.text = nextMatch.time
                binding.nextGame.text = nextMatch.dateDay
            }
        }
    }

    fun onClear() {
        onClickListener = null
    }
}