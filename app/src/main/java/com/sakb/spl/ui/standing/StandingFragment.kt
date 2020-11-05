package com.sakb.spl.ui.standing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.SubeldwrysItem
import com.sakb.spl.ui.myleague.MyLeagueFragment.Companion.LINK_LEAGUE
import com.sakb.spl.ui.standing.adapter.StandingAdapter
import com.sakb.spl.ui.standing.adapter.StandingSpinnerAdapter
import kotlinx.android.synthetic.main.standing_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingFragment : BaseFragment() {
    override val viewModel by viewModel<StandingViewModel>()

    val link: String? by lazy {
        arguments?.getString(LINK_LEAGUE, "")
    }

    lateinit var standingAdapter: StandingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.standing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadStanding(link ?: "")

        viewModel.standingResponse.observe(viewLifecycleOwner, {
            it?.data?.let { dataStanding ->
                dataStanding.usersGroup?.let {
                    standingAdapter = StandingAdapter(dataStanding.usersGroup)
                    rv_parent.adapter = standingAdapter
                }
            }
            it?.subeldwrys?.let { list ->
                initSubEldawry(list as ArrayList<SubeldwrysItem?>)
            }
        })
    }


    private fun initSubEldawry(leaguesResponse: ArrayList<SubeldwrysItem?>) {

        val leagues: MutableList<SubeldwrysItem?> = leaguesResponse
        leagues.add(SubeldwrysItem("",
            "",
            getString(R.string.all_weeks),
            "",
            getString(R.string.all_weeks),
            "",
            "",
            ""))

        val adapter =
            leagues.let {
                StandingSpinnerAdapter(
                    this,
                    leagues, android.R.layout.simple_spinner_dropdown_item
                )
            }
        adapter?.setDropDownViewResource(R.layout.item_edited)
        league_spinner.adapter = adapter
        adapter?.count?.let { league_spinner.setSelection(it) }
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
            adapter?.count?.let { no -> league_spinner.setSelection(no) }
            league_spinner.performClick()
        }

    }
}