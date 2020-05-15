package com.sakb.spl.ui.contactus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.FragmentContactUsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactUsFragment : BaseFragment() {


    private lateinit  var binding : FragmentContactUsBinding
    //private val binding = _binding!!
    override val viewModel by viewModel<ContactUsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      // activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_us, container, false)
        return binding.root
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
    }

    private fun initListener() {
//        binding.menu.setOnClickListener {
//            (activity as MainActivity).binding.drawerLayout.openDrawer(GravityCompat.START)
//        }
    }


   /* override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/




}
