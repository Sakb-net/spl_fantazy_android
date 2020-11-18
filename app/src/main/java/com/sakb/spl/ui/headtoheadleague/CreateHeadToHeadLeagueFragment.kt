package com.sakb.spl.ui.headtoheadleague

import android.app.AlertDialog
import android.content.*
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.DataCreateLeague
import com.sakb.spl.data.model.DataItemSub
import com.sakb.spl.databinding.CreateHeadToHeadLeagueFragmentBinding
import com.sakb.spl.databinding.NewLeagueCreatedDialogBinding
import com.sakb.spl.ui.league.LeagueFragment.Companion.HEAD_TO_HEAD
import com.sakb.spl.ui.myleague.MyLeagueFragment
import com.sakb.spl.utils.showConfirmationDialog
import com.sakb.spl.utils.showSuccessDialog
import com.sakb.spl.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateHeadToHeadLeagueFragment : BaseFragment() {
    override val viewModel by viewModel<CreateHeadToHeadLeagueViewModel>()
    private lateinit var binding: CreateHeadToHeadLeagueFragmentBinding

    var option = mutableListOf<DataItemSub>()
    var builder: androidx.appcompat.app.AlertDialog.Builder? = null

    var linkSub: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.create_head_to_head_league_fragment,
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

        binding.maxTeamsLayout.setOnClickListener {
            dialogMaxTeams()
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
                        HEAD_TO_HEAD
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
            builder = androidx.appcompat.app.AlertDialog.Builder(requireContext(),
                R.style.MaterialThemeDialog)
            initDialogRounds()
        })
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
            binding.roundEt.text = options[selectedItem]
            option[selectedItem].link?.let { link -> linkSub = link }
            dialogInterface.dismiss()
        }
        binding.roundEt.text = options[options.size - 1]
        option[options.size - 1].link?.let { link -> linkSub = link }
        builder?.create()
    }

    private fun openConfirmationDialog() {
        context?.showConfirmationDialog(
            R.drawable.ic_done,
            "تم الانضمام بنجاح لدوري \" المحترفين\""
        ) { dialog ->
            dialog?.dismiss()
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
        val builder = AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
        builder.setTitle(getString(R.string.select_round))
        builder.setSingleChoiceItems(
            options, 0
        ) { _: DialogInterface, item: Int ->
            selectedItem = item
        }
        builder.setPositiveButton(R.string.okkk) { dialogInterface: DialogInterface, _: Int ->
            binding.roundEt.text = options[selectedItem]
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
        val builder = AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
        builder.setTitle(getString(R.string.max_team_num))
        builder.setSingleChoiceItems(
            options, 0
        ) { _: DialogInterface, item: Int ->
            selectedItem = item
        }
        builder.setPositiveButton(R.string.okkk) { dialogInterface: DialogInterface, _: Int ->
            binding.maxTeamsEdit.text = options[selectedItem]
            ///todo  viewModel.selectedTeamPosition = selectedItem
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(R.string.cancell) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder.create()
        builder.show()
    }

    private fun Context.joinNowDialog(
        @DrawableRes drawableRes: Int,
        title: String,
        contentResID: Int? = null,
        price: String? = null,
        code: String? = null,
        positive: (dialog: AlertDialog?) -> Unit,
        negative: (dialog: AlertDialog?) -> Unit
    ) {
        val binding = NewLeagueCreatedDialogBinding.inflate(LayoutInflater.from(this), null, false)
        val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(false)
        val dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding.dialogImage.setImageResource(drawableRes)
        binding.dialogTextTitle.text = title
        binding.dialogTextContent.text = contentResID?.let { this.getString(it) }
        binding.dialogTextPrice.text = contentResID?.let { price }
        binding.codeText.text = contentResID?.let { code }
        binding.copyBtn.setOnClickListener {
            copyToClipboard(binding.codeText.text)
        }
        binding.shareBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, binding.codeText.text.toString())
            }
            startActivity(Intent.createChooser(intent, "Share via"))
        }


        binding.positiveButton.setOnClickListener { positive(dialog) }
        binding.negativeBtn.setOnClickListener { negative(dialog) }
        dialog.show()
    }

    fun Context.copyToClipboard(text: CharSequence) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        toast("Copied")
    }


    private fun openConfirmationDialog(data: DataCreateLeague) {
        context?.showSuccessDialog(
            data,
            copy = { dialog, code ->
                val sdk = Build.VERSION.SDK_INT
                if (sdk < Build.VERSION_CODES.HONEYCOMB) {
                    val clipboard =
                        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager?
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
                    MyLeagueFragment.LINK_TYPE to HEAD_TO_HEAD)
                findNavController().navigate(R.id.action_createHeadToHeadLeagueFragment_to_ManagementLeagueFragment,
                    bundle)
                dialog?.dismiss()
            },
            myLeague = { dialog ->
                findNavController().navigate(R.id.action_createHeadToHeadLeagueFragment_to_MyLeagueFragment)
                dialog?.dismiss()
            }
        )
    }
}
