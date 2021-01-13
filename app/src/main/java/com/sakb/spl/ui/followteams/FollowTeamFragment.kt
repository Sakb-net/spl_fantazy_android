package com.sakb.spl.ui.followteams

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.FollowTeamRequest
import com.sakb.spl.data.model.FollowTeamRequestResult
import com.sakb.spl.data.model.GetTeamResponse
import com.sakb.spl.databinding.FragmentFollowTeamBinding
import com.sakb.spl.ui.followteams.adapter.MultiNotifEmailAdapter
import com.sakb.spl.ui.terms.TermsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class FollowTeamFragment : BaseFragment() {

    override val viewModel by viewModel<FollowTeamViewModel>()

    val teams: ArrayList<GetTeamResponse.Data?>? by lazy {
        arguments?.getParcelableArrayList(TEAMS)
    }

    val teamsNew: ArrayList<FollowTeamRequest> = ArrayList()
    var teamsResult: ArrayList<FollowTeamRequest> = ArrayList()

    val finalResult: ArrayList<FollowTeamRequestResult> = ArrayList()
    private var _binding: FragmentFollowTeamBinding? = null
    private val binding get() = _binding
    private var adapter: MultiNotifEmailAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowTeamBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teams?.forEach {
            val followTeamRequest =
                FollowTeamRequest(name = it?.name ?: "", link = it?.link, isEmail = 0, isNotif = 0)

            teamsNew.add(followTeamRequest)
        }

        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        adapter = MultiNotifEmailAdapter(teamsNew) {
            teamsResult = it
        }
        binding?.recyclerView?.adapter = adapter

        binding?.buttonStart?.setOnClickListener {
            teamsResult.forEach { followTeamRequest ->
                val followTeamRequestResult =
                    FollowTeamRequestResult(isEmail = followTeamRequest.isEmail,
                        isNotif = followTeamRequest.isNotif,
                        link = followTeamRequest.link)
                finalResult.add(followTeamRequestResult)
            }
            val followString = Gson().toJson(finalResult)
            viewModel.getFollowTeams(followString)
        }

        viewModel.followTeamResponse.observe(viewLifecycleOwner, {
            if (it?.data == true) {
                startActivity(
                    Intent(activity, TermsActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        })
    }

    companion object {
        val TAG = FollowTeamFragment::class.java.simpleName
        const val TEAMS = "teams"
    }
}