package com.sakb.spl.ui.matches

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.DataItemSub
import com.sakb.spl.data.model.DataItemSubFix
import com.sakb.spl.databinding.MatchesFragmentBinding
import com.sakb.spl.ui.matches.MatchDetailsFragment.Companion.MATCH_DETAILS_OBJECT
import com.sakb.spl.ui.matches.adapters.MatchesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class MatchesFragment : BaseFragment() {

    private lateinit var binding: MatchesFragmentBinding
    override val viewModel by viewModel<MatchesViewModel>()
    private val matchesAdapter by lazy { MatchesAdapter() }

    var option = mutableListOf<DataItemSub>()
    var builder: AlertDialog.Builder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.matches_fragment, container, false)
        return binding.root
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
        initRecyclerView()
        binding.spinnerView.setOnClickListener {
            builder?.show()
        }
        // fake data
        viewModel.allFixturesBySubeldawry.observe(viewLifecycleOwner, Observer {
            var list = mutableListOf<DataItemSubFix>()
            it?.data?.let { dataItem ->
                list = dataItem.filterNotNull() as MutableList<DataItemSubFix>
            }
            matchesAdapter.submitList(list)
            matchesAdapter.onPervMatchListener ={
                var bundle = bundleOf(MATCH_DETAILS_OBJECT to it)
                findNavController().navigate(R.id.action_matchesFragment_to_matchDetailsFragment,bundle)
            }
        })


    }

    private fun initRecyclerView() {
        val mergeAdapter = ConcatAdapter(matchesAdapter)
        binding.matchesRecycler.itemAnimator = null
        binding.matchesRecycler.adapter = mergeAdapter
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
        ) { _: DialogInterface, item: Int ->
            selectedItem = item
        }

        binding.roundTitleTv.text = options[options.size - 1]
        option[options.size - 1].link?.let { link-> callFixturesBySubeldawry(link) }

        builder?.setPositiveButton(R.string.okkk) { dialogInterface: DialogInterface, _: Int ->
            binding.roundTitleTv.text = options[selectedItem]
            option[selectedItem].link?.let { link-> callFixturesBySubeldawry(link) }
            dialogInterface.dismiss()
        }
        builder?.setNegativeButton(R.string.cancell) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder?.create()
    }

    private fun callFixturesBySubeldawry(link: String) {
        viewModel.loadAllFixturesBySubelawry(link)
    }
}
