package com.sakb.spl.ui.register

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ahmedadel.socialmediasignup.SocialMediaSignUp
import com.ahmedadel.socialmediasignup.SocialMediaSignUp.SocialMediaType
import com.ahmedadel.socialmediasignup.callback.SocialMediaSignUpCallback
import com.ahmedadel.socialmediasignup.model.SocialMediaUser
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.data.model.LoginResponse
import com.sakb.spl.databinding.ActivityRegisterBinding
import com.sakb.spl.ui.chooseteam.ChooseFavTeamActivity
import com.sakb.spl.ui.main.MainActivity
import com.sakb.spl.utils.ImageUtils
import com.sakb.spl.utils.LanguageUtil
import com.sakb.spl.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class RegisterActivity : BaseActivity(), SocialMediaSignUpCallback {

    private lateinit var binding: ActivityRegisterBinding

    override val viewModel by viewModel<RegisterViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        var backIcon = BitmapFactory.decodeResource(resources,R.drawable.back)
        backIcon = if(LanguageUtil.isArabic()){
            ImageUtils.rotateImage(backIcon,0f)
        } else{
            ImageUtils.rotateImage(backIcon,180f)
        }
        binding.back.setImageBitmap(backIcon)
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.signIn.setOnClickListener {
            onBackPressed()
        }



        binding.buttonRegister.setOnClickListener {
            val email = binding.EmailEtt.text.toString()
            val name = binding.UserNameEtt.text.toString()
            val phone = binding.PhoneEtt.text.toString()
            val pass = binding.PasswordEtt.text.toString()
            val confirmPass = binding.ConfirmPasswordEtt.text.toString()

            if (pass.isEmpty() || confirmPass.isEmpty() || (email.isEmpty() && phone.isEmpty()) || name.isEmpty()) {
                toast(getString(R.string.all_fileds_must_filled))
                return@setOnClickListener
            }

            if (pass != confirmPass) {
                toast(getString(R.string.pass_not_match))
                return@setOnClickListener
            }

            viewModel.register(name, email, phone, pass)
        }

        viewModel.registerResultLiveData.observe(this, Observer { data ->
            // toast(""+it.status)


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

                        state = data.data?.state
                    ),
                    message = data.Message, statusCode = data.StatusCode
                )
            )

            /*         startActivity(
                   Intent(this@RegisterActivity, MainActivity::class.java)
                       .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
               )*/
            startActivity(Intent(this@RegisterActivity, ChooseFavTeamActivity::class.java))


        })

        binding.fbLogin.setOnClickListener {
            Timber.e("login with facebook!")
            viewModel.provider = "facebook"
            SocialMediaSignUp.getInstance().connectTo(SocialMediaType.FACEBOOK, null, this)

        }

        binding.twitterLogin.setOnClickListener {
            Timber.e("login with twitter!")
            viewModel.provider = "twitter"
            SocialMediaSignUp.getInstance().connectTo(SocialMediaType.TWITTER, null, this)

        }

        binding.googleLogin.setOnClickListener {
            Timber.e("login with google!")
            viewModel.provider = "google"
            SocialMediaSignUp.getInstance().connectTo(SocialMediaType.GOOGLE_PLUS, null, this)

        }

        viewModel.loginSocialResultLiveData.observe(this,
            Observer { data ->


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

                            state = data.data?.state
                        )
                    )
                )
                startActivity(
                    Intent(this@RegisterActivity, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )


            })
    }

    override fun onSuccess(
        p0: SocialMediaSignUp.SocialMediaType?,
        socialMediaUser: SocialMediaUser?
    ) {

        setSocialMediaContent(socialMediaUser)
    }

    private fun setSocialMediaContent(socialMediaUser: SocialMediaUser?) {
        Timber.e(
            "success === " + "Access Token :" + socialMediaUser?.accessToken + "\n" +
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
