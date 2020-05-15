package com.sakb.spl.ui.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.databinding.FragmentMyProfileBinding
import com.sakb.spl.constants.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MyProfileFragment : BaseFragment() {

    private lateinit var binding : FragmentMyProfileBinding
    override val viewModel by viewModel<MyProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()

        viewModel.loadMyProfile()

        viewModel.profileResultLiveData.observe(this, Observer { data->


                        binding.name.text = data.data?.displayName
                        binding.emailVal.text = data.data?.email
                        binding.addressVal.text = data.data?.teamName

                        if (data.data?.image?.contains("http") == true) {
                            Timber.e("yesssss=======http")
                            Glide.with(this).load(data.data.image).circleCrop()
                                .into(binding.profileImage)
                        }
                        else
                            Glide.with(this).load(Constants.baseUrl+ data.data?.image).circleCrop().into(binding.profileImage)



            })
    }

    private fun initListener() {
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_editProfileFragment)
        }

        binding.changePass.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_changePasswordFragment)
        }

    }

}
