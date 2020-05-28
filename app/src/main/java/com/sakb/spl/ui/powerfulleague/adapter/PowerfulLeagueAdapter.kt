package com.sakb.spl.ui.powerfulleague.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.databinding.PowerfulLeagueItemBinding
import com.sakb.spl.databinding.RecyclerSectionHeaderBinding
import timber.log.Timber

class PowerfulLeagueAdapter :
    ListAdapter<PowerfulLeagueUIModel, RecyclerView.ViewHolder>(
        PowerfulLeagueDiffCallback()
    ) {

    var onClickListener: ((position : Int,PowerfulLeagueUIModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {

        if (viewType== 0)
            return FirstItemViewHolder(
                RecyclerSectionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onClickListener
            )
        else
              return  InstructionsViewHolder(
                PowerfulLeagueItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onClickListener
            )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val instructions = getItem(position)
        when(holder){
            is InstructionsViewHolder ->  holder.bind(instructions)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position==0) 0 else 1
    }

    inner class InstructionsViewHolder(
        private val binding: PowerfulLeagueItemBinding,
        private val onClickListener: ((position : Int,PowerfulLeagueUIModel) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.joinButton.setOnClickListener {
                Timber.e("role = clicked")
                onClickListener?.invoke(absoluteAdapterPosition,  getItem(absoluteAdapterPosition))
            }
        }
        fun bind(data : PowerfulLeagueUIModel) = data.run {
            binding.name.text = name
            binding.order.text = absoluteAdapterPosition.toString()
            binding.executePendingBindings()

        }
    }

    inner class FirstItemViewHolder(
        private val binding: RecyclerSectionHeaderBinding,
        private val onClickListener: ((position : Int,PowerfulLeagueUIModel) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
          /*  binding.joinButton.setOnClickListener {
                Timber.e("role = clicked")
                onClickListener?.invoke(absoluteAdapterPosition,  getItem(absoluteAdapterPosition))
            }*/
        }
        fun bind(data : PowerfulLeagueUIModel) = data.run {
            binding.name.text = name
            binding.order.text = absoluteAdapterPosition.toString()
            binding.executePendingBindings()

        }
    }

    fun onClear() {
        onClickListener = null
    }
}