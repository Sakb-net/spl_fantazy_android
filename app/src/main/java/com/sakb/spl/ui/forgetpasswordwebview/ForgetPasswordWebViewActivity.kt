package com.sakb.spl.ui.forgetpasswordwebview

import android.annotation.SuppressLint
import android.os.Bundle
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.ui.forgotpassword.ForgotPassViewModel
import kotlinx.android.synthetic.main.activity_forget_password_web_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgetPasswordWebViewActivity : BaseActivity() {
    override val viewModel by viewModel<ForgotPassViewModel>()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_web_view)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("http://sakbfantasy.sakb-co.com.sa/password/reset")
    }
}