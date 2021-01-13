package com.sakb.spl.ui.standingheadtohead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.DataItemSubGroup
import com.sakb.spl.databinding.StandingHeadToHeadFragmentBinding
import com.sakb.spl.ui.league.LeagueFragment.Companion.HEAD_TO_HEAD
import com.sakb.spl.ui.myleague.MyLeagueFragment.Companion.LINK_LEAGUE
import com.sakb.spl.ui.myleague.MyLeagueFragment.Companion.LINK_TYPE
import com.sakb.spl.ui.standingheadtohead.adapter.StandingHeadToHeadAdapter
import com.sakb.spl.ui.standingheadtohead.adapter.StandingHeadToHeadSpinnerAdapter
import com.sakb.spl.ui.standingheadtohead.adapter.StandingMatchesHeadToHeadAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingHeadToHeadFragment : BaseFragment() {
    override val viewModel by viewModel<StandingHeadToHeadViewModel>()
    private lateinit var binding: StandingHeadToHeadFragmentBinding

    val link: String? by lazy {
        arguments?.getString(LINK_LEAGUE, "")
    }

    val type: String? by lazy {
        arguments?.getString(LINK_TYPE, HEAD_TO_HEAD)
    }
    lateinit var standingHeadToHeadAdapter: StandingHeadToHeadAdapter
    lateinit var standingMatchesHeadToHeadAdapter: StandingMatchesHeadToHeadAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = StandingHeadToHeadFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(STANDING)

        binding.buttonLeague.setOnClickListener {
            initView(STANDING)
        }
        binding.buttonMatch.setOnClickListener {
            initView(MATCHES)
        }
    }

    private fun initView(status: String) {
        when (status) {
            STANDING -> {
                binding.buttonLeague.setTextColor(resources.getColor(R.color.white))
                binding.buttonMatch.setTextColor(resources.getColor(R.color.colorBlueContent))
                binding.buttonLeague.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_rec_green)
                binding.buttonMatch.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_rec_gray)
                binding.leagueHeadTitle.visibility = View.VISIBLE
                binding.matchHeadTitle.visibility = View.INVISIBLE
                binding.rvParent.visibility = View.VISIBLE
                binding.rvMatches.visibility = View.INVISIBLE
            }
            MATCHES -> {
                binding.buttonLeague.setTextColor(resources.getColor(R.color.colorBlueContent))
                binding.buttonMatch.setTextColor(resources.getColor(R.color.white))
                binding.buttonLeague.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_rec_gray)
                binding.buttonMatch.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_rec_green)
                binding.leagueHeadTitle.visibility = View.INVISIBLE
                binding.matchHeadTitle.visibility = View.VISIBLE
                binding.rvParent.visibility = View.INVISIBLE
                binding.rvMatches.visibility = View.VISIBLE
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadSubeldawry(type_league = type ?: "", link_league = link ?: "")
        viewModel.loadStanding(type_league = type ?: "", link_league = link ?: "")

        viewModel.standingResponse.observe(viewLifecycleOwner, {
            it?.data?.let { dataStanding ->
                binding.leagueHeader.text = dataStanding.groupEldwry?.name
                dataStanding.usersGroup?.let {
                    standingHeadToHeadAdapter =
                        StandingHeadToHeadAdapter(dataStanding.usersGroup)
                    binding.rvParent.adapter = standingHeadToHeadAdapter
                }

                dataStanding.matchGroupList?.let { list ->
                    binding.NumWeek.text = list[0]?.langNumWeek
                    standingMatchesHeadToHeadAdapter =
                        StandingMatchesHeadToHeadAdapter(dataStanding.matchGroupList)
                    binding.rvMatches.adapter = standingMatchesHeadToHeadAdapter
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
            StandingHeadToHeadSpinnerAdapter(
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

    companion object {
        const val STANDING = "standing"
        const val MATCHES = "matches"
    }
}