package com.sakb.spl.ui.myleague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.FragmentMyLeagueBinding
import com.sakb.spl.ui.league.LeagueFragment.Companion.CLASSIC
import com.sakb.spl.ui.league.LeagueFragment.Companion.HEAD_TO_HEAD
import com.sakb.spl.ui.myleague.adapter.MyLeaguesAdapter
import com.sakb.spl.utils.showLeaguesSettingsDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyLeagueFragment : BaseFragment() {

    override val viewModel by viewModel<MyLeagueViewModel>()

    lateinit var myLeaguesAdapter: MyLeaguesAdapter
    lateinit var myLeaguesHeadAdapter: MyLeaguesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentMyLeagueBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyLeagueBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadAllLeaguesClassic(CLASSIC)

        viewModel.leaguesResponseClassic.observe(viewLifecycleOwner, {
            myLeaguesAdapter =
                MyLeaguesAdapter(it.data?.groupEldwry, user?.data?.id) { link, admin ->
                    context?.showLeaguesSettingsDialog(link,
                        admin,
                        standingBtn = { dialog, linkInside ->
                            val bundle = bundleOf(LINK_LEAGUE to linkInside)
                            findNavController().navigate(R.id.action_MyLeagueFragment_to_StandingFragment,
                                bundle)
                            dialog?.dismiss()
                        },
                        managementBtn = { dialog, linkInside ->
                            val bundle = bundleOf(LINK_LEAGUE to linkInside, LINK_TYPE to CLASSIC)
                            findNavController().navigate(R.id.action_MyLeagueFragment_to_ManageLeagueFragment,
                                bundle)
                            dialog?.dismiss()
                        },
                        leaveBtn = { dialog, linkInside ->
                            viewModel.loadLeaveLeague(CLASSIC, linkInside)
                            dialog?.dismiss()
                        })
                }
            binding?.rvParent?.adapter = myLeaguesAdapter
        })

        viewModel.loadAllLeaguesHead(HEAD_TO_HEAD)

        viewModel.leaguesResponseHead.observe(viewLifecycleOwner, {
            myLeaguesHeadAdapter =
                MyLeaguesAdapter(it.data?.groupEldwry, user?.data?.id) { link, admin ->
                    context?.showLeaguesSettingsDialog(link, admin, standingBtn = { dialog, linkInside ->
                        val bundle = bundleOf(LINK_LEAGUE to linkInside, LINK_TYPE to HEAD_TO_HEAD)
                        findNavController().navigate(R.id.action_MyLeagueFragment_to_StandingHeadToHeadFragment,
                            bundle)
                        dialog?.dismiss()
                    }, managementBtn = { dialog, linkInside ->
                        val bundle = bundleOf(LINK_LEAGUE to linkInside, LINK_TYPE to HEAD_TO_HEAD)
                        findNavController().navigate(R.id.action_MyLeagueFragment_to_ManageLeagueFragment,
                            bundle)
                        dialog?.dismiss()
                    }, leaveBtn = { dialog, linkInside ->
                        viewModel.loadLeaveLeague(HEAD_TO_HEAD, linkInside)
                        dialog?.dismiss()
                    })
                }
            binding?.rvParentHead?.adapter = myLeaguesHeadAdapter
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