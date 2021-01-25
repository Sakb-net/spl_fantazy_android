package com.sakb.spl.ui.forgotpassword

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.databinding.ActivityForgotPassTokenBinding
import com.sakb.spl.ui.newpass.CreatePassActivity
import com.sakb.spl.utils.ImageUtils
import com.sakb.spl.utils.LanguageUtil
import org.koin.androidx.viewmodel.ext.android.viewModel


class TokenForgotPassActivity : BaseActivity() {

    private lateinit var context: Context
    private lateinit var dialog: Dialog
    override val viewModel by viewModel<ForgotPassViewModel>()

    var _binding: ActivityForgotPassTokenBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgotPassTokenBinding.inflate(layoutInflater)
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
            if (binding?.TokenEtt?.text?.toString().isNullOrBlank()) {
                Toast.makeText(context,
                    getString(R.string.enter_your_token),
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.loadConfirmPassword(email = "", binding?.TokenEtt?.text?.toString() ?: "")
            //startActivity(Intent(this, CreatePassActivity::class.java))
        }

        viewModel.confirmResetPassword.observe(this) {
            if(it?.data.isNullOrEmpty()){
                Toast.makeText(context,
                    getString(R.string.enter_right_token),
                    Toast.LENGTH_LONG).show()
                return@observe
            }
            it?.data?.let {
                //sendEmail
                startActivity(Intent(this, CreatePassActivity::class.java))
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
