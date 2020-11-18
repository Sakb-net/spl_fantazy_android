package com.sakb.spl.ui.classicleague

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
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.DataCreateLeague
import com.sakb.spl.data.model.DataItemSub
import com.sakb.spl.databinding.CreateClassicLeagueFragmentBinding
import com.sakb.spl.ui.league.LeagueFragment.Companion.CLASSIC
import com.sakb.spl.ui.myleague.MyLeagueFragment
import com.sakb.spl.utils.showSuccessDialog
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
                        name,
                        CLASSIC
                    )
                }
            }
        }

        viewModel.createLeagueResponse.observe(viewLifecycleOwner, {
            it.data?.let { data ->
                openConfirmationDialog(data)
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

    private fun openConfirmationDialog(data: DataCreateLeague) {
        context?.showSuccessDialog(
            data,
            copy = { dialog, code ->
                val sdk = Build.VERSION.SDK_INT
                if (sdk < Build.VERSION_CODES.HONEYCOMB) {
                    val clipboard =
                        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                    clipboard?.text = code
                } else {
                    val clipboard =
                        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager?
                    val clip = ClipData.newPlainText(getString(R.string.code_league), code)
                    clipboard?.setPrimaryClip(clip)
                }
            },
            share = { dialog, url ->
//                val sendIntent: Intent = Intent().apply {
//                    action = Intent.ACTION_SEND
//                    putExtra(Intent.EXTRA_TEXT, url)
//                    type = "text/plain"
//                }
//                val shareIntent = Intent.createChooser(sendIntent, null)
//                requireContext().startActivity(shareIntent)
            },
            manage = { dialog, url ->
                val bundle = bundleOf(MyLeagueFragment.LINK_LEAGUE to url,
                    MyLeagueFragment.LINK_TYPE to CLASSIC)
                findNavController().navigate(R.id.action_createClassicLeagueFragment_to_ManagementLeagueFragment,
                    bundle)
                dialog?.dismiss()
            },
            myLeague = { dialog ->
                findNavController().navigate(R.id.action_createClassicLeagueFragment_to_MyLeagueFragment)
                dialog?.dismiss()
            }
        )
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
