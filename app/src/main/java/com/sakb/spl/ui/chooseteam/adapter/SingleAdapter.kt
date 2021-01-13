package com.sakb.spl.ui.chooseteam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.GetTeamResponse


/**
 * Created by dev.mahmoud_ashraf on 10/7/2019.
 */
class SingleAdapter(private var employees: MutableList<GetTeamResponse.Data?>?) :
    RecyclerView.Adapter<SingleAdapter.SingleViewHolder>() {
    private var checkedPosition = -1
    var onItemClick: ((pos: Int, data: GetTeamResponse.Data) -> Unit)? = null

    val selected: GetTeamResponse.Data?
        get() = if (checkedPosition != -1) {
            employees?.get(checkedPosition)
        } else null

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): SingleViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_team_option, viewGroup, false)
        return SingleViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull singleViewHolder: SingleViewHolder, position: Int) {
        employees?.get(position)?.let { singleViewHolder.bind(it, position) }
    }

    override fun getItemCount(): Int {
        return employees?.size ?: 0
    }

    inner class SingleViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.textView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(employee: GetTeamResponse.Data, position: Int) {
            if (checkedPosition == -1) {
                imageView.setBackgroundResource(R.drawable.circle)
            } else {
                if (checkedPosition == position) {
                    imageView.setBackgroundResource(R.drawable.circle_point)
                } else {
                    imageView.setBackgroundResource(R.drawable.circle)
                }
            }
            textView.text = employee.name
            textView.tag = employee.link

            itemView.setOnClickListener {
                imageView.setBackgroundResource(R.drawable.circle_point)
                if (checkedPosition != position) {
                    notifyItemChanged(checkedPosition)
                    checkedPosition = position
                    onItemClick?.invoke(adapterPosition, employee)
                }
            }
        }
    }
}