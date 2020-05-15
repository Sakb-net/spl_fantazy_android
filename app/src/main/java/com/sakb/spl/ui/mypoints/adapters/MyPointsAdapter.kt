package com.sakb.spl.ui.mypoints.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.data.model.PlayerMasterResponse
import com.sakb.spl.databinding.MyPointChildItemBinding
import com.sakb.spl.ui.mypoints.callback.MyPointsDiffCallback
import timber.log.Timber

class MyPointsAdapter :
    ListAdapter< PlayerMasterResponse.Data, MyPointsAdapter.InstructionsViewHolder>(
        MyPointsDiffCallback()
    ) {

    var onClickListener: ((position : Int, PlayerMasterResponse.Data) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        InstructionsViewHolder(
            MyPointChildItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickListener
        )

    override fun onBindViewHolder(holder: InstructionsViewHolder, position: Int) {
        val instructions = getItem(position)
        holder.bind(instructions)
    }

    inner class InstructionsViewHolder(
        private val binding: MyPointChildItemBinding,
        private val onClickListener: ((position : Int,PlayerMasterResponse.Data) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                Timber.e("role = clicked")
                onClickListener?.invoke(absoluteAdapterPosition,  getItem(absoluteAdapterPosition))
            }
        }
        fun bind(data : PlayerMasterResponse.Data) = data.run {
//            binding.title.text = title
//            binding.content.text = content
//            if (isActivated==true){
//                binding.content.visibility = View.VISIBLE
//                binding.img.setImageResource(R.drawable.ic_expand_more_white_24dp)
//            }
//            else{
//                binding.content.visibility = View.GONE
//                binding.img.setImageResource(R.drawable.ic_navigate_next_black_24dp)
//            }
            binding.executePendingBindings()

        }
    }

    fun onClear() {
        onClickListener = null
    }
}