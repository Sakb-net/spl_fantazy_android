package com.sakb.spl.ui.forgetpasswordwebview

import android.annotation.SuppressLint
import android.os.Bundle
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.databinding.ActivityForgetPasswordWebViewBinding
import com.sakb.spl.ui.forgotpassword.ForgotPassViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgetPasswordWebViewActivity : BaseActivity() {
    override val viewModel by viewModel<ForgotPassViewModel>()
    var _binding: ActivityForgetPasswordWebViewBinding? = null
    private val binding get() = _binding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgetPasswordWebViewBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.webView?.settings?.javaScriptEnabled = true
        binding?.webView?.loadUrl("http://sakbfantasy.sakb-co.com.sa/password/reset")
    }
}