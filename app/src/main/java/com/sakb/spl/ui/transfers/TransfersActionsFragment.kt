package com.sakb.spl.ui.transfers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings
import com.oppwa.mobile.connect.provider.Connect
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.ArrayPlayerRequest
import com.sakb.spl.data.model.PlayerResponse
import com.sakb.spl.data.model.PlayersSubtitle
import com.sakb.spl.ui.home.HomeFragment
import com.sakb.spl.ui.transfers.TransfersFragment.Companion.PLAYER_SUB
import com.sakb.spl.ui.transfers.adapter.PlayerInOutAdapter
import com.sakb.spl.utils.LanguageUtil
import com.sakb.spl.utils.showWarningDialog
import kotlinx.android.synthetic.main.fragment_transfers_actions.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TransfersActionsFragment : BaseFragment() {
    override val viewModel by sharedViewModel<TransfersViewModel>()
    var playerInList: ArrayList<String> = ArrayList()
    var playerOutList: ArrayList<String> = ArrayList()
    var playerInDataList: ArrayList<PlayerResponse> = ArrayList()
    var playerOuDatatList: ArrayList<PlayerResponse> = ArrayList()
    var grayCardStatus = ""

    val players by lazy {
        arguments?.getParcelableArrayList<PlayersSubtitle>(PLAYER_SUB)
    }

    lateinit var playerInOutAdapter: PlayerInOutAdapter

    var arrayPlayer = ArrayList<ArrayPlayerRequest>()
    var arrayPlayerString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transfers_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCardStatus(GRAY)
        playerInOutAdapter = PlayerInOutAdapter(
            this@TransfersActionsFragment.requireContext(),
            playerInDataList,
            playerOuDatatList
        )
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
        viewModel.playerInResponse.observe(viewLifecycleOwner, {
            playerInDataList.add(it)
            playerInOutAdapter.updateIn(it)
        })
        viewModel.playerOutResponse.observe(viewLifecycleOwner, {
            playerOuDatatList.add(it)
            playerInOutAdapter.updateOut(it)
        })

        tv_info.text =
            "${getString(R.string.you_are_used)} ${HomeFragment.transfersData.transferFree} ${
                getString(
                    R.string.free_trans
                )
            } \n" +
                    "${getString(R.string.get_extra_transfers_point)} ( ${
                        HomeFragment.transfersData.changePoint?.times(
                            players?.size ?: 1
                        )
                    } ${getString(R.string.point)} )\n" +
                    "${getString(R.string.money_remainig)}  ${HomeFragment.transfersData.moneyRemaining} "

        viewModel.cardStatus.observe(viewLifecycleOwner, {
            it?.data?.let { dataCard ->
                grayCardStatus = if (dataCard.activeCard == 1) {
                    "1"
                } else {
                    "0"
                }
            }
        })

        viewModel.getGoldInfoResponse.observe(viewLifecycleOwner, {
            it?.data?.let { cardInfo ->

                val paymentBrands: MutableSet<String> = LinkedHashSet()

                paymentBrands.add("VISA")
                paymentBrands.add("MASTER")

                CHECKOUT_ID = cardInfo.checkoutId ?: ""
                val checkoutSettings =
                    CheckoutSettings(
                        CHECKOUT_ID,
                        paymentBrands,
                        Connect.ProviderMode.TEST
                    )
                // Set shopper result URL
                if (LanguageUtil.isArabic()) {
                    checkoutSettings.locale = "ar"
                } else {
                    checkoutSettings.locale = "en"
                }
                checkoutSettings.checkoutId = CHECKOUT_ID
                checkoutSettings.shopperResultUrl = cardInfo.shopperResultUrl ?: ""
                val intent =
                    checkoutSettings.createCheckoutActivityIntent(this@TransfersActionsFragment.requireContext())
                startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT)
            }
        })
        viewModel.getSubDefaultResponse.observe(viewLifecycleOwner, {
            findNavController().popBackStack(R.id.transfersActionFragment, true)
            //activity?.supportFragmentManager?.popBackStack()
        })
        buttonGoldCard.setOnClickListener {
            openGoldCardDialog()
        }
        buttonSilverCard.setOnClickListener {
            if (grayCardStatus == "0") {
                openSilverCardDialog()
            } else if (grayCardStatus == "1") {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.you_cant_use_it_again),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        cancel_button.setOnClickListener {
            findNavController().popBackStack(R.id.transfersActionFragment, true)
            //activity?.supportFragmentManager?.popBackStack()
        }
        confirm_button.setOnClickListener {
            arrayPlayerString = callList(players)
            if (grayCardStatus == "0")
                viewModel.getSubDefault(arrayPlayer = arrayPlayerString, activeCardGray = "")
            else
                viewModel.getSubDefault(arrayPlayer = arrayPlayerString,
                    activeCardGray = grayCardStatus)
        }

    }

    private fun callList(players: ArrayList<PlayersSubtitle>?): String {
        players?.let { list ->
            list.forEach {
                arrayPlayer.add(ArrayPlayerRequest(newplayerId = it.newPlayerId,
                    substituteplayerId = it.oldPlayerId,
                    substituteplayerCost = it.oldPlayerCost))
            }
        }
        return "${Gson().toJson(arrayPlayer)}"
    }

    private fun openSilverCardDialog() {
        context?.showWarningDialog(
            R.drawable.ic_sliver_card,
            R.string.silver_card,
            R.string.silver_card_content,
            { dialog ->
                grayCardStatus = "1"
                dialog?.dismiss()
            },
            { dialog ->
                grayCardStatus = "0"
                dialog?.dismiss()
            })
    }


    private fun openGoldCardDialog() {
        context?.showWarningDialog(
            R.drawable.ic_golden_card,
            R.string.gold_card,
            R.string.gold_card_content,
            { dialog ->
                arrayPlayerString = callList(players)
                viewModel.getGoldInfo(arrayPlayerString)
                dialog?.dismiss()
            },
            { dialog ->
                dialog?.dismiss()
            })
    }

    companion object {
        const val GRAY = "gray"
        var CHECKOUT_ID = ""
    }
}