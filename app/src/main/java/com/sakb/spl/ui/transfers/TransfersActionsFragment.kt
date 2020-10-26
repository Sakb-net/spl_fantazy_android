package com.sakb.spl.ui.transfers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.DataItemSubFix
import com.sakb.spl.data.model.PlayerResponse
import com.sakb.spl.data.model.PlayersSubtitle
import com.sakb.spl.ui.addplayer.AddPlayerActivity
import com.sakb.spl.ui.home.HomeFragment
import com.sakb.spl.ui.transfers.TransfersFragment.Companion.PLAYER_SUB
import com.sakb.spl.ui.transfers.adapter.PlayerInOutAdapter
import com.sakb.spl.utils.showWarningDialog
import kotlinx.android.synthetic.main.fragment_transfers.*
import kotlinx.android.synthetic.main.fragment_transfers_actions.*
import kotlinx.android.synthetic.main.fragment_transfers_actions.buttonGoldCard
import kotlinx.android.synthetic.main.fragment_transfers_actions.buttonSilverCard
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TransfersActionsFragment : BaseFragment() {
    override val viewModel by sharedViewModel<TransfersViewModel>()
    var playerInList:ArrayList<String> = ArrayList()
    var playerOutList:ArrayList<String> = ArrayList()
    var playerInDataList:ArrayList<PlayerResponse> = ArrayList()
    var playerOuDatatList:ArrayList<PlayerResponse> = ArrayList()

    val players by lazy {
        arguments?.getParcelableArrayList<PlayersSubtitle>(PLAYER_SUB)
    }

    lateinit var playerInOutAdapter: PlayerInOutAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transfers_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerInOutAdapter = PlayerInOutAdapter(this@TransfersActionsFragment.requireContext(),playerInDataList,playerOuDatatList)
        rvPlayer.adapter = playerInOutAdapter

        players?.let { list ->
            list.forEach { player ->
                playerInList.add(player.newPlayerLink)
                playerOutList.add(player.oldPlayerLink)
            }

            playerInList.forEach {
                viewModel.loadInPlayerInfo(it)
            }
            playerOutList.forEach {
                viewModel.loadOutPlayerInfo(it)
            }
        }
        viewModel.playerInResponse.observe(viewLifecycleOwner,{
            playerInDataList.add(it)
            playerInOutAdapter.updateIn(it)
        })
        viewModel.playerOutResponse.observe(viewLifecycleOwner,{
            playerOuDatatList.add(it)
            playerInOutAdapter.updateOut(it)
        })

        tv_info.text = "${getString(R.string.you_are_used)} ${HomeFragment.transfersData.transferFree} ${getString(R.string.free_trans)} \n" +
                "${getString(R.string.get_extra_transfers_point)} ( ${HomeFragment.transfersData.changePoint?.times(players?.size?:1)} ${getString(R.string.point)} )\n" +
                "${getString(R.string.money_remainig) }  ${HomeFragment.transfersData.moneyRemaining} "

        buttonGoldCard.setOnClickListener {
            openGoldCardDialog()
        }
        buttonSilverCard.setOnClickListener {
            openSilverCardDialog()
        }
        cancel_button.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        confirm_button.setOnClickListener {
            callList(players)
        }
    }

    private fun callList(players: ArrayList<PlayersSubtitle>?) {
        players?.let {list->
            list.forEach {
                viewModel.changePlayer(
                    "" + HomeFragment.transfersData.daweryLink,
                    "" + it.oldPlayerLink,
                    "" + it.newPlayerLink
                )
            }
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun openSilverCardDialog() {
        context?.showWarningDialog(
            R.drawable.ic_sliver_card,
            R.string.silver_card,
            R.string.silver_card_content,
            { dialog ->
                dialog?.dismiss()
            },
            { dialog ->
                dialog?.dismiss()
            })
    }


    private fun openGoldCardDialog() {
        context?.showWarningDialog(
            R.drawable.ic_golden_card,
            R.string.gold_card,
            R.string.gold_card_content,
            { dialog ->
                dialog?.dismiss()
            },
            { dialog ->
                dialog?.dismiss()
            })
    }


}