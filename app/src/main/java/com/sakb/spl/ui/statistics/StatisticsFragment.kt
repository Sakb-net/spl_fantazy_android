package com.sakb.spl.ui.statistics


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.AllDataItem
import com.sakb.spl.data.model.GetTeamResponse
import com.sakb.spl.databinding.FragmentStatisitcsBinding
import com.sakb.spl.utils.DividerItemDecoration
import com.sakb.spl.utils.FixedGridLayoutManager
import com.sakb.spl.utils.SpinnerHelperAdapter
import kotlinx.android.synthetic.main.fragment_statisitcs.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class StatisticsFragment : BaseFragment() {
    private lateinit var binding: FragmentStatisitcsBinding

    override val viewModel by viewModel<StatisticsViewModel>()

    internal var scrollX = 0

    internal var playerList: MutableList<AllDataItem> = ArrayList()

    lateinit var clubAdapter: ClubAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statisitcs, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //  changeViewsFonts()
        viewModel.loadStatistics()
        viewModel.statisticsPlayer.observe(viewLifecycleOwner, Observer {
            it?.data?.let { data ->
                data.allData?.let { list ->
                    playerList = list.filterNotNull() as MutableList<AllDataItem>
                }
            }
            setUpRecyclerView()
        })
        viewModel.loadTeams()
        viewModel.team.observe(viewLifecycleOwner, Observer {
            initTeamsSpinner(it)
        })
        initSpinnerOrder()
        initSpinnerPosition()
    }

    /**
     * Handles RecyclerView for the action
     */

    private fun setUpRecyclerView() {
        clubAdapter = ClubAdapter(context, playerList)

        val manager = FixedGridLayoutManager()
        manager.setTotalColumnCount(1)
        rvClub.layoutManager = manager
        rvClub.adapter = clubAdapter
        rvClub.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun initTeamsSpinner(teamResponse: GetTeamResponse) {


        val teams = teamResponse.data
        teams?.add(GetTeamResponse.Data("", "___end__"))

        val adapter =
            teams?.let {
                TeamNewSpinnerAdapter(
                    this,
                    it, android.R.layout.simple_spinner_dropdown_item
                )
            }
        adapter?.setDropDownViewResource(R.layout.item_edited)
        binding.teamsSpinner.adapter = adapter
        adapter?.count?.let { binding.teamsSpinner.setSelection(it) }
        binding.teamsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    if (viewModel.recreatedTeam) {
                        return
                    }
                    if (position != (teams?.size?.minus(1))) {
                        binding.allTeamsTv.text = teams?.get(position)?.name
                        viewModel.selectedTeamLink = teams?.get(position)?.link
                        viewModel.loadStatistics(
                            "" + viewModel.selectedTeamLink,
                            "" + viewModel.orderPlay,
                            "" + viewModel.locPlayer
                        )

                    } else {
//                        viewModel.selectedTeamLink = ""
//                        viewModel.loadStatistics(
//                            "" + viewModel.selectedTeamLink,
//                            "" + viewModel.orderPlay,
//                            "" + viewModel.locPlayer
//                        )
                    }
                }

            }

        binding.allTeamsTv.setOnClickListener {
            viewModel.recreatedTeam = false
            adapter?.count?.let { no -> binding.teamsSpinner.setSelection(no) }
            binding.teamsSpinner.performClick()
        }

    }

    private fun initSpinnerOrder() {
        val mutableList = mutableListOf<String>()
        //heigh_price,low_price,heighest_point,lowest_point
        mutableList.add(getString(R.string.lowest_point))
        mutableList.add(getString(R.string.heighest_point))
        mutableList.add(getString(R.string.low_price))
        mutableList.add(getString(R.string.heigh_price))
        mutableList.add("___end__")
        val data = mutableList
        Timber.e("init spinner")
        //  Timber.e("init spinner " +data?.size )
        val adapter =
            SpinnerHelperAdapter(
                this.requireContext(),
                data,
                android.R.layout.simple_spinner_dropdown_item
            )
        adapter.setDropDownViewResource(R.layout.item_edited)
        binding.optionsSpinner.adapter = adapter

        // show hint
        //  Timber.e("init spinner " +adapter.count )
        binding.optionsSpinner.setSelection(adapter.count)

        binding.optionsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Timber.e("xddddd pos " + position)
                    if (viewModel.recreatedOrder) {
                        return
                    }
                    if (position != (data.size - 1)) {
                        binding.sortByTv.text = data[position]

                        when (data[position]) {
                            getString(R.string.heigh_price) -> {
                                viewModel.orderPlay = "heigh_price"
                                viewModel.loadStatistics(
                                    "" + viewModel.selectedTeamLink,
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.locPlayer
                                )
                            }
                            getString(R.string.low_price) -> {
                                viewModel.orderPlay = "low_price"
                                viewModel.loadStatistics(
                                    "" + viewModel.selectedTeamLink,
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.locPlayer
                                )
                            }
                            getString(R.string.heighest_point) -> {
                                viewModel.orderPlay = "heighest_point"
                                viewModel.loadStatistics(
                                    "" + viewModel.selectedTeamLink,
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.locPlayer
                                )
                            }
                            getString(R.string.lowest_point) -> {
                                viewModel.orderPlay = "lowest_point"
                                viewModel.loadStatistics(
                                    "" + viewModel.selectedTeamLink,
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.locPlayer
                                )
                            }
                        }
                    } else {
//                        viewModel.orderPlay = ""
//                        viewModel.loadStatistics(
//                            "" + viewModel.selectedTeamLink,
//                            "" + viewModel.orderPlay,
//                            "" + viewModel.locPlayer
//                        )
                    }
                }

            }

        binding.sortByTv.setOnClickListener {
            viewModel.recreatedOrder = false
            binding.optionsSpinner.setSelection(adapter.count)
            binding.optionsSpinner.performClick()
        }
    }

    private fun initSpinnerPosition() {
        val mutableList = mutableListOf<String>()
        // goalkeeper,defender,line,attacker
        mutableList.add(getString(R.string.goalkeeper))
        mutableList.add(getString(R.string.defender))
        mutableList.add(getString(R.string.line))
        mutableList.add(getString(R.string.attacker))
        mutableList.add("___end__")
        val data = mutableList
        val adapter =
            SpinnerHelperAdapter(
                this.requireContext(),
                data,
                android.R.layout.simple_spinner_dropdown_item
            )
        adapter.setDropDownViewResource(R.layout.item_edited)
        binding.playerSpinner.adapter = adapter

        binding.playerSpinner.setSelection(adapter.count)

        binding.playerSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Timber.e("xddddd pos " + position)
                    if (viewModel.recreatedLocation) {
                        return
                    }
                    if (position != (data.size - 1)) {
                        binding.allPlayersTv.text = data[position]
                        when (data[position]) {
                            getString(R.string.goalkeeper) -> {
                                viewModel.locPlayer = "goalkeeper"
                                viewModel.loadStatistics(
                                    "" + viewModel.selectedTeamLink,
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.locPlayer
                                )
                            }
                            getString(R.string.defender) -> {
                                viewModel.locPlayer = "defender"
                                viewModel.loadStatistics(
                                    "" + viewModel.selectedTeamLink,
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.locPlayer
                                )
                            }
                            getString(R.string.line) -> {
                                viewModel.locPlayer = "line"
                                viewModel.loadStatistics(
                                    "" + viewModel.selectedTeamLink,
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.locPlayer
                                )
                            }
                            getString(R.string.attacker) -> {
                                viewModel.locPlayer = "attacker"
                                viewModel.loadStatistics(
                                    "" + viewModel.selectedTeamLink,
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.locPlayer
                                )
                            }
                        }

                    } else {
//                        viewModel.locPlayer = ""
//                        viewModel.loadStatistics(
//                            "" + viewModel.selectedTeamLink,
//                            "" + viewModel.orderPlay,
//                            "" + viewModel.locPlayer
//                        )
                    }
                }
            }

        binding.allPlayersTv.setOnClickListener {
            viewModel.recreatedLocation = false
            binding.playerSpinner.setSelection(adapter.count)
            binding.playerSpinner.performClick()
        }
    }

}
