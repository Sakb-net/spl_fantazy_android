package com.sakb.spl.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.FragmentHelpsBinding
import com.sakb.spl.ui.help.adapter.HelpAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HelpFragment : BaseFragment() {


    private lateinit var binding: FragmentHelpsBinding
    //private val binding = _binding!!

    override val viewModel by viewModel<HelpViewModel>()


    private val adapter by lazy {
        HelpAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_helps,
            container,
            false
        )
        return binding.root

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.onClickListener = { position, _ ->
            Timber.e("pos is======== %s", position)
            viewModel.updateColapseState(position)
        }
        loadInstructionsStatuesObserver()
        viewModel.help()
    }


    private fun loadInstructionsStatuesObserver() {


        viewModel.helpListLiveData.observe(this, Observer {
            // Timber.e("observed = role "+it?.toString())
            adapter.submitList(it)
            // adapter.notifyDataSetChanged()
        })
    }

//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }


}
