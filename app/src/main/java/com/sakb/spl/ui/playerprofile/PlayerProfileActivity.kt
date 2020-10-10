package com.sakb.spl.ui.playerprofile

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.constants.Constants
import com.sakb.spl.databinding.ActivityPlayerProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayerProfileBinding
    override val viewModel by viewModel<PlayerProfileViewModel>()


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
            onBackPressed()
        }
        viewModel.playerInfoResultLiveData.observe(this, Observer { data ->

            binding.toolbarTitle.text = data.data?.name
            binding.nameTv.text = data.data?.name
            binding.teamTv.text = data.data?.team
            binding.locationTv.text = data.data?.location_player

            Glide.with(this).load(Constants.baseUrl + data?.data?.image)
                .into(binding.playerIv)


            //toast(""+data.data?.msg_add)


        })

        //  val user = PrefManager.getUser()
        // val lang = PrefManager.getLanguage()
        viewModel.playerInfo(
            "" + intent.getStringExtra("link")
        )


    }

    companion object{
        const val KEY_PLAYER_INFO = "key_player_info"
    }
}
