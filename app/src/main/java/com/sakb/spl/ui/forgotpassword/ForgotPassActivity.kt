package com.sakb.spl.ui.forgotpassword

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.databinding.ActivityForgotPassBinding
import com.sakb.spl.utils.ImageUtils
import com.sakb.spl.utils.LanguageUtil
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForgotPassActivity : BaseActivity() {

    private lateinit var context: Context
    private lateinit var dialog: Dialog
    override val viewModel by viewModel<ForgotPassViewModel>()

    var _binding: ActivityForgotPassBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        context = this

        var backIcon = BitmapFactory.decodeResource(resources, R.drawable.back)
        backIcon = if (LanguageUtil.isArabic()) {
            ImageUtils.rotateImage(backIcon, 0f)
        } else {
            ImageUtils.rotateImage(backIcon, 180f)
        }
        binding?.back?.setImageBitmap(backIcon)
        binding?.back?.setOnClickListener {
            onBackPressed()
        }
        binding?.buttonSend?.setOnClickListener {
            if (binding?.EmailEtt?.text?.toString().isNullOrBlank()) {
                Toast.makeText(context,
                    getString(R.string.enter_your_email),
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.loadForgetPassword(email = binding?.EmailEtt?.text?.toString() ?: "")
            //startActivity(Intent(this, CreatePassActivity::class.java))
        }

        viewModel.forgetPassword.observe(this) {
            it?.data?.token?.let {
                //send email and token
                startActivity(Intent(this, TokenForgotPassActivity::class.java))
            }
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
