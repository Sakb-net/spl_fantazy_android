package  com.sakb.spl.ui.chooseteamplayers

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
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.data.model.AddPlayerResponse
import com.sakb.spl.data.model.ChangePlayerResponse
import com.sakb.spl.databinding.ChooseTeamPlayersFragmentBinding
import com.sakb.spl.ui.chooseteamplayers.adapter.MyTeamPlayersMasterAdapter
import com.sakb.spl.ui.chooseteamplayers.adapter.menu.MyTeamPlayersMasterMenuAdapter
import com.sakb.spl.ui.playerprofile.PlayerProfileActivity
import com.sakb.spl.utils.showEnterTeamNameDialog
import com.sakb.spl.utils.toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class ChooseTeamPlayersFragment : BaseFragment() {


    private var _binding: ChooseTeamPlayersFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyTeamPlayersMasterAdapter

    override val viewModel by viewModel<ChooseTeamPlayersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.choose_team_players_fragment,
            container,
            false
        )
        // binding.toolbarTitle.text = getString(R.string.choose_team)

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
            // adapter.updateData(it)
            viewModel.updateData(it)
        }
    }

    @Subscribe
    fun onPlayerChanged(event: ChangePlayerResponse) {
        event.let {
            // adapter.updateData(it)
            viewModel.updateData(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var text = binding.playerNum.text.toString()
        if(text != "15 / 15"){
            binding.buttonChooseTeam.isEnabled  = false
            binding.buttonChooseTeam.background= ContextCompat.getDrawable(requireContext(),R.drawable.button_home_disable)
        }else{
            binding.buttonChooseTeam.isEnabled = true
            binding.buttonChooseTeam.background= ContextCompat.getDrawable(requireContext(),R.drawable.button_home)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loadMyTeamPlayers()

        binding.autoSelectionLayout.setOnClickListener {
            viewModel.autoSelectionTeamPlayers()
        }
        binding.deleteAllLayout.setOnClickListener {
            viewModel.deleteAllTeamPlayers()
        }

        viewModel.MyTeamPlayersListResultLiveData.observe(this, Observer { data ->
            Timber.e("data is ===== ${Gson().toJson(data?.data)}")

            binding.playerNum.text = data.total_team_play.toString().plus(" / 15")
            binding.payTotal.text = data.pay_total_cost.toString()
            val text = binding.playerNum.text.toString()
            if (text != "15 / 15") {
                binding.buttonChooseTeam.isEnabled = false
                binding.buttonChooseTeam.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.button_home_disable)
            } else {
                binding.buttonChooseTeam.isEnabled = true
                binding.buttonChooseTeam.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.button_home)
            }

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

                        onItemDeleteClick = { childPos, parentPos, data ->
                            context?.toast("del $childPos plz!")
                            viewModel.updateAphaData(0.5f, childPos, parentPos, data)
                        }

                        onRestorePlayerClicked = { childPos, parentPos, data ->
                            context?.toast("Restore $childPos plz!")
                            viewModel.updateAphaData(1.0f, childPos, parentPos, data)
                        }


                    }
                    binding.rvParent.adapter = _adapter
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

                        onItemDeleteClick = { childPos, parentPos, data ->
                            context?.toast("del $childPos plz!")
                            viewModel.updateAphaData(0.5f, childPos, parentPos, data)
                        }

                        onRestorePlayerClicked = { childPos, parentPos, data ->
                            context?.toast("Restore $childPos plz!")
                            viewModel.updateAphaData(1.0f, childPos, parentPos, data)
                        }


                    }

                    binding.rvParent.adapter = adapter
                }
            }

            binding.buttonChooseTeam.setOnClickListener {
                context?.showEnterTeamNameDialog { dialog, name ->
                    dialog?.dismiss()
                    viewModel.saveTeam(
                        name
                    )
                }
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
                        onItemDeleteClick = { childPos, parentPos, data ->
                            context?.toast("del $childPos plz!")
                            viewModel.updateAphaData(0.5f, childPos, parentPos, data)
                        }

                        onRestorePlayerClicked = { childPos, parentPos, data ->
                            context?.toast("Restore $childPos plz!")
                            viewModel.updateAphaData(1.0f, childPos, parentPos, data)
                        }


                    }
                    binding.rvParent.adapter = _adapter
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

                        onItemDeleteClick = { childPos, parentPos, data ->
                            context?.toast("del $childPos plz!")
                            viewModel.updateAphaData(0.5f, childPos, parentPos, data)
                        }

                        onRestorePlayerClicked = { childPos, parentPos, data ->
                            context?.toast("Restore $childPos plz!")
                            viewModel.updateAphaData(1.0f, childPos, parentPos, data)
                        }

                    }
                    binding.rvParent.adapter = adapter
                }
            }

        })


        viewModel.SaveTeamResponseLiveData.observe(this, Observer {
            it?.let { data ->
                Timber.e("data is ===== ${Gson().toJson(data.data)}")
                user?.data?.chooseTeam = 1
                PrefManager.saveUser(user)
                context?.toast("" + data.data?.msg_add)
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
