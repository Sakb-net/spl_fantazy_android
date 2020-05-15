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
            employees!!.get(checkedPosition)
        } else null

/* fun setEmployees(employees:MutableList<teams>) {
this.employees = ArrayList()
this.employees = employees
notifyDataSetChanged()
}*/

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): SingleViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_team_option, viewGroup, false)
        return SingleViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull singleViewHolder: SingleViewHolder, position: Int) {
        singleViewHolder.bind(employees!![position]!!)
    }

    override fun getItemCount(): Int {
        return employees!!.size
    }

    inner class SingleViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView
        private val imageView: ImageView

        init {
            textView = itemView.findViewById(R.id.textView)
            imageView = itemView.findViewById(R.id.imageView)
        }

        fun bind(employee: GetTeamResponse.Data) {
            if (checkedPosition == -1) {
                imageView.setBackgroundResource(R.drawable.circle)
            } else {
                if (checkedPosition == adapterPosition) {
                    imageView.setBackgroundResource(R.drawable.circle_point)
                } else {
                    imageView.setBackgroundResource(R.drawable.circle)
                }
            }
            textView.setText(employee.name)
            textView.setTag(employee.link)

            itemView.setOnClickListener {
                imageView.setBackgroundResource(R.drawable.circle_point)
                if (checkedPosition != adapterPosition) {
                    notifyItemChanged(checkedPosition)
                    checkedPosition = adapterPosition
                    onItemClick?.invoke(adapterPosition, employee)
                }
            }
        }
    }
}