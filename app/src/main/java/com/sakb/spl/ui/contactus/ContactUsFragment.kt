package com.sakb.spl.ui.contactus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.FragmentContactUsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactUsFragment : BaseFragment() {

    private lateinit var binding: FragmentContactUsBinding
    override val viewModel by viewModel<ContactUsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_us, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadContactUs()
        initObservers()
        initListener()
    }

    private fun initObservers() {
        viewModel.contactUsData.observe(viewLifecycleOwner, Observer { data ->
            binding.emailtv.text = data?.data?.email
            binding.addresstv.text = data?.data?.address
        })

        viewModel.addState.observe(viewLifecycleOwner, Observer {
            if (it > 0) {
                findNavController().navigateUp()
            }
        })
    }

    private fun initListener() {
        binding.buttonSend.setOnClickListener {
            viewModel.addContactUsMessage(binding.MessageContentEt.text?.toString() ?: "")
        }
    }


}
