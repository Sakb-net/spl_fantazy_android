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
import com.sakb.spl.data.model.AddPlayerResponse
import com.sakb.spl.data.model.ChangePlayerResponse
import com.sakb.spl.databinding.ChooseTeamPlayersFragmentBinding
import com.sakb.spl.ui.chooseteamplayers.adapter.MyTeamPlayersMasterAdapter
import com.sakb.spl.ui.chooseteamplayers.adapter.menu.MyTeamPlayersMasterMenuAdapter
import com.sakb.spl.ui.playerprofile.PlayerProfileActivity
import com.sakb.spl.utils.showEnterTeamNameDialog
import com.sakb.spl.utils.toast
import kotlinx.android.synthetic.main.choose_team_players_fragment.*
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
        // context?.toast("coming!" + event.players?.size)
        //  viewModel.updatePayersMaster(event.players)
        event.let {
            // adapter.updateData(it)
            viewModel.updateData(it)
        }

        /**rv_parent.apply {
        event.players?.let {
        adapter = MyTeamPlayersMasterAdapter(it).apply {
        onItemDeleteClick = { pos, data ->
        context?.toast("del $pos plz!")
        }
        }
        }
        }*/

    }

    @Subscribe
    fun onPlayerChanged(event: ChangePlayerResponse) {
        // context?.toast("coming!" + event.data?.size)
        //  viewModel.updatePayersMaster(event.players)
        event.let {
            // adapter.updateData(it)
            viewModel.updateData(it)
        }

        /**rv_parent.apply {
        event.players?.let {
        adapter = MyTeamPlayersMasterAdapter(it).apply {
        onItemDeleteClick = { pos, data ->
        context?.toast("del $pos plz!")
        }
        }
        }
        }*/

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        binding.menu.setOnClickListener {
//            (activity as MainActivity).binding.drawerLayout.openDrawer(GravityCompat.START)
//        }
//        viewModel = ViewModelProviders.of(this, viewModelFactory)
//            .get(ChooseTeamPlayersViewModel::class.java)

        //val user = PrefManager.getUser()
        //val lang = PrefManager.getLanguage()

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
                //  binding.stadIv.visibility = View.INVISIBLE
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
                    rv_parent.adapter = _adapter

                    /**  rv_parent.apply {
                    adapter = adapter
                    }*/
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
                /**  binding.stadIv.setBackgroundColor(
                ContextCompat.getColor(
                context!!,
                R.color.white
                )
                )*/
                //  binding.stadIv.visibility = View.INVISIBLE
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

                    rv_parent.adapter = adapter

                    /**  rv_parent.apply {
                    adapter = adapter
                    }*/
                }
            }

            binding.buttonChooseTeam.setOnClickListener {
                //TODO Important change chooseTeam form 0 to 1
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
                //   binding.stadIv.visibility = View.INVISIBLE
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

                        onItemDeleteClick = { childPos, parentPos, data ->
                            context?.toast("del $childPos plz!")
                            viewModel.updateAphaData(0.5f, childPos, parentPos, data)
                        }

                        onRestorePlayerClicked = { childPos, parentPos, data ->
                            context?.toast("Restore $childPos plz!")
                            viewModel.updateAphaData(1.0f, childPos, parentPos, data)
                        }


                    }

                    rv_parent.adapter = adapter

                    /**  rv_parent.apply {
                    adapter = adapter
                    }*/
                }
            }

        })


        // handle save button
        viewModel.SaveTeamResponseLiveData.observe(this, Observer {
            it?.let { data ->
                Timber.e("data is ===== ${Gson().toJson(data.data)}")
                context?.toast("" + data.data?.msg_add)
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
