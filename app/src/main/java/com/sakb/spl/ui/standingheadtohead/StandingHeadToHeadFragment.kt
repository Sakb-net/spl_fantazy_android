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
import com.sakb.spl.ui.league.LeagueFragment.Companion.HEAD_TO_HEAD
import com.sakb.spl.ui.myleague.MyLeagueFragment.Companion.LINK_LEAGUE
import com.sakb.spl.ui.myleague.MyLeagueFragment.Companion.LINK_TYPE
import com.sakb.spl.ui.standingheadtohead.adapter.StandingHeadToHeadAdapter
import com.sakb.spl.ui.standingheadtohead.adapter.StandingHeadToHeadSpinnerAdapter
import com.sakb.spl.ui.standingheadtohead.adapter.StandingMatchesHeadToHeadAdapter
import kotlinx.android.synthetic.main.standing_head_to_head_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingHeadToHeadFragment : BaseFragment() {
    override val viewModel by viewModel<StandingHeadToHeadViewModel>()

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
        return inflater.inflate(R.layout.standing_head_to_head_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(STANDING)

        buttonLeague.setOnClickListener {
            initView(STANDING)
        }
        buttonMatch.setOnClickListener {
            initView(MATCHES)
        }
    }

    private fun initView(status: String) {
        when(status){
            STANDING->{
                buttonLeague.setTextColor(resources.getColor(R.color.white))
                buttonMatch.setTextColor(resources.getColor(R.color.colorBlueContent))
                buttonLeague.background = ContextCompat.getDrawable(requireContext(),R.drawable.bg_rec_green)
                buttonMatch.background = ContextCompat.getDrawable(requireContext(),R.drawable.bg_rec_gray)
                league_head_title.visibility = View.VISIBLE
                match_head_title.visibility = View.INVISIBLE
                rv_parent.visibility = View.VISIBLE
                rv_matches.visibility = View.INVISIBLE
            }
            MATCHES->{
                buttonLeague.setTextColor(resources.getColor(R.color.colorBlueContent))
                buttonMatch.setTextColor(resources.getColor(R.color.white))
                buttonLeague.background = ContextCompat.getDrawable(requireContext(),R.drawable.bg_rec_gray)
                buttonMatch.background = ContextCompat.getDrawable(requireContext(),R.drawable.bg_rec_green)
                league_head_title.visibility = View.INVISIBLE
                match_head_title.visibility = View.VISIBLE
                rv_parent.visibility = View.INVISIBLE
                rv_matches.visibility = View.VISIBLE
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadSubeldawry(type_league = type ?: "", link_league = link ?: "")
        viewModel.loadStanding(type_league = type ?: "", link_league = link ?: "")

        viewModel.standingResponse.observe(viewLifecycleOwner, {
            it?.data?.let { dataStanding ->
                league_header.text = dataStanding.groupEldwry?.name
                dataStanding.usersGroup?.let {
                    standingHeadToHeadAdapter =
                        StandingHeadToHeadAdapter(dataStanding.usersGroup)
                    rv_parent.adapter = standingHeadToHeadAdapter
                }

                dataStanding.matchGroupList?.let {list->
                    NumWeek.text = list[0]?.langNumWeek
                    standingMatchesHeadToHeadAdapter =
                        StandingMatchesHeadToHeadAdapter(dataStanding.matchGroupList)
                    rv_matches.adapter = standingMatchesHeadToHeadAdapter
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
        league_spinner.adapter = adapter
        adapter.count.let { league_spinner.setSelection(it) }
        league_spinner.onItemSelectedListener =
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
                        allLeagues_Tv.text = leagues[position]?.langNumWeek
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

        allLeagues_Tv.setOnClickListener {
            viewModel.recreatedStanding = false
            adapter.count.let { no -> league_spinner.setSelection(no) }
            league_spinner.performClick()
        }

    }

    companion object{
        const val STANDING = "standing"
        const val MATCHES = "matches"
    }
}