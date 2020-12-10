package com.sakb.spl.ui.login

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ahmedadel.socialmediasignup.SocialMediaSignUp
import com.ahmedadel.socialmediasignup.callback.SocialMediaSignUpCallback
import com.ahmedadel.socialmediasignup.model.SocialMediaUser
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.data.model.LoginResponse
import com.sakb.spl.ui.forgetpasswordwebview.ForgetPasswordWebViewActivity
import com.sakb.spl.ui.main.MainActivity
import com.sakb.spl.ui.register.RegisterActivity
import com.sakb.spl.utils.ImageUtils
import com.sakb.spl.utils.LanguageUtil
import com.sakb.spl.utils.toast
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginActivity : BaseActivity(), SocialMediaSignUpCallback {

    private lateinit var context: Context

    override val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        sign_up.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        var backIcon = BitmapFactory.decodeResource(resources, R.drawable.back)
        backIcon = if (LanguageUtil.isArabic()) {
            ImageUtils.rotateImage(backIcon, 0f)
        } else {
            ImageUtils.rotateImage(backIcon, 180f)
        }
        back.setImageBitmap(backIcon)
        back.setOnClickListener {
            onBackPressed()
        }
        tv_forgotPass.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordWebViewActivity::class.java))
        }

        buttonLogin.setOnClickListener {
            val email = UserNameEtt.text.toString()
            val pass = PasswordEtt.text.toString()
            viewModel.login(email, pass)
            // startActivity(Intent(this , MainActivity::class.java))
        }

        fb_login.setOnClickListener {
            //toast(getString(R.string.soon))
            //return@setOnClickListener
            Timber.e("login with facebook!")
            viewModel.provider = "facebook"
            SocialMediaSignUp.getInstance()
                .connectTo(SocialMediaSignUp.SocialMediaType.FACEBOOK, null, this)

        }

        twitter_login.setOnClickListener {
            toast(getString(R.string.soon))
            return@setOnClickListener
            /*
            Timber.e("login with twitter!")
            viewModel.provider = "twitter"
            SocialMediaSignUp.getInstance()
                .connectTo(SocialMediaSignUp.SocialMediaType.TWITTER, null, this)
            */
        }

        google_login.setOnClickListener {
            //toast(getString(R.string.soon))
            //return@setOnClickListener

            Timber.e("login with google!")
            viewModel.provider = "google"
            SocialMediaSignUp.getInstance()
                .connectTo(SocialMediaSignUp.SocialMediaType.GOOGLE_PLUS, null, this)

        }

        viewModel.loginResultLiveData.observe(this, androidx.lifecycle.Observer { data ->
            // toast(""+data.message)
            PrefManager.saveUser(data)
            startActivity(
                Intent(this@LoginActivity, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        })

        viewModel.loginSocialResultLiveData.observe(this, Observer { data ->
            toast("" + data.Message)
            PrefManager.saveUser(
                LoginResponse(
                    data = LoginResponse.Data(
                        accessToken = data.data?.access_token,
                        address = data.data?.address,
                        city = data.data?.city,
                        displayName = data.data?.display_name,
                        email = data.data?.email,
                        gender = data.data?.gender,
                        image = data.data?.image,
                        newFcmToken = data.data?.new_fcm_token,
                        phone = data.data?.phone,
                        state = data.data?.state,
                        id = data.data?.id,
                        chooseTeam = data.data?.choose_team
                    )
                )
            )
            startActivity(
                Intent(this@LoginActivity, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        })
    }

    override fun onSuccess(
        p0: SocialMediaSignUp.SocialMediaType?,
        socialMediaUser: SocialMediaUser?,
    ) {
        setSocialMediaContent(socialMediaUser)
    }

    private fun setSocialMediaContent(socialMediaUser: SocialMediaUser?) {
        Timber.e("success === " +
                "User Id : " + socialMediaUser?.userId +
                "\n" + "Access Token :" + socialMediaUser?.accessToken + "\n" +
                "Full Name : " + socialMediaUser?.fullName + "\n" +
                "Profile Picture Link :" + socialMediaUser?.profilePictureUrl
        )

        viewModel.loginSocial(
            viewModel.provider, "" + socialMediaUser?.userId, "" + socialMediaUser?.fullName
        )
    }

    override fun onSignOut(p0: SocialMediaSignUp.SocialMediaType?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(p0: Throwable?) {
        p0?.printStackTrace()
        toast(getString(R.string.something_wrong))
    }
}
