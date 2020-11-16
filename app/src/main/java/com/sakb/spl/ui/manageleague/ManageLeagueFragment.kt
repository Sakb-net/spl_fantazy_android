package com.sakb.spl.ui.manageleague

import android.content.ClipData
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.DataItemSubGroup
import com.sakb.spl.data.model.UsersGroupItemSetting
import com.sakb.spl.ui.myleague.MyLeagueFragment
import kotlinx.android.synthetic.main.manage_league_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageLeagueFragment : BaseFragment() {

    override val viewModel by viewModel<ManageLeagueViewModel>()
    var option = mutableListOf<DataItemSubGroup>()
    var optionPlayerManager = mutableListOf<UsersGroupItemSetting>()
    var optionPlayerDelete = mutableListOf<UsersGroupItemSetting>()
    var builder: AlertDialog.Builder? = null
    var builderPlayerManager: AlertDialog.Builder? = null
    var builderPlayerDelete: AlertDialog.Builder? = null

    var linkSub: String = ""
    var playerManager: String = ""
    var playerDelete: String = ""

    val link: String? by lazy {
        arguments?.getString(MyLeagueFragment.LINK_LEAGUE, "")
    }

    val type: String? by lazy {
        arguments?.getString(MyLeagueFragment.LINK_TYPE, "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.manage_league_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadAllSubeldawry(type ?: "", link ?: "")
        viewModel.loadSettingGroup(type ?: "", link ?: "")

        viewModel.allSubeldawry.observe(viewLifecycleOwner, Observer {
            it?.data?.let { list ->
                if (list.isNullOrEmpty()) {
                    league_details.visibility = View.GONE
                } else {
                    option = list.filterNotNull() as MutableList<DataItemSubGroup>
                    builder = AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
                    initDialogRounds()
                }
            }
        })

        viewModel.settingGroupsResponse.observe(viewLifecycleOwner, {
            it.data?.let { dataSetting ->
                code_league_code.text = dataSetting.groupEldwry?.code
                copy_btn_league_code.setOnClickListener {
                    val sdk = Build.VERSION.SDK_INT
                    if (sdk < Build.VERSION_CODES.HONEYCOMB) {
                        val clipboard =
                            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                        clipboard?.text = dataSetting.groupEldwry?.code
                    } else {
                        val clipboard =
                            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager?
                        val clip = ClipData.newPlainText(getString(R.string.code_league),
                            dataSetting.groupEldwry?.code)
                        clipboard?.setPrimaryClip(clip)
                    }
                }
                dataSetting.usersGroup?.let { list ->
                    if (list.isNullOrEmpty()) {
                        league_delete_player.visibility = View.GONE
                        league_change_manager.visibility = View.GONE
                    } else {
                        optionPlayerManager =
                            list.filterNotNull() as MutableList<UsersGroupItemSetting>
                        optionPlayerDelete =
                            list.filterNotNull() as MutableList<UsersGroupItemSetting>
                        builderPlayerManager =
                            AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
                        builderPlayerDelete =
                            AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
                        initDialogPlayerManagerRounds()
                        initDialogPlayerDeleteRounds()
                    }
                }
            }
        })

        viewModel.updateGroupResponse.observe(viewLifecycleOwner, {
            it.data.let {
                viewModel.loadSettingGroup(type ?: "", link ?: "")
            }
        })

        viewModel.deleteGroup.observe(viewLifecycleOwner, {
            it.data.let { data ->
                if (data == true) {
                    findNavController().popBackStack(R.id.manageLeagueFragment, true)
                }
            }
        })

        viewModel.switchAdmin.observe(viewLifecycleOwner, {
            it.data.let { data ->
                if (data == true) {
                    viewModel.loadSettingGroup(type ?: "", link ?: "")
                }
            }
        })
        viewModel.deletePlayer.observe(viewLifecycleOwner, {
            it.data?.let { date ->
                if (date.update == true) {
                    date.usersGroup?.let { list ->
                        if (list.isNullOrEmpty()) {
                            league_delete_player.visibility = View.GONE
                            league_change_manager.visibility = View.GONE
                        } else {
                            optionPlayerManager =
                                list.filterNotNull() as MutableList<UsersGroupItemSetting>
                            optionPlayerDelete =
                                list.filterNotNull() as MutableList<UsersGroupItemSetting>
                            builderPlayerManager =
                                AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
                            builderPlayerDelete =
                                AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
                            initDialogPlayerManagerRounds()
                            initDialogPlayerDeleteRounds()
                        }
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        league_code.setOnClickListener {
            if (ll_league_code.isVisible) {
                ll_league_code.visibility = View.GONE
            } else {
                ll_league_code.visibility = View.VISIBLE
            }
        }
        league_details.setOnClickListener {
            if (ll_league_details.isVisible) {
                ll_league_details.visibility = View.GONE
            } else {
                ll_league_details.visibility = View.VISIBLE
            }
        }
        round_layout.setOnClickListener {
            builder?.show()
        }
        buttonSend.setOnClickListener {
            if (nameEt.text?.toString()?.trim().isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.all_fileds_must_filled),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                nameEt.text?.toString()?.trim()?.let { name ->
                    viewModel.loadUpdateGroup(type ?: "", link ?: "", linkSub, name)
                }
            }
        }

        league_delete_player.setOnClickListener {
            if (ll_league_delete_player.isVisible) {
                ll_league_delete_player.visibility = View.GONE
            } else {
                ll_league_delete_player.visibility = View.VISIBLE
            }
        }

        round_layout_delete_player.setOnClickListener {
            builderPlayerDelete?.show()
        }
        buttonDeletePlayer.setOnClickListener {
            viewModel.loadDeletePlayer(type ?: "", link ?: "", playerDelete)
        }
        league_change_manager.setOnClickListener {
            if (ll_league_change_manager.isVisible) {
                ll_league_change_manager.visibility = View.GONE
            } else {
                ll_league_change_manager.visibility = View.VISIBLE
            }
        }
        round_layout_change_manager.setOnClickListener {
            builderPlayerManager?.show()
        }
        buttonChangeManager.setOnClickListener {
            viewModel.loadSwitchAdmin(type ?: "", link ?: "", playerManager)
        }
        league_delete.setOnClickListener {
            if (ll_league_delete.isVisible) {
                ll_league_delete.visibility = View.GONE
            } else {
                ll_league_delete.visibility = View.VISIBLE
            }
        }
        buttonDeleteLeague.setOnClickListener {
            viewModel.loadDeleteGroup(type ?: "", link ?: "")
        }
    }

    private fun initDialogRounds() {
        val options = option.map {
            it.let {
                it.langNumWeek?.trim()
            }
        }.toTypedArray()

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter<CharSequence>(
            requireContext(), R.layout.item_check_list, options
        )

        var selectedItem = -1
        builder?.setTitle(getString(R.string.select_round))
        builder?.setSingleChoiceItems(
            adapter, -1
        ) { dialogInterface: DialogInterface, item: Int ->
            selectedItem = item
            roundEt.text = options[selectedItem]
            option[selectedItem].link?.let { link -> linkSub = link }
            dialogInterface.dismiss()
        }
        roundEt.text = options[options.size - 1]
        option[options.size - 1].link?.let { link -> linkSub = link }
        builder?.create()
    }

    private fun initDialogPlayerManagerRounds() {
        val options = optionPlayerManager.map {
            it.let {
                it.displayName?.trim()
            }
        }.toTypedArray()

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter<CharSequence>(
            requireContext(), R.layout.item_check_list, options
        )

        var selectedItem = -1
        builderPlayerManager?.setTitle(getString(R.string.select_round))
        builderPlayerManager?.setSingleChoiceItems(
            adapter, -1
        ) { dialogInterface: DialogInterface, item: Int ->
            selectedItem = item
            roundEtChangeManager.text = options[selectedItem]
            optionPlayerManager[selectedItem].userName?.let { userName ->
                playerManager = userName
            }
            dialogInterface.dismiss()
        }
        roundEtChangeManager.text = options[options.size - 1]
        optionPlayerManager[options.size - 1].userName?.let { userName ->
            playerManager = userName.toString()
        }
        builderPlayerManager?.create()
    }

    private fun initDialogPlayerDeleteRounds() {
        val options = optionPlayerDelete.map {
            it.let {
                it.displayName?.trim()
            }
        }.toTypedArray()

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter<CharSequence>(
            requireContext(), R.layout.item_check_list, options
        )

        var selectedItem = -1
        builderPlayerDelete?.setTitle(getString(R.string.select_round))
        builderPlayerDelete?.setSingleChoiceItems(
            adapter, -1
        ) { dialogInterface: DialogInterface, item: Int ->
            selectedItem = item
            roundEtDeletePlayer.text = options[selectedItem]
            optionPlayerDelete[selectedItem].userName?.let { userName ->
                playerDelete = userName
            }
            dialogInterface.dismiss()
        }
        roundEtDeletePlayer.text = options[options.size - 1]
        optionPlayerDelete[options.size - 1].userName?.let { userName ->
            playerDelete = userName
        }
        builderPlayerDelete?.create()
    }
}