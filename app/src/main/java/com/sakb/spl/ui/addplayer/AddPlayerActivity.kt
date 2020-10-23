package com.sakb.spl.ui.addplayer

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.data.model.PlayerByTypeResponse
import com.sakb.spl.databinding.ActivityAddPlayerBinding
import com.sakb.spl.utils.DividerItemDecorationNoLast
import com.sakb.spl.utils.SpinnerHelperAdapter
import com.sakb.spl.utils.showEnterRangeDialog
import com.sakb.spl.utils.toast
import org.greenrobot.eventbus.EventBus
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class AddPlayerActivity : BaseActivity() {

    private lateinit var binding: ActivityAddPlayerBinding

    private var playersByTypeAdapter: PlayersByTypeAdapter? = null

    override val viewModel by viewModel<AddPlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_player)
        binding.toolbarTitle.text = getString(R.string.add_player)
        val window = window
        val winParams = window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        window.attributes = winParams
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        binding.menu.setOnClickListener {
            onBackPressed()
        }

        viewModel.recreated = true
        viewModel.recreatedTeam = true
        viewModel.initSpinnerBefore = false
        viewModel.getPlayers(
            "" + intent.getStringExtra("type_loc_player"),
            "",
            "",
            "",
            ""
        )

        viewModel.AddPlayerResultLiveData.observe(this, Observer { data ->
            toast("" + data?.data?.msg_add)
            EventBus.getDefault().post(data)
            onBackPressed()
        })

        viewModel.changePlayerResultLiveData.observe(this, Observer { data ->
            toast("" + data?.result_change?.msg_delete)
            EventBus.getDefault().post(data)
            onBackPressed()
        })

        viewModel.ResultLiveData.observe(this, Observer { data ->

            data?.let {

                if (!viewModel.initSpinnerBefore) {
                    initTeamsSpinner(data)
                    viewModel.initSpinnerBefore = true
                }
                toast("" + data.Message)
                // Timber.e("" + data.data?.get(0)?.players_group?.size)
                if (data.data?.get(0)?.players_group != null) {

                    val listData = data.data?.get(0)?.players_group!!

                    data.data?.let {
                        if (it.size > 1)
                            for (i in 1..it.size - 1)
                                listData.addAll(it.get(i)?.players_group!!)
                    }

                    playersByTypeAdapter = PlayersByTypeAdapter(listData,this@AddPlayerActivity)
                    playersByTypeAdapter?.onItemClick = { _, data ->

                        if (intent.getStringExtra(ACTIONTYPE) == REPLACE) {
                            viewModel.changePlayer(
                                "" + intent.getStringExtra(ELDAWRYlINK),
                                "" + intent.getStringExtra(DELETEDPLAYER),
                                "" + data?.link
                            )
                        } else {
                            viewModel.addPlayer(
                                "" + data?.link
                            )
                        }
                    }

                    binding.recyclerView.addItemDecoration(
                        DividerItemDecorationNoLast(
                            this,
                            LinearLayoutManager.VERTICAL
                        )
                    )
                    binding.recyclerView.adapter = playersByTypeAdapter
                }
            }
        })
        initSpinner()
    }

    private fun initSpinner() {
        val mutableList = mutableListOf<String>()
        mutableList.add(getString(R.string.points_total))
        mutableList.add(getString(R.string.price_is_from_top_to_bottom))
        mutableList.add(getString(R.string.price_is_from_bottom_to_top))
        mutableList.add(getString(R.string.price_from_to))
        mutableList.add("___end__")
        val data = mutableList
        Timber.e("init spinner")
        val adapter =
            SpinnerHelperAdapter(this, data, android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.item_edited)
        binding.optionsSpinner.adapter = adapter

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
                    if (viewModel.recreated) {
                        return
                    }
                    toast("pos option " + position)
                    if (position != (data.size - 1)) {
                        binding.sortByTv.text = data[position]
                        when (data[position]) {
                            getString(R.string.points_total) -> {
                                viewModel.orderPlay = "point"
                                viewModel.getPlayers(
                                    "" + intent.getStringExtra("type_loc_player"),
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.selectedTeamLink,
                                    "",
                                    ""
                                )
                                /*   viewModel.getPlayers(
                                       "" + user?.data?.accessToken
                                       , "" + intent.getStringExtra("type_loc_player"),
                                       "point", lang
                                   )*/
                            }
                            getString(R.string.price_is_from_bottom_to_top) -> {
                                viewModel.orderPlay = "low_price"

                                viewModel.getPlayers(

                                    "" + intent.getStringExtra("type_loc_player"),
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.selectedTeamLink,
                                    "",
                                    ""
                                )

                                /* viewModel.getPlayers(
                                     "" + user?.data?.accessToken
                                     , "" + intent.getStringExtra("type_loc_player"),
                                     "low_price", lang
                                 )*/

                            }
                            getString(R.string.price_is_from_top_to_bottom) -> {

                                viewModel.orderPlay = "heigh_price"

                                viewModel.getPlayers(
                                    "" + intent.getStringExtra("type_loc_player"),
                                    "" + viewModel.orderPlay,
                                    "" + viewModel.selectedTeamLink,
                                    "",
                                    ""
                                )
                            }
                            getString(R.string.price_from_to) -> {

                                showEnterRangeDialog { dialog, priceFrom, priceTo ->
                                    dialog?.dismiss()

                                    viewModel.getPlayers(

                                        "" + intent.getStringExtra("type_loc_player"),
                                        "",
                                        "" + viewModel.selectedTeamLink,
                                        "" + priceFrom,
                                        "" + priceTo
                                    )
                                }
                            }
                        }
                    } else {
                        //  viewModel.orderPlay = ""

                        /*     viewModel.getPlayers(
                                 "" + user?.data?.accessToken
                                 , "" + intent.getStringExtra("type_loc_player"),
                                 "", lang
                             )*/
                    }
                }
            }

        binding.sortByTv.setOnClickListener {
            viewModel.recreated = false
            binding.optionsSpinner.setSelection(adapter.count)
            binding.optionsSpinner.performClick()
        }

    }

    private fun initTeamsSpinner(playerByTypeResponse: PlayerByTypeResponse) {
        val teams = playerByTypeResponse.data?.get(0)?.teams
        teams?.add(PlayerByTypeResponse.Team("", "___end__"))
        val adapter =
            TeamSpinnerAdapter(this, teams!!, android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.item_edited)
        binding.teamsSpinner.adapter = adapter
        binding.teamsSpinner.setSelection(adapter.count)
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
                    toast("team pos " + position)

                    if (position != (teams.size - 1)) {
                        binding.allTeamsTv.text = teams[position]?.team
                        viewModel.selectedTeamLink = teams[position]?.link

                        viewModel.getPlayers(
                            "" + intent.getStringExtra("type_loc_player"),
                            "" + viewModel.orderPlay,
                            "" + viewModel.selectedTeamLink,
                            "",
                            ""
                        )

                    } else {
                        //   viewModel.selectedTeamLink = ""
                        /*   viewModel.getPlayers(""+user?.data?.accessToken
                               ,""+intent.getStringExtra("type_loc_player"),
                               "",lang)*/
                    }
                    //  binding?.CityTv?.tag = listOfKeys!![position]
                }

            }

        binding.allTeamsTv.setOnClickListener {
            viewModel.recreatedTeam = false
            binding.teamsSpinner.setSelection(adapter.count)
            //  if (binding?.teamsSpinner?.selectedItemPosition == adapter.count)
            //    binding?.teamsSpinner?.setSelection(-1)

            binding.teamsSpinner.performClick()
        }

    }

    companion object {
        const val ELDAWRYlINK = "eldawry_link"
        const val DELETEDPLAYER = "deleted_player"
        const val ACTIONTYPE = "actiontype"
        const val REPLACE = "replace"
    }


}
