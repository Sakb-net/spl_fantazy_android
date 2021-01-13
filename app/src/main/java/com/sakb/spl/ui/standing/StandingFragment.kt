package com.sakb.spl.ui.standing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.DataItemSubGroup
import com.sakb.spl.databinding.StandingFragmentBinding
import com.sakb.spl.ui.league.LeagueFragment.Companion.CLASSIC
import com.sakb.spl.ui.myleague.MyLeagueFragment.Companion.LINK_LEAGUE
import com.sakb.spl.ui.myleague.MyLeagueFragment.Companion.LINK_TYPE
import com.sakb.spl.ui.standing.adapter.StandingAdapter
import com.sakb.spl.ui.standing.adapter.StandingSpinnerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingFragment : BaseFragment() {
    override val viewModel by viewModel<StandingViewModel>()
    private lateinit var binding: StandingFragmentBinding
    val link: String? by lazy {
        arguments?.getString(LINK_LEAGUE, "")
    }

    val type: String? by lazy {
        arguments?.getString(LINK_TYPE, CLASSIC)
    }
    lateinit var standingAdapter: StandingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = StandingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadSubeldawry(type_league = type ?: "", link_league = link ?: "")
        viewModel.loadStanding(type_league = type ?: "", link_league = link ?: "")

        viewModel.standingResponse.observe(viewLifecycleOwner, {
            it?.data?.let { dataStanding ->
                binding.leagueHeader.text = dataStanding.groupEldwry?.name
                dataStanding.usersGroup?.let {
                    standingAdapter = StandingAdapter(dataStanding.usersGroup)
                    binding.rvParent.adapter = standingAdapter
                }
            }

        })

        viewModel.groupSubEldawryResponse.observe(viewLifecycleOwner, {
            it?.data?.let { list ->
                initSubEldawry(list as ArrayList<DataItemSubGroup?>)
            }
        })
    }


    private fun initSubEldawry(leaguesResponse: ArrayList<DataItemSubGroup?>) {
        val leagues: MutableList<DataItemSubGroup?> = leaguesResponse
        leagues.add(DataItemSubGroup("",
            "",
            getString(R.string.all_weeks),
            "",
            getString(R.string.all_weeks),
            "",
            "",
            ""))
        leagues.add(DataItemSubGroup("",
            "",
            getString(R.string.all_weeks),
            "",
            getString(R.string.all_weeks),
            "",
            "",
            ""))

        val adapter =
            StandingSpinnerAdapter(
                this,
                leagues, android.R.layout.simple_spinner_dropdown_item
            )

        adapter.setDropDownViewResource(R.layout.item_edited)
        binding.leagueSpinner.adapter = adapter
        adapter.count.let { binding.leagueSpinner.setSelection(it) }
        binding.leagueSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {

                    if (viewModel.recreatedStanding) {
                        return
                    }
                    if (position != (leagues.size.minus(1))) {
                        binding.allLeaguesTv.text = leagues[position]?.langNumWeek
                        viewModel.selectedLink = leagues[position]?.link
                        viewModel.loadStanding(
                            type ?: "",
                            link ?: "",
                            "" + viewModel.selectedLink
                        )
                    } else {
//
                    }
                }

            }

        binding.allLeaguesTv.setOnClickListener {
            viewModel.recreatedStanding = false
            adapter.count.let { no -> binding.leagueSpinner.setSelection(no) }
            binding.leagueSpinner.performClick()
        }

    }
}