package  com.sakb.spl.ui.chooseteamplayers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.PlayerMasterResponse
import kotlinx.android.synthetic.main.parent_item_team_master_recycler.view.*


class MyTeamPlayersMasterAdapter(
    private var parents: List<List<PlayerMasterResponse.Data>?>
) : RecyclerView.Adapter<MyTeamPlayersMasterAdapter.ViewHolder>() {

    var onItemDeleteClick: ((pos: Int, parentPosition: Int, PlayerMasterResponse.Data) -> Unit)? =
        null
    var onOpenProfileClicked: ((pos: Int, PlayerMasterResponse.Data) -> Unit)? = null
    var onRestorePlayerClicked: ((pos: Int, parentPosition: Int, PlayerMasterResponse.Data) -> Unit)? =
        null


    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_item_team_master_recycler, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return parents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = parents[position]
        holder.recyclerView.apply {
            // layoutManager = LinearLayoutManager(holder.recyclerView
            // .context, LinearLayout.HORIZONTAL, false)
            parents[position]?.let {
                adapter = PlayersMasterChildItemAdapter(
                    it,
                    position,
                    onItemDeleteClick,
                    onOpenProfileClicked,
                    onRestorePlayerClicked
                )
            }
            setRecycledViewPool(viewPool)
        }
    }

    fun updateData(players: List<List<PlayerMasterResponse.Data>?>) {
        this.parents = players
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.rv_child
    }
}