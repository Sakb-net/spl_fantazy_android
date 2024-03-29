package com.sakb.spl.ui.chooseteam

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.databinding.ActivityChooseFavTeamBinding
import com.sakb.spl.ui.chooseteam.adapter.SingleAdapter
import com.sakb.spl.ui.chooseteam.adapter.teams
import com.sakb.spl.ui.terms.TermsActivity
import com.sakb.spl.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class ChooseFavTeamActivity : BaseActivity() {

    private lateinit var binding: ActivityChooseFavTeamBinding
    private lateinit var context : Context
    private lateinit var dialog: Dialog

     override val viewModel by viewModel<ChooseTeamViewModel>()


    private val teamsList = mutableListOf<teams>()
    private var adapter: SingleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_fav_team)

        val window = window
        val winParams = window.attributes
        winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        window.attributes = winParams
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    //    binding.recyclerView.addItemDecoration(DividerItemDecorationNoLast(this, LinearLayoutManager.VERTICAL))

        viewModel.getTeams()

        viewModel.ResultLiveData.observe(this, Observer {data->
                        toast(""+data.Message)
                        Timber.e(""+data?.data?.size)
                        adapter = SingleAdapter( data?.data)
                        binding.recyclerView.adapter = adapter
                        adapter?.onItemClick = {_, data->
                            //val user = PrefManager.getUser()
                            viewModel.updateProfile(""+data.link)
                        }

                        //createList()






            })

        viewModel.updateProfileResultLiveData.observe(this, Observer {data->
                // toast(""+it.status)

                        toast(""+data.Message)
                        startActivity(
                            Intent(this@ChooseFavTeamActivity, TermsActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        )

                        //createList()






            })

        /*
        to get selected
        */
       // showToast(adapter.selected!!.name)
    }

   /* private fun createList() {
        for (i in 0..19) {
            val team = teams()
            team.name=("Team " + (i + 1))
            teamsList.add(team)
        }
        adapter?.setEmployees(teamsList)
    }*/


}
