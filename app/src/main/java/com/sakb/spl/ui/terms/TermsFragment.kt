package com.sakb.spl.ui.terms
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.FragmentTermsBinding


import org.koin.androidx.viewmodel.ext.android.viewModel

class TermsFragment : BaseFragment() {


    private  var _binding : FragmentTermsBinding ?= null
    private val binding = _binding!!
    override val viewModel by viewModel<TermsViewModel>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_terms, container, false)
        binding.toolbarTitle.text =getString(R.string.terms)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //  changeViewsFonts()
        initListener()
        viewModel.terms()

        viewModel.termsResultLiveData.observe(this, Observer {data->
                // toast(""+it.status)



                       // context?.toast(""+data.message)
                        if (data.statusCode==1){
                            binding.terms.text = data.data?.content
                        }




            })
    }

    private fun initListener() {
//        binding.menu.setOnClickListener {
//            (activity as MainActivity).binding.drawerLayout.openDrawer(GravityCompat.START)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
