package com.sakb.spl.ui.transfers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.*
import com.sakb.spl.ui.home.HomeFragment.Companion.transfersData
import com.sakb.spl.ui.playerprofile.PlayerProfileActivity
import com.sakb.spl.ui.transfers.adapter.MyTeamPlayersMasterAdapter
import com.sakb.spl.ui.transfers.adapter.menu.MyTeamPlayersMasterMenuAdapter
import kotlinx.android.synthetic.main.fragment_transfers.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class TransfersFragment : BaseFragment() {
    private lateinit var adapter: MyTeamPlayersMasterAdapter
    override val viewModel by sharedViewModel<TransfersViewModel>()

    lateinit var masterPlayer: PlayerMasterResponse
    lateinit var newMasterPlayer: PlayerMasterResponse

    lateinit var playersSubtitle: PlayersSubtitle

    lateinit var playersSubtitleList: ArrayList<PlayersSubtitle>

    var teamsSize: HashMap<String, Int> = HashMap()

    var firstTime = true
    var childReplacePosition = 0
    var parentReplacePosition = 0

    var changeAgain = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_transfers, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playersSubtitleList = ArrayList()
    }
    @Subscribe
    fun onPlayerAdded(event: AddPlayerResponse) {
        event.let {
            viewModel.updateData(it)
        }
    }

    @Subscribe
    fun onPlayerChanged(event: ChangePlayerResponse) {
        event.let {
            viewModel.updateData(it)
        }
    }

    @Subscribe
    fun onEvent(event:PlayersSubtitle ){
        event.let {
            playersSubtitle = event
            playersSubtitle.childPosition = childReplacePosition
            playersSubtitle.parentPosition = parentReplacePosition
            viewModel.loadPlayerInfo(event.newPlayerLink)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadMyTeamPlayers()

        viewModel.MyTeamPlayersListResultLiveData.observe(this, { data ->
            Timber.e("data is ===== ${Gson().toJson(data?.data)}")

            masterPlayer = data
            newMasterPlayer = data

            teamsSize = HashMap()
            masterPlayer.data.let { parentList ->
                for (i in 0 until parentList?.size!!) {
                    for (j in 0 until parentList[i]?.size!!) {
                        if (parentList[i]?.get(j)?.alPha == 1f) {
                            if (teamsSize.isEmpty()) {
                                teamsSize[parentList[i]?.get(j)?.team ?: ""] = 1
                            } else {
                                val team = teamsSize[parentList[i]?.get(j)?.team ?: ""] ?: 0
                                teamsSize[parentList[i]?.get(j)?.team ?: ""] = team + 1
                            }
                        }
                    }
                }
            }

            if (firstTime) {
                transfersData.moneyRemaining = data.pay_total_cost
                firstTime = false
            }
            player_num.text = transfersData.transferFree.toString()
            pay_total.text = transfersData.moneyRemaining.toString()
            menuBtn.setOnClickListener {
                menuBtn.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.colorGreenDark
                )

                preview.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.white
                )

                menuBtn.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                preview.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                viewModel.isMenuPreviewEnabled = true

                stadIv.setImageDrawable(null)
                stadIv.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                masterPlayer.data?.let {
                    val _adapter = MyTeamPlayersMasterMenuAdapter(it).apply {
                        onOpenProfileClicked = { _, data ->
                            startActivity(
                                Intent(context, PlayerProfileActivity::class.java).putExtra(
                                    "link",
                                    data.link_player
                                )
                            )
                        }
                        onItemDeleteClick = { childPos, parentPos, player ->
                            val team = teamsSize[player.team] ?: 0
                            teamsSize[player.team ?: ""] = team - 1
                            Log.d("TAG", "${teamsSize[player.team ?: ""]}")
                            viewModel.updateAphaData(0.5f, childPos, parentPos/*, data*/)
                        }
                        onRestorePlayerClicked = { childPos, parentPos, player ->
                            val team = teamsSize[player.team] ?: 0
                            teamsSize[player.team ?: ""] = team + 1
                            Log.d("TAG", "${teamsSize[player.team ?: ""]}")
                            viewModel.updateAphaData(1.0f, childPos, parentPos/*, data*/)
                        }
                        onReplaceClicked = { childPos, parentPos, player ->
                            childReplacePosition = childPos
                            parentReplacePosition = parentPos
                        }
                    }
                    rv_parent.adapter = _adapter
                }
            }
            preview.setOnClickListener {
                preview.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.colorGreenDark
                )

                menuBtn.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.white
                )

                preview.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                menuBtn.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                viewModel.isMenuPreviewEnabled = false
                stadIv.setImageResource(R.drawable.pitch)
                masterPlayer.data?.let {
                    adapter = MyTeamPlayersMasterAdapter(it).apply {
                        onOpenProfileClicked = { _, data ->
                            startActivity(
                                Intent(context, PlayerProfileActivity::class.java).putExtra(
                                    "link",
                                    data.link_player
                                )
                            )
                        }
                        onItemDeleteClick = { childPos, parentPos, player ->
                            val team = teamsSize[player.team] ?: 0
                            teamsSize[player.team ?: ""] = team - 1
                            Log.d("TAG", "${teamsSize[player.team ?: ""]}")
                            viewModel.updateAphaData(0.5f, childPos, parentPos/*, data*/)
                        }
                        onRestorePlayerClicked = { childPos, parentPos, player ->
                            val team = teamsSize[player.team] ?: 0
                            teamsSize[player.team ?: ""] = team + 1
                            Log.d("TAG", "${teamsSize[player.team ?: ""]}")
                            viewModel.updateAphaData(1.0f, childPos, parentPos/*, data*/)
                        }
                        onReplaceClicked = { childPos, parentPos, player ->
                            childReplacePosition = childPos
                            parentReplacePosition = parentPos
                        }
                    }
                    rv_parent.adapter = adapter
                }
            }
            buttonChooseTeam.setOnClickListener {
                val bundle = bundleOf(PLAYER_SUB to playersSubtitleList)
                findNavController().navigate(
                    R.id.action_transfersFragment_to_transfersActionFragment,
                    bundle
                )
            }

            if (viewModel.isMenuPreviewEnabled) {
                stadIv.setImageDrawable(null)
                stadIv.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                masterPlayer.data?.let {
                    val _adapter = MyTeamPlayersMasterMenuAdapter(it).apply {

                        onOpenProfileClicked = { _, data ->
                            startActivity(
                                Intent(context, PlayerProfileActivity::class.java).putExtra(
                                    "link",
                                    data.link_player
                                )
                            )
                        }

                        onItemDeleteClick = { childPos, parentPos, player ->
                            val team = teamsSize[player.team] ?: 0
                            teamsSize[player.team ?: ""] = team - 1
                            Log.d("TAG", "${teamsSize[player.team ?: ""]}")
                            viewModel.updateAphaData(0.5f, childPos, parentPos/*, data*/)
                        }

                        onRestorePlayerClicked = { childPos, parentPos, player ->
                            val team = teamsSize[player.team] ?: 0
                            teamsSize[player.team ?: ""] = team + 1
                            Log.d("TAG", "${teamsSize[player.team ?: ""]}")
                            viewModel.updateAphaData(1.0f, childPos, parentPos/*, data*/)
                        }

                        onReplaceClicked = { childPos, parentPos, player ->
                            childReplacePosition = childPos
                            parentReplacePosition = parentPos
                        }
                    }
                    rv_parent.adapter = _adapter
                }
            } else {
                stadIv.setImageResource(R.drawable.pitch)
                masterPlayer.data?.let {
                    adapter = MyTeamPlayersMasterAdapter(it).apply {
                        onOpenProfileClicked = { _, data ->
                            startActivity(
                                Intent(context, PlayerProfileActivity::class.java).putExtra(
                                    "link",
                                    data.link_player
                                )
                            )
                        }
                        onItemDeleteClick = { childPos, parentPos, player ->
                            val team = teamsSize[player.team] ?: 0
                            teamsSize[player.team ?: ""] = team - 1
                            Log.d("TAG", "${teamsSize[player.team ?: ""]}")
                            viewModel.updateAphaData(0.5f, childPos, parentPos/*, data*/)
                        }
                        onRestorePlayerClicked = { childPos, parentPos, player ->
                            val team = teamsSize[player.team] ?: 0
                            teamsSize[player.team ?: ""] = team + 1
                            Log.d("TAG", "${teamsSize[player.team ?: ""]}")
                            viewModel.updateAphaData(1.0f, childPos, parentPos/*, data*/)
                        }
                        onReplaceClicked = { childPos, parentPos, player ->
                            childReplacePosition = childPos
                            parentReplacePosition = parentPos
                        }
                    }
                    rv_parent.adapter = adapter
                }
            }
        })

        viewModel.playerResponse.observe(viewLifecycleOwner, { newPlayer ->
            if (teamsSize[newPlayer?.data?.playerData?.team] ?: 0 >= 3) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.your_have_3_player_from_same_team),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.updateData(masterPlayer)
                return@observe
            }
            masterPlayer.data?.let { parentList ->
                newPlayer?.data?.playerData?.let { newPlayerData ->
                    val playersLinksArray = ArrayList<String>()
                    when (newPlayerData.locationPlayer?.trim()) {
                        requireContext().resources.getString(R.string.goalkeeper_trans) -> {
                            for (j in parentList[0]?.indices!!) {
                                playersLinksArray.add(parentList[0]?.get(j)?.link_player ?: "")
                            }
                            if (!playerIsFound(playersLinksArray, newPlayerData.link)) {
                                changePlayer(newPlayer,
                                    parentList[parentReplacePosition]?.get(childReplacePosition))
                            } else {
                                return@observe
                            }
                        }
                        requireContext().resources.getString(R.string.defender_trans) -> {
                            for (j in parentList[1]?.indices!!) {
                                playersLinksArray.add(parentList[1]?.get(j)?.link_player ?: "")
                            }
                            if (!playerIsFound(playersLinksArray, newPlayerData.link)) {
                                changePlayer(newPlayer,
                                    parentList[parentReplacePosition]?.get(childReplacePosition))
                            } else {
                                return@observe
                            }
                        }
                        requireContext().resources.getString(R.string.mid_trans) -> {
                            for (j in parentList[2]?.indices!!) {
                                playersLinksArray.add(parentList[2]?.get(j)?.link_player ?: "")
                            }
                            if (!playerIsFound(playersLinksArray, newPlayerData.link)) {
                                changePlayer(newPlayer,
                                    parentList[parentReplacePosition]?.get(childReplacePosition))
                            } else {
                                return@observe
                            }
                        }
                        requireContext().resources.getString(R.string.attacker_trans) -> {
                            for (j in parentList[3]?.indices!!) {
                                playersLinksArray.add(parentList[3]?.get(j)?.link_player ?: "")
                            }
                            if (!playerIsFound(playersLinksArray, newPlayerData.link)) {
                                changePlayer(newPlayer,
                                    parentList[parentReplacePosition]?.get(childReplacePosition))
                            } else {
                                return@observe
                            }
                        }
                        else -> {
                            return@observe
                        }
                    }

                }
                if (playersSubtitleList.size > 0) {
                    buttonChooseTeam.isEnabled = true
                    buttonChooseTeam.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.button_home)
                } else {
                    buttonChooseTeam.isEnabled = false
                    buttonChooseTeam.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.button_home_disable)
                }
                viewModel.updateData(masterPlayer)
            }
        })
    }

    private fun playerIsFound(playersLinksArray: ArrayList<String>, link: String?): Boolean {
        return if (playersLinksArray.contains(link)) {
            Toast.makeText(
                requireContext(),
                getString(R.string.this_player_in_your_team),
                Toast.LENGTH_SHORT
            ).show()
            true
        } else {
            false
        }
    }


    private fun changePlayer(newPlayer: PlayerResponse, oldPlayer: PlayerMasterResponse.Data?) {
        val newItem = newPlayer.data?.playerData

        playersSubtitle.oldPlayerId = oldPlayer?.player_id.toString()
        playersSubtitle.newPlayerId = newItem?.playerId.toString()
        playersSubtitle.oldPlayerCost = oldPlayer?.cost_player.toString()

        if (playersSubtitleList.isEmpty()) {
            playersSubtitleList.add(playersSubtitle)
        } else {
            for (i in playersSubtitleList.indices) {
                val playersSubtitleNew = playersSubtitleList[i]
                if (playersSubtitleList[i].newPlayerLink == oldPlayer?.link_player) {
                    playersSubtitleNew.newPlayerLink = newItem?.link ?: ""
                    playersSubtitleList[i] = playersSubtitleNew
                    changeAgain = true
                }
            }
            if (!changeAgain) {
                playersSubtitleList.add(playersSubtitle)
            }
        }
        if (!changeAgain) {
            if (transfersData.transferFree ?: 0 > 0) {
                transfersData.transferFree =
                    transfersData.transferFree?.minus(1)
                player_num.text = transfersData.transferFree.toString()
            } else {
                transfersData.moneyRemaining = transfersData.moneyRemaining?.minus(
                    newItem?.cost?.toDouble() ?: 0.0
                )
                if (transfersData.moneyRemaining ?: 0.0 >= 0.0) {
                    pay_total.text = transfersData.moneyRemaining?.toString()
                }
            }
        }
        if (transfersData.moneyRemaining ?: 0.0 >= 0.0) {
            oldPlayer?.cost_player = newItem?.cost?.toDouble()
            oldPlayer?.link_player = newItem?.link
            oldPlayer?.image_player = newItem?.image
            oldPlayer?.name_player = newItem?.name
            oldPlayer?.point_player = newItem?.point
            oldPlayer?.state_player = newItem?.statePlayer
            oldPlayer?.team = newItem?.team
            oldPlayer?.type_player = newItem?.typePlayer
            oldPlayer?.alPha = 1.0f
            oldPlayer?.player_id = newItem?.id
            transfersData.daweryLink = oldPlayer?.eldwry_link
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.remaining_amount_not_enough),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val PLAYER_SUB = "player_sub"
    }

}
