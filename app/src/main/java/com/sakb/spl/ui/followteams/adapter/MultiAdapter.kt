package com.sakb.spl.ui.followteams.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.GetTeamResponse

class MultiAdapter(
    private var employees: MutableList<GetTeamResponse.Data?>?,
    var onCLick: (MutableList<GetTeamResponse.Data?>?) -> Unit,
) :
    RecyclerView.Adapter<MultiAdapter.MultiViewHolder>() {

    var employeesResult: MutableList<GetTeamResponse.Data?>? = ArrayList()

    @NonNull
    override fun onCreateViewHolder(
        @NonNull viewGroup: ViewGroup,
        i: Int,
    ): MultiAdapter.MultiViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_team_multi_option, viewGroup, false)
        return MultiViewHolder(view)
    }

    override fun onBindViewHolder(
        @NonNull multiViewHolder: MultiAdapter.MultiViewHolder,
        position: Int,
    ) {
        employees?.get(position)?.let { multiViewHolder.bind(it) }
    }

    override fun getItemCount(): Int {
        return employees?.size ?: 0
    }

    inner class MultiViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.textView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(employee: GetTeamResponse.Data) {

            textView.text = employee.name
            textView.tag = employee.link

            if (employee.isChoice) {
                imageView.setBackgroundResource(R.drawable.square_point)
                if (employeesResult?.isNullOrEmpty() == true) {
                    employeesResult?.add(employee)
                    onCLick(employeesResult)
                } else {
                    if (employeesResult?.contains(employee) != true) {
                        employeesResult?.add(employee)
                        onCLick(employeesResult)
                    }
                }
            } else {
                imageView.setBackgroundResource(R.drawable.square)
                if (employeesResult?.contains(employee) == true) {
                    employeesResult?.remove(employee)
                    onCLick(employeesResult)
                }
            }

            itemView.setOnClickListener {
                if (!employee.isChoice) {
                    employee.isChoice = true
                    imageView.setBackgroundResource(R.drawable.square_point)
                    if (employeesResult?.isNullOrEmpty() == true) {
                        employeesResult?.add(employee)
                        onCLick(employeesResult)
                    } else {
                        if (employeesResult?.contains(employee) != true) {
                            employeesResult?.add(employee)
                            onCLick(employeesResult)
                        }
                    }
                } else {
                    employee.isChoice = false
                    imageView.setBackgroundResource(R.drawable.square)
                    if (employeesResult?.contains(employee) == true) {
                        employeesResult?.remove(employee)
                        onCLick(employeesResult)
                    }
                }
            }
        }
    }
}