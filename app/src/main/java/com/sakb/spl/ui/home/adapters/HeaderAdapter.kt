package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.data.model.HomePoints
import com.sakb.spl.databinding.ItemHomeHeaderBinding

class HeaderAdapter() : RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    var onChooseTeamClickListener: (() -> Unit)? = null
    var onPointClickListener: (() -> Unit)? = null
    var onMyTeamClickListener: (() -> Unit)? = null
    var onTransClickListener: (() -> Unit)? = null
    var homePoints: HomePoints? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HeaderViewHolder(
            ItemHomeHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            ),
            onChooseTeamClickListener,
            onPointClickListener,
            onMyTeamClickListener,
            onTransClickListener
        )

    override fun onBindViewHolder(holderHeader: HeaderViewHolder, position: Int) {
        holderHeader.bind(homePoints)
    }

    override fun getItemCount() = HEADER

    class HeaderViewHolder(
        private val binding: ItemHomeHeaderBinding,
        private val onChooseTeamClickListener: (() -> Unit)?,
        private val onPointClickListener: (() -> Unit)?,
        private val onMyTeamClickListener: (() -> Unit)?,
        private val onTransClickListener: (() -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        val user by lazy {
            PrefManager.getUser()
        }

        fun bind(homePoints: HomePoints?) {
            if (user?.data?.chooseTeam == null || user?.data?.chooseTeam == 0) {
                binding.buttonChooseTeam.visibility = View.VISIBLE
                binding.llPoints.visibility = View.GONE
                binding.llChooseTeam.visibility = View.GONE
            } else {
                binding.buttonChooseTeam.visibility = View.GONE
                binding.llPoints.visibility = View.VISIBLE
                binding.llChooseTeam.visibility = View.VISIBLE
            }
            itemView.run {
                binding.buttonChooseTeam.setOnClickListener {
                    onChooseTeamClickListener?.invoke()
                }
            }

            binding.llInsidePoints?.setOnClickListener {
                onPointClickListener?.invoke()
            }
            binding.llInsideTeam?.setOnClickListener {
                onMyTeamClickListener?.invoke()
            }
            binding.llInsideTrans?.setOnClickListener {
                onTransClickListener?.invoke()
            }

            homePoints?.let {
                binding.upValue.text = it.totalAvg.toString()
                binding.highestBody.text = it.heighPoint.toString()
                binding.pointBody.text = it.totalSum.toString()
            }
        }
    }

    fun onClear() {
        onChooseTeamClickListener = null
        onMyTeamClickListener = null
        onPointClickListener = null
        onTransClickListener = null
    }

    fun homePoint(homePoints: HomePoints) {
        this.homePoints = homePoints
        notifyDataSetChanged()
    }

    companion object {
        private const val HEADER = 1
    }
}