package com.sakb.spl.ui.login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ahmedadel.socialmediasignup.SocialMediaSignUp
import com.ahmedadel.socialmediasignup.callback.SocialMediaSignUpCallback
import com.ahmedadel.socialmediasignup.model.SocialMediaUser
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.data.model.LoginResponse
import com.sakb.spl.databinding.ActivityLoginBinding
import com.sakb.spl.ui.forgotpassword.ForgotPassActivity
import com.sakb.spl.ui.main.MainActivity
import com.sakb.spl.ui.register.RegisterActivity
import com.sakb.spl.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginActivity :  BaseActivity() , SocialMediaSignUpCallback {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var context : Context
    private lateinit var dialog: Dialog

    override val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        context = this


        binding.signUp.setOnClickListener {
            startActivity(Intent(this , RegisterActivity::class.java))
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.tvForgotPass.setOnClickListener {
            startActivity(Intent(this , ForgotPassActivity::class.java))
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.UserNameEtt.text.toString()
            val pass =  binding.PasswordEtt.text.toString()
            viewModel.login(email, pass)
            // startActivity(Intent(this , MainActivity::class.java))
        }

        binding.fbLogin.setOnClickListener {
            Timber.e("login with facebook!")
            viewModel.provider = "facebook"
            SocialMediaSignUp.getInstance().connectTo(SocialMediaSignUp.SocialMediaType.FACEBOOK, null, this)

        }

        binding.twitterLogin.setOnClickListener {
            Timber.e("login with twitter!")
            viewModel.provider = "twitter"
            SocialMediaSignUp.getInstance().connectTo(SocialMediaSignUp.SocialMediaType.TWITTER, null, this)

        }

        binding.googleLogin.setOnClickListener {
            Timber.e("login with google!")
            viewModel.provider = "google"
            SocialMediaSignUp.getInstance().connectTo(SocialMediaSignUp.SocialMediaType.GOOGLE_PLUS, null, this)

        }

        viewModel.loginResultLiveData.observe(this, Observer {data->
                       // toast(""+data.message)
                            PrefManager.saveUser(data)
                            startActivity(
                                Intent(this@LoginActivity, MainActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            )
            })

        viewModel.loginSocialResultLiveData.observe(this, Observer {data->

                        toast(""+data.Message)

                        PrefManager.saveUser(        LoginResponse(data = LoginResponse.Data(

                            accessToken = data.data?.access_token,
                            address = data.data?.address,
                            city = data.data?.city,

                            displayName = data.data?.display_name,
                            email = data.data?.email,
                            gender = data.data?.gender,

                            image = data.data?.image,

                            newFcmToken= data.data?.new_fcm_token,



                            phone= data.data?.phone,

                            state = data.data?.state
                        ))
                        )
                        startActivity(
                            Intent(this@LoginActivity, MainActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        )




            })
    }

    override fun onSuccess(p0: SocialMediaSignUp.SocialMediaType?, socialMediaUser : SocialMediaUser?) {

        setSocialMediaContent(socialMediaUser)
    }

    private fun setSocialMediaContent(socialMediaUser: SocialMediaUser?) {

        Timber.e("success === "+
                "User Id : "+socialMediaUser?.userId+
                "\n"+ "Access Token :" + socialMediaUser?.getAccessToken() + "\n" +
                "Full Name : " + socialMediaUser?.getFullName() + "\n" +
                "Profile Picture Link :" + socialMediaUser?.getProfilePictureUrl())

        viewModel.loginSocial(viewModel.provider,""+socialMediaUser?.userId
            ,""+socialMediaUser?.getFullName())
    }

    override fun onSignOut(p0: SocialMediaSignUp.SocialMediaType?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(p0: Throwable?) {
        p0?.printStackTrace()
        toast(getString(R.string.something_wrong))

    }

    /*private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@LoginActivity, Constants.FONT_REGULAR,
            binding.PasswordEtt)
        binding.PasswordEtt.transformationMethod = PasswordTransformationMethod()

    }*/


}
