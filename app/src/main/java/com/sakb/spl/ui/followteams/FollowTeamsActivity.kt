package com.sakb.spl.ui.followteams

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.data.model.GetTeamResponse
import com.sakb.spl.ui.chooseteam.ChooseFavTeamActivity.Companion.TEAM_LINK
import com.sakb.spl.ui.chooseteam.ChooseFavTeamActivity.Companion.TEAM_NAME
import com.sakb.spl.ui.followteams.FollowTeamFragment.Companion.TEAMS
import com.sakb.spl.ui.followteams.adapter.MultiAdapter
import kotlinx.android.synthetic.main.activity_follow_teams.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class FollowTeamsActivity : BaseActivity() {
    override val viewModel by viewModel<FollowTeamViewModel>()
    private var adapter: MultiAdapter? = null

    val team_fav_link: String by lazy {
        intent.getStringExtra(TEAM_LINK)
    }
    val team_fav_name: String by lazy {
        intent.getStringExtra(TEAM_NAME)
    }

    var teams: ArrayList<GetTeamResponse.Data?>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow_teams)

        val window = window
        val winParams = window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        window.attributes = winParams
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getTeams()

        viewModel.ResultLiveData.observe(this, { data ->
            Timber.e("" + data?.data?.size)
            adapter = MultiAdapter(data?.data, onCLick = {
                teams = it as ArrayList<GetTeamResponse.Data?>?
            })
            recyclerView.adapter = adapter

        })

        buttonStart.setOnClickListener {
            val teamResponse = GetTeamResponse.Data(team_fav_link, team_fav_name, true)
            if (teams.isNullOrEmpty()) {
                teams?.add(teamResponse)
            } else {
                if (teams?.contains(teamResponse) != true) {
                    teams?.add(teamResponse)
                }
            }
            val bundle = Bundle()
            bundle.putParcelableArrayList(TEAMS, teams)
            val followTeamFragment = FollowTeamFragment()
            followTeamFragment.arguments = bundle
            val manager: FragmentManager? = supportFragmentManager
            val transaction: FragmentTransaction? = manager?.beginTransaction()
            transaction?.replace(R.id.container, followTeamFragment, FollowTeamFragment.TAG)
            transaction?.addToBackStack(null)
            transaction?.commit()

        }
    }
}