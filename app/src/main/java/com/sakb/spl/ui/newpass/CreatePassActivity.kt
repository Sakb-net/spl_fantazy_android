package com.sakb.spl.ui.newpass

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.databinding.ActivityCreatePassBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePassActivity : BaseActivity() {

    private lateinit var binding: ActivityCreatePassBinding
    private lateinit var context: Context
    private lateinit var dialog: Dialog
    override val viewModel by viewModel<NewPassViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_pass)
        context = this
        // viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        //  changeViewsFonts()

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }
}
