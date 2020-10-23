package com.sakb.spl.ui.transfers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.AddPlayerResponse
import com.sakb.spl.data.model.ChangePlayerResponse
import com.sakb.spl.databinding.FragmentTransfersBinding
import com.sakb.spl.ui.playerprofile.PlayerProfileActivity
import com.sakb.spl.ui.transfers.adapter.MyTeamPlayersMasterAdapter
import com.sakb.spl.ui.transfers.adapter.menu.MyTeamPlayersMasterMenuAdapter
import com.sakb.spl.utils.showEnterTeamNameDialog
import com.sakb.spl.utils.showWarningDialog
import com.sakb.spl.utils.toast
import kotlinx.android.synthetic.main.fragment_transfers.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class TransfersFragment : BaseFragment() {


    private lateinit var binding: FragmentTransfersBinding

    private lateinit var adapter: MyTeamPlayersMasterAdapter


    override val viewModel by viewModel<TransfersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transfers,
            container,
            false
        )
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadMyTeamPlayers()

        viewModel.MyTeamPlayersListResultLiveData.observe(this, Observer { data ->
            Timber.e("data is ===== ${Gson().toJson(data?.data)}")

            binding.playerNum.text = data.total_team_play.toString().plus(" / 15")
            binding.payTotal.text = data.pay_total_cost.toString()
            binding.menuBtn.setOnClickListener {
                binding.menuBtn.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.colorGreenDark
                )

                binding.preview.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.white
                )

                binding.menuBtn.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                binding.preview.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                viewModel.isMenuPreviewEnabled = true

                binding.stadIv.setImageDrawable(null)
                binding.stadIv.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                data.data?.let {
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

            binding.preview.setOnClickListener {
                binding.preview.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.colorGreenDark
                )

                binding.menuBtn.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.white
                )

                binding.preview.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                binding.menuBtn.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                viewModel.isMenuPreviewEnabled = false

                binding.stadIv.setImageResource(R.drawable.pitch)
                data.data?.let {
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

            binding.buttonChooseTeam.setOnClickListener {

            }

            binding.buttonGoldCard.setOnClickListener {
                openGoldCardDialog()
            }
            binding.buttonSilverCard.setOnClickListener {
                openSilverCardDialog()
            }

            if (viewModel.isMenuPreviewEnabled) {
                binding.stadIv.setImageDrawable(null)
                binding.stadIv.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                data.data?.let {
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
                binding.stadIv.setImageResource(R.drawable.pitch)
                data.data?.let {
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

        viewModel.SaveTeamResponseLiveData.observe(this, Observer {
            it?.let { data ->
                Timber.e("data is ===== ${Gson().toJson(data.data)}")
                context?.toast("" + data.data?.msg_add)
            }
        })

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
