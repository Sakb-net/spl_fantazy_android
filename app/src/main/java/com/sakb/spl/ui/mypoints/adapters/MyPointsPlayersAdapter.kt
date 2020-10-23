package com.sakb.spl.ui.mypoints.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.PlayerMasterItemItem
import com.sakb.spl.ui.myteam.adapter.MyTeamPlayersChildItemAdapter
import kotlinx.android.synthetic.main.parent_item_myteam_recycler.view.*


class MyPointsPlayersAdapter(
    private var parents: List<List<PlayerMasterItemItem>>?
) : RecyclerView.Adapter<MyPointsPlayersAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_item_myteam_recycler, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if (parents?.size?:0 > 4) 4 else parents?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = parents?.get(position)
        holder.recyclerView.apply {
            // layoutManager = LinearLayoutManager(holder.recyclerView
            // .context, LinearLayout.HORIZONTAL, false)
            parents?.get(position)?.let {
                adapter = MyPointPlayersChildItemAdapter(
                    it,
                    position)
            }
            setRecycledViewPool(viewPool)
        }
    }

    fun updateData(players: List<List<PlayerMasterItemItem>>?) {
        this.parents = players
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.rv_child
    }
}