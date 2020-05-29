package com.sakb.spl.ui.headtoheadleague

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.CreateHeadToHeadLeagueFragmentBinding
import com.sakb.spl.utils.showConfirmationDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateHeadToHeadLeagueFragment : BaseFragment() {
    override val viewModel by viewModel<CreateHeadToHeadLeagueViewModel>()
    private lateinit var binding : CreateHeadToHeadLeagueFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate(inflater, R.layout.create_head_to_head_league_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.roundLayout.setOnClickListener {
            dialogRounds()
        }

        binding.maxTeamsLayout.setOnClickListener {
            dialogMaxTeams()
        }

        binding.buttonSend.setOnClickListener {
            openConfirmationDialog()
        }
    }

    private fun openConfirmationDialog() {
        context?.showConfirmationDialog(R.drawable.ic_done, "تم الانضمام بنجاح لدوري \" المحترفين\""){
                dialog ->  dialog?.dismiss()
        }
    }

    private fun dialogRounds() {
        // todo   val options = viewModel.teamsNames.toTypedArray()
        val option = mutableListOf<String>()
        option.add("الجولة 1 ")
        option.add("الجولة 2 ")
        option.add("الجولة 3 ")
        val options = option.toTypedArray()

        var selectedItem = 0
        val builder = AlertDialog.Builder(requireContext(),R.style.MaterialThemeDialog)
        builder.setTitle(getString(R.string.select_round))
        builder.setSingleChoiceItems(options
            , 0
        ) { _: DialogInterface, item: Int ->
            selectedItem = item
        }
        builder.setPositiveButton(R.string.okkk) { dialogInterface: DialogInterface, _: Int ->
            binding.roundEt.setText(options[selectedItem])
            ///todo  viewModel.selectedTeamPosition = selectedItem
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(R.string.cancell) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder.create()
        builder.show()
    }

    private fun dialogMaxTeams() {
        // todo   val options = viewModel.teamsNames.toTypedArray()
        val option = mutableListOf<String>()
        option.add(" غير محدد ")
        option.add(" 12 ")
        option.add(" 13 ")
        val options = option.toTypedArray()

        var selectedItem = 0
        val builder = AlertDialog.Builder(requireContext(),R.style.MaterialThemeDialog)
        builder.setTitle(getString(R.string.max_team_num))
        builder.setSingleChoiceItems(options
            , 0
        ) { _: DialogInterface, item: Int ->
            selectedItem = item
        }
        builder.setPositiveButton(R.string.okkk) { dialogInterface: DialogInterface, _: Int ->
            binding.maxTeamsEdit.setText(options[selectedItem])
            ///todo  viewModel.selectedTeamPosition = selectedItem
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(R.string.cancell) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder.create()
        builder.show()
    }


}
