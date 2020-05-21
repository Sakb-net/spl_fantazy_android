package com.sakb.spl.ui.matches

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.MergeAdapter
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.databinding.MatchesFragmentBinding
import com.sakb.spl.ui.matches.adapters.MatchesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchesFragment : BaseFragment() {

    private lateinit var binding : MatchesFragmentBinding
    override val viewModel by viewModel<MatchesViewModel>()
    private val matchesAdapter by lazy { MatchesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate(inflater, R.layout.matches_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        binding.spinnerView.setOnClickListener {
            dialogRounds()
        }
        // fake data
        val list = mutableListOf<HomeResponse.MatchGroup>()
        list.add(HomeResponse.MatchGroup("2020-05-27",nameFirst = "الاهلي",nameSecond = "الاتحاد"))
        list.add(HomeResponse.MatchGroup("2020-05-27",nameFirst = "الاهلي",nameSecond = "الاتحاد"))
        list.add(HomeResponse.MatchGroup("2020-05-27",nameFirst = "الاهلي",nameSecond = "الاتحاد"))
        list.add(HomeResponse.MatchGroup("2020-05-27",nameFirst = "الاهلي",nameSecond = "الاتحاد"))
        list.add(HomeResponse.MatchGroup("2020-05-27",nameFirst = "الاهلي",nameSecond = "الاتحاد"))
        list.add(HomeResponse.MatchGroup("2020-03-27",nameFirst = "الاهلي",nameSecond = "الاتحاد"))

        matchesAdapter.submitList(list)
    }

    private fun initRecyclerView() {
        val mergeAdapter = MergeAdapter(matchesAdapter)
        binding.matchesRecycler.itemAnimator = null
        binding.matchesRecycler.adapter = mergeAdapter
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
            binding.roundTitleTv.text = options[selectedItem]
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
