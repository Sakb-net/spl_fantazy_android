package com.sakb.spl.ui.addplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.PlayerByTypeResponse
import com.sakb.spl.databinding.ListItemPlayerByTypeBinding
import com.sakb.spl.ui.playerprofile.PlayerProfileActivity
import timber.log.Timber


/**
 * Created by dev.mahmoud_ashraf on 10/7/2019.
 */
class PlayersByTypeAdapter(
    private var players: List<PlayerByTypeResponse.PlayersGroup?>? = null,
    var context: Context
) :
    RecyclerView.Adapter<PlayersByTypeAdapter.PlayersViewHolder>() {
    var onItemClick: ((pos: Int, data: PlayerByTypeResponse.PlayersGroup?) -> Unit)? = null

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): PlayersViewHolder {
        val binding: ListItemPlayerByTypeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_item_player_by_type, viewGroup, false
        )
        return PlayersViewHolder(binding)

    }

    override fun onBindViewHolder(@NonNull playersViewHolder: PlayersViewHolder, position: Int) {
        players!![position]?.let {
            playersViewHolder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return players!!.size
    }


    inner class PlayersViewHolder(val binding: ListItemPlayerByTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //  private val textView: TextView
        //   private val imageView: ImageView

        init {
            binding.addPlayerBtn.setOnClickListener {
                Timber.e("clicked!!=========" + adapterPosition)
                onItemClick?.invoke(adapterPosition, players!![adapterPosition])
            }
            //     textView = itemView.findViewById(R.id.textView)
            //   imageView = itemView.findViewById(R.id.imageView)
        }

        fun bind(player: PlayerByTypeResponse.PlayersGroup) {
            binding.nameTv.text = player.name
            binding.clubTv.text = player.team?.plus(" - ")?.plus(player.type_player)
            binding.priceTv.text = player.cost.toString()
            // Todo
            binding.pointTv.text = player.point.toString()
            /* if (checkedPosition == -1) {
                 imageView.setBackgroundResource(R.drawable.circle)
             } else {
                 if (checkedPosition == adapterPosition) {
                     imageView.setBackgroundResource(R.drawable.circle_point)
                 } else {
                     imageView.setBackgroundResource(R.drawable.circle)
                 }
             }
             textView.setText(employee.name)
             textView.setTag(employee.link)*/

            itemView.setOnClickListener {
                context.startActivity(
                    Intent(context, PlayerProfileActivity::class.java).putExtra(
                        "link",
                        player.link
                    )
                )
            }
        }
    }
}