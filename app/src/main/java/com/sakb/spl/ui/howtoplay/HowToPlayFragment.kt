package com.sakb.spl.ui.howtoplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.HowToPlayFragmentBinding
import com.sakb.spl.ui.howtoplay.adapter.InstructionsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HowToPlayFragment : BaseFragment() {
    override val viewModel by viewModel<HowToPlayViewModel>()

    private val adapter by lazy {
        InstructionsAdapter()
    }

    private var _binding: HowToPlayFragmentBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = HowToPlayFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.itemAnimator = null
        binding?.recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.onClickListener = { position, _ ->
            Timber.e("pos is======== %s", position)
            viewModel.updateColapseState(position)
        }
        loadInstructionsStatuesObserver()
        viewModel.instructions()
    }


    private fun loadInstructionsStatuesObserver() {
        viewModel.instructionsListLiveData.observe(this, Observer {
            Timber.e("observed = role %s", it?.toString())
            adapter.submitList(it)
        })
    }

}
