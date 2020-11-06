package com.sakb.spl.ui.forgotpassword

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.ui.newpass.CreatePassActivity
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForgotPassActivity : BaseActivity() {

    private lateinit var context: Context
    private lateinit var dialog: Dialog
    override val viewModel by viewModel<ForgotPassViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        context = this

        back.setOnClickListener {
            onBackPressed()
        }
        buttonSend.setOnClickListener {
            startActivity(Intent(this, CreatePassActivity::class.java))
        }
    }

    private fun changeViewsFonts() {
//        Util.changeViewTypeFace(this@ForgotPassActivity, Constants.FONT_REGULAR,
//            binding.textView2,
//            binding.EmailEtt,
//            binding.buttonSend,
//            binding.textView4)

    }
}
