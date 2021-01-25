package com.sakb.spl.ui.leagueorder

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.DataItemSub
import com.sakb.spl.data.model.RankingEldwryItem
import com.sakb.spl.databinding.LeagueOrderFragmentBinding
import com.sakb.spl.ui.leagueorder.adapters.LeagueRankingAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeagueOrderFragment : BaseFragment() {
    private lateinit var binding: LeagueOrderFragmentBinding
    override val viewModel by viewModel<LeagueOrderViewModel>()
    lateinit var leagueOrderAdapter: LeagueRankingAdapter

    var option = mutableListOf<DataItemSub>()
    var builder: AlertDialog.Builder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.league_order_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        viewModel.rankingTeamLoad("")
        viewModel.subEldawryLoad()

        viewModel.rankingTeams.observe(viewLifecycleOwner) {

            var list = mutableListOf<RankingEldwryItem>()
            it?.data?.rankingEldwry?.let { dataItem ->
                list = dataItem.filterNotNull() as MutableList<RankingEldwryItem>
            }

            leagueOrderAdapter.submitList(list)
            leagueOrderAdapter.onClickListener = { rank, pos ->
                leagueOrderAdapter.expand = !leagueOrderAdapter.expand
                leagueOrderAdapter.pos = pos
                leagueOrderAdapter.notifyDataSetChanged()
            }

        }

        viewModel.subEldawry.observe(viewLifecycleOwner) {
            it?.data?.let { list ->
                option = list.filterNotNull() as MutableList<DataItemSub>
            }
            builder = AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
            initDialogRounds()
        }

        binding.viewByTourTv.setOnClickListener {
            builder?.show()
        }
    }

    private fun initRecyclerView() {
        leagueOrderAdapter = LeagueRankingAdapter(requireContext())
        binding.rvClub.adapter = leagueOrderAdapter
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

        var selectedItem: Int
        builder?.setTitle(getString(R.string.select_round))
        builder?.setSingleChoiceItems(
            adapter, -1
        ) { dialogInterface: DialogInterface, item: Int ->
            selectedItem = -1
            binding.viewByTourTv.text = options[selectedItem]
            option[selectedItem].link?.let { link -> callRankingBySubeldawry(link) }
            dialogInterface.dismiss()
        }

//        binding.viewByTourTv.text = options[options.size - 1]
//        option[options.size - 1].link?.let { link -> callRankingBySubeldawry(link) }
        builder?.create()
    }

    private fun callRankingBySubeldawry(link: String) {
        viewModel.rankingTeamLoad(link)
    }
}