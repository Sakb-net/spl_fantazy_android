package com.sakb.spl.ui.myleague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.ui.league.LeagueFragment.Companion.CLASSIC
import com.sakb.spl.ui.league.LeagueFragment.Companion.HEAD_TO_HEAD
import com.sakb.spl.ui.myleague.adapter.MyLeaguesAdapter
import com.sakb.spl.utils.showLeaguesSettingsDialog
import kotlinx.android.synthetic.main.fragment_my_league.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyLeagueFragment : BaseFragment() {

    override val viewModel by viewModel<MyLeagueViewModel>()

    lateinit var myLeaguesAdapter: MyLeaguesAdapter
    lateinit var myLeaguesHeadAdapter: MyLeaguesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadAllLeaguesClassic(CLASSIC)

        viewModel.leaguesResponseClassic.observe(viewLifecycleOwner, {
            myLeaguesAdapter =
                MyLeaguesAdapter(it.data?.groupEldwry, user?.data?.id) { link, admin ->
                    context?.showLeaguesSettingsDialog(link, admin, standingBtn = { dialog, link ->
                        val bundle = bundleOf(LINK_LEAGUE to link)
                        findNavController().navigate(R.id.action_MyLeagueFragment_to_StandingFragment,
                            bundle)
                        dialog?.dismiss()
                    }, managementBtn = { dialog, link ->
                        val bundle = bundleOf(LINK_LEAGUE to link, LINK_TYPE to CLASSIC)
                        findNavController().navigate(R.id.action_MyLeagueFragment_to_ManageLeagueFragment,
                            bundle)
                        dialog?.dismiss()
                    }, leaveBtn = { dialog, link ->
                        viewModel.loadLeaveLeague(CLASSIC, link)
                        dialog?.dismiss()
                    })
                }
            rv_parent.adapter = myLeaguesAdapter
        })

        viewModel.loadAllLeaguesHead(HEAD_TO_HEAD)

        viewModel.leaguesResponseHead.observe(viewLifecycleOwner, {
            myLeaguesHeadAdapter =
                MyLeaguesAdapter(it.data?.groupEldwry, user?.data?.id) { link, admin ->
                    context?.showLeaguesSettingsDialog(link, admin, standingBtn = { dialog, link ->
                        val bundle = bundleOf(LINK_LEAGUE to link, LINK_TYPE to HEAD_TO_HEAD)
                        findNavController().navigate(R.id.action_MyLeagueFragment_to_StandingFragment,
                            bundle)
                        dialog?.dismiss()
                    }, managementBtn = { dialog, link ->
                        val bundle = bundleOf(LINK_LEAGUE to link, LINK_TYPE to HEAD_TO_HEAD)
                        findNavController().navigate(R.id.action_MyLeagueFragment_to_ManageLeagueFragment,
                            bundle)
                        dialog?.dismiss()
                    }, leaveBtn = { dialog, link ->
                        viewModel.loadLeaveLeague(HEAD_TO_HEAD, link)
                        dialog?.dismiss()
                    })
                }
            rv_parent_head.adapter = myLeaguesHeadAdapter
        })


        viewModel.leaveLeague.observe(viewLifecycleOwner, {
            it.data?.let { value ->
                if (value) {
                    viewModel.loadAllLeaguesClassic(CLASSIC)
                    viewModel.loadAllLeaguesHead(HEAD_TO_HEAD)
                }
            }
        })
    }


    companion object {
        const val LINK_LEAGUE = "link_league"
        const val LINK_TYPE = "link_type"
    }

}