package com.sakb.spl.ui.leagueorder

import android.content.DialogInterface
import android.os.Bundle
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

    var optionHomeAway = mutableListOf<StatusMatch>()
    var builderHomeAway: AlertDialog.Builder? = null

    var optionWinTieLoss = mutableListOf<StatusMatch>()
    var builderWinTieLoss: AlertDialog.Builder? = null


    var linkSub: String = ""
    var statusHomeAway = ""
    var statusWinTieLoss = ""
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

        initHomeAway()

        initWinTieLoss()

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

        binding.winLoseTv.setOnClickListener {
            builderWinTieLoss?.show()
        }

        binding.landTv.setOnClickListener {
            builderHomeAway?.show()
        }
    }

    private fun initHomeAway() {
        val homeAwayList = ArrayList<StatusMatch>()
        homeAwayList.add(StatusMatch(name = getString(R.string.all_list), status = ""))
        homeAwayList.add(StatusMatch(name = getString(R.string.homeList), status = HOME))
        homeAwayList.add(StatusMatch(name = getString(R.string.awayList), status = AWAY))

        optionHomeAway = homeAwayList
        builderHomeAway = AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
        initDialogRoundsHomeAway()
    }

    private fun initWinTieLoss() {
        val winTieLossList = ArrayList<StatusMatch>()
        winTieLossList.add(StatusMatch(name = getString(R.string.all_list), status = ""))
        winTieLossList.add(StatusMatch(name = getString(R.string.win), status = WIN))
        winTieLossList.add(StatusMatch(name = getString(R.string.tie), status = TIE))
        winTieLossList.add(StatusMatch(name = getString(R.string.loss), status = LOSS))

        optionWinTieLoss = winTieLossList
        builderWinTieLoss = AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
        initDialogRoundsWinTieLoss()
    }

    private fun initRecyclerView() {
        leagueOrderAdapter = LeagueRankingAdapter(requireContext())
        binding.rvClub.adapter = leagueOrderAdapter
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

        var selectedItem: Int
        builder?.setTitle(getString(R.string.select_round))
        builder?.setSingleChoiceItems(
            adapter, -1
        ) { dialogInterface: DialogInterface, item: Int ->
            selectedItem = item
            binding.viewByTourTv.text = options[selectedItem]
            option[selectedItem].link?.let { link ->
                linkSub = link
                callRankingBySubeldawry(link)
            }
            dialogInterface.dismiss()
        }
        builder?.create()
    }

    private fun callRankingBySubeldawry(link: String) {
        viewModel.rankingTeamLoad(link)
    }

    private fun initDialogRoundsHomeAway() {
        val options = optionHomeAway.map {
            it.name
        }.toTypedArray()

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter<CharSequence>(
            requireContext(), R.layout.item_check_list, options
        )

        var selectedItem: Int
        builderHomeAway?.setTitle(getString(R.string.select_round))
        builderHomeAway?.setSingleChoiceItems(
            adapter, -1
        ) { dialogInterface: DialogInterface, item: Int ->
            selectedItem = item
            binding.landTv.text = options[selectedItem]
            statusHomeAway = optionHomeAway[selectedItem].status
            leagueOrderAdapter.changeStatus(statusHomeAway, statusWinTieLoss)
            dialogInterface.dismiss()
        }
        builderHomeAway?.create()
    }

    private fun initDialogRoundsWinTieLoss() {
        val options = optionWinTieLoss.map {
            it.name
        }.toTypedArray()

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter<CharSequence>(
            requireContext(), R.layout.item_check_list, options
        )

        var selectedItem: Int
        builderWinTieLoss?.setTitle(getString(R.string.select_round))
        builderWinTieLoss?.setSingleChoiceItems(
            adapter, -1
        ) { dialogInterface: DialogInterface, item: Int ->
            selectedItem = item
            binding.winLoseTv.text = options[selectedItem]
            statusWinTieLoss = optionWinTieLoss[selectedItem].status
            leagueOrderAdapter.changeStatus(statusHomeAway, statusWinTieLoss)
            dialogInterface.dismiss()
        }
        builderWinTieLoss?.create()
    }

    companion object {
        const val HOME = "home"
        const val AWAY = "away"

        const val WIN = "w"
        const val TIE = "d"
        const val LOSS = "l"
    }
}

data class StatusMatch(var name: String, var status: String)