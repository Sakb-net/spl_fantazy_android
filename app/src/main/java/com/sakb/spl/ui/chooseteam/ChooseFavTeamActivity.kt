package com.sakb.spl.ui.chooseteam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.ui.chooseteam.adapter.SingleAdapter
import com.sakb.spl.ui.terms.TermsActivity
import kotlinx.android.synthetic.main.activity_choose_fav_team.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class ChooseFavTeamActivity : BaseActivity() {
    override val viewModel by viewModel<ChooseTeamViewModel>()

    private var adapter: SingleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_fav_team)

        val window = window
        val winParams = window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        window.attributes = winParams
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getTeams()

        viewModel.ResultLiveData.observe(this, Observer { data ->
            Timber.e("" + data?.data?.size)
            adapter = SingleAdapter(data?.data)
            recyclerView.adapter = adapter
            adapter?.onItemClick = { _, data ->
                viewModel.updateProfile("" + data.link)
            }
        })

        viewModel.updateProfileResultLiveData.observe(this, Observer { data ->
            startActivity(
                Intent(this@ChooseFavTeamActivity, TermsActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        })
    }
}
