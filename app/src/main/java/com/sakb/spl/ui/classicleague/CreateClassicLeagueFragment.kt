package com.sakb.spl.ui.classicleague

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.DataItemSub
import com.sakb.spl.databinding.CreateClassicLeagueFragmentBinding
import com.sakb.spl.utils.showConfirmationDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateClassicLeagueFragment : BaseFragment() {

    private lateinit var binding: CreateClassicLeagueFragmentBinding
    override val viewModel by viewModel<CreateClassicLeagueViewModel>()

    var option = mutableListOf<DataItemSub>()
    var builder: AlertDialog.Builder? = null

    var linkSub: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.create_classic_league_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.roundLayout.setOnClickListener {
            builder?.show()
        }

        binding.buttonSend.setOnClickListener {
            if (binding.nameEt.text?.toString()?.trim().isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.all_fileds_must_filled),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                binding.nameEt.text?.toString()?.trim()?.let { name ->
                    viewModel.loadCreateLeague(
                        linkSub,
                        name
                    )
                }
            }
        }

        viewModel.createLeagueResponse.observe(viewLifecycleOwner, {
            it.data?.let {
                openConfirmationDialog()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadAllSubeldawry()
        viewModel.allSubeldawry.observe(viewLifecycleOwner, Observer {
            it?.data?.let { list ->
                option = list.filterNotNull() as MutableList<DataItemSub>
            }
            builder = AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
            initDialogRounds()
        })
    }

    private fun openConfirmationDialog() {
        context?.showConfirmationDialog(
            R.drawable.ic_done,
            getString(R.string.classic_league_dialoug)
        ) { dialog ->
            dialog?.dismiss()
        }
    }

    private fun initDialogRounds() {
        // todo   val options = viewModel.teamsNames.toTypedArray()
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
            binding.roundEt.text = options[selectedItem]
            option[selectedItem].link?.let { link -> linkSub = link }
            dialogInterface.dismiss()
        }
        binding.roundEt.text = options[options.size - 1]
        option[options.size - 1].link?.let { link -> linkSub = link }
        builder?.create()
    }


}
