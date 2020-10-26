package com.sakb.spl.ui.transfers

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.youtube.player.internal.d
import com.google.gson.Gson
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.*
import com.sakb.spl.ui.home.HomeFragment.Companion.TRANSFERS_DATA
import com.sakb.spl.ui.home.HomeFragment.Companion.transfersData
import com.sakb.spl.ui.playerprofile.PlayerProfileActivity
import com.sakb.spl.ui.transfers.adapter.MyTeamPlayersMasterAdapter
import com.sakb.spl.ui.transfers.adapter.menu.MyTeamPlayersMasterMenuAdapter
import com.sakb.spl.utils.showWarningDialog
import com.sakb.spl.utils.toast
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_transfers.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class TransfersFragment : BaseFragment() {
    private lateinit var adapter: MyTeamPlayersMasterAdapter
    override val viewModel by sharedViewModel<TransfersViewModel>()

    lateinit var masterPlayer : PlayerMasterResponse
    lateinit var newMasterPlayer : PlayerMasterResponse

    lateinit var playersSubtitle: PlayersSubtitle

    lateinit var playersSubtitleList:ArrayList<PlayersSubtitle>

    var firstTime = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
            if(firstTime){
                transfersData.moneyRemaining = data.pay_total_cost
                firstTime  = false
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
                        onItemDeleteClick = { childPos, parentPos, _ ->
                            viewModel.updateAphaData(0.5f, childPos, parentPos/*, data*/)
                        }
                        onRestorePlayerClicked = { childPos, parentPos, _ ->
                            viewModel.updateAphaData(1.0f, childPos, parentPos/*, data*/)
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
                        onItemDeleteClick = { childPos, parentPos, _ ->
                            viewModel.updateAphaData(0.5f, childPos, parentPos/*, data*/)
                        }
                        onRestorePlayerClicked = { childPos, parentPos, _ ->
                            viewModel.updateAphaData(1.0f, childPos, parentPos/*, data*/)
                        }
                    }
                    rv_parent.adapter = adapter
                }
            }

            buttonChooseTeam.setOnClickListener {
//                context?.showEnterTeamNameDialog { dialog, name ->
//                    dialog?.dismiss()
//                    viewModel.saveTeam(
//                        name
//                    )
//                }
                val bundle = bundleOf(PLAYER_SUB to playersSubtitleList)
                findNavController().navigate(R.id.action_transfersFragment_to_transfersActionFragment,bundle)
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

                        onItemDeleteClick = { childPos, parentPos, _ ->
                            // context?.toast("del $childPos plz!")
                            viewModel.updateAphaData(0.5f, childPos, parentPos/*, data*/)
                        }

                        onRestorePlayerClicked = { childPos, parentPos, _ ->
                            //  context?.toast("Restore $childPos plz!")
                            viewModel.updateAphaData(1.0f, childPos, parentPos/*, data*/)
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
                        onItemDeleteClick = { childPos, parentPos, _ ->
                            viewModel.updateAphaData(0.5f, childPos, parentPos/*, data*/)
                        }
                        onRestorePlayerClicked = { childPos, parentPos, _ ->
                            viewModel.updateAphaData(1.0f, childPos, parentPos/*, data*/)
                        }
                    }
                    rv_parent.adapter = adapter
                }
            }
        })

        viewModel.SaveTeamResponseLiveData.observe(this, {
            it?.let { data ->
                Timber.e("data is ===== ${Gson().toJson(data.data)}")
                context?.toast("" + data.data?.msg_add)
            }
        })

        viewModel.playerResponse.observe(viewLifecycleOwner, {
            it?.let { newPlayer ->
                masterPlayer.data?.let {
                    for (i in 0 until masterPlayer.data?.size!!) {
                        for (j in 0 until masterPlayer.data?.get(i)?.size!!) {
                            if(playersSubtitle.newPlayerLink == masterPlayer.data?.get(i)?.get(j)?.link_player){
                                Toast.makeText(requireContext(), getString(R.string.this_player_in_your_team), Toast.LENGTH_SHORT).show()
                                break
                            }
                            else if (playersSubtitle.oldPlayerLink == masterPlayer.data?.get(i)?.get(j)?.link_player) {
                                val newItem = newPlayer.data?.playerData
                                val oldItem = masterPlayer.data?.get(i)?.get(j)
                                if(transfersData.transferFree?:0 > 0){
                                    transfersData.transferFree =
                                        transfersData.transferFree?.minus(1)
                                    player_num.text = transfersData.transferFree.toString()
                                }else{
                                    transfersData.moneyRemaining = transfersData.moneyRemaining?.minus(
                                        newItem?.cost?.toDouble() ?: 0.0
                                    )
                                    if (transfersData.moneyRemaining?:0.0 >= 0.0) {
                                        pay_total.text = transfersData.moneyRemaining?.toString()
                                    }
                                }
                                if (transfersData.moneyRemaining?:0.0 >= 0.0) {
                                    playersSubtitleList.add(playersSubtitle)
                                    oldItem?.cost_player = newItem?.cost?.toDouble()
                                    oldItem?.link_player = newItem?.link
                                    oldItem?.image_player = newItem?.image
                                    oldItem?.name_player = newItem?.name
                                    oldItem?.point_player = newItem?.point
                                    oldItem?.state_player = newItem?.statePlayer
                                    oldItem?.team = newItem?.team
                                    oldItem?.type_player = newItem?.typePlayer
                                    oldItem?.alPha = 1.0f
                                    oldItem?.type_loc_player = newItem?.locationPlayer
                                    transfersData.daweryLink = oldItem?.eldwry_link
                                } else {
                                    Toast.makeText(requireContext(), getString(R.string.remaining_amount_not_enough), Toast.LENGTH_SHORT).show()
                                }
                                break
                            }
                        }
                    }
                }
                if(playersSubtitleList.size>0){
                   buttonChooseTeam.isEnabled = true
                    buttonChooseTeam.background = ContextCompat.getDrawable(requireContext(),R.drawable.button_home)
                }else{
                    buttonChooseTeam.isEnabled = false
                    buttonChooseTeam.background = ContextCompat.getDrawable(requireContext(),R.drawable.button_home_disable)
                }
                viewModel.updateData(masterPlayer)
            }
        })
    }


    companion object{
        const val PLAYER_SUB = "player_sub"
    }
}

