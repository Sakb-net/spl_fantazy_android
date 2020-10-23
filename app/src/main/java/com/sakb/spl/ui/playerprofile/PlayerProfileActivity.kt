package com.sakb.spl.ui.playerprofile

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.PlayerResponse
import com.sakb.spl.data.model.StatisticsDataItem
import com.sakb.spl.databinding.ActivityPlayerProfileBinding
import com.sakb.spl.ui.matches.MatchDetailsFragment
import com.sakb.spl.ui.matches.MatchDetailsFragment.Companion.LINK_OBJECT
import com.sakb.spl.ui.playerprofile.adapter.MatchesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayerProfileBinding
    override val viewModel by viewModel<PlayerProfileViewModel>()

    private val matchAdapter by lazy { MatchesAdapter(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player_profile)
        binding.toolbarTitle.text = ""
        val window = window
        val winParams = window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        window.attributes = winParams
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


        ///  binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.menu.setOnClickListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                onBackPressed()
            }
        }
        viewModel.playerInfoResultLiveData.observe(this, Observer { data ->
            updateUI(data)
        })

        //  val user = PrefManager.getUser()
        // val lang = PrefManager.getLanguage()
        viewModel.playerInfo(
            "" + intent.getStringExtra("link")
        )


    }

    private fun updateUI(data: PlayerResponse?) {
        data?.data?.playerData?.let { playerData ->
            binding.toolbarTitle.text = playerData.name
            binding.nameTv.text = playerData.name
            binding.teamTv.text = playerData.team
            binding.locationTv.text = playerData.locationPlayer

            Glide.with(this).load(Constants.baseUrl + playerData.image)
                .into(binding.playerIv)

            binding.fromTv.text = playerData.form.toString()
            binding.weekTv.text = playerData.week.toString()
            binding.totalTV.text = playerData.point.toString()
            binding.priceTv.text = playerData.cost.toString()
            binding.selectionPercentageTv.text = playerData.selPercentage.toString()
            binding.influenceTv.text = playerData.influence.toString()
            binding.creativityTv.text = playerData.creativity.toString()
            binding.threatsTv.text = playerData.threats.toString()
            binding.ictIndexTv.text = playerData.iCTIndex.toString()
        }
        data?.data?.statisticsData?.let { statisticsList ->
            binding.list.visibility = View.VISIBLE
            binding.recyclerView.adapter = matchAdapter
            val statisticsListResult = mutableListOf<StatisticsDataItem>()
            statisticsList.forEach { data ->
                data?.let {
                    statisticsListResult.add(it)
                }
            }
            matchAdapter.submitList(statisticsListResult)
            matchAdapter.addDataPlayer(data.data.playerData)
            matchAdapter.onClickListener = {
                val bundle = bundleOf(LINK_OBJECT to it.linkMatch)
                val matchDetailsFragment = MatchDetailsFragment()
                matchDetailsFragment.arguments = bundle
                val fm: FragmentManager = supportFragmentManager
                fm.beginTransaction().add(R.id.fragment_child, matchDetailsFragment)
                    .addToBackStack(MatchDetailsFragment::class.java.simpleName)
                    .commit()
            }
        }

    }

    companion object {
        const val KEY_PLAYER_INFO = "key_player_info"

    }
}
