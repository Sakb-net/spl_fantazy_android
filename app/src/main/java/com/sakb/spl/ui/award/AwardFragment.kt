package com.sakb.spl.ui.award

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.FragmentAwardBinding
import com.sakb.spl.ui.help.adapter.AwardAdapter
import com.sakb.spl.ui.help.adapter.HelpAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class AwardFragment : BaseFragment() {
    private lateinit var binding: FragmentAwardBinding
    override val viewModel by viewModel<AwardViewModel>()

    private val adapter by lazy {
        AwardAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_award, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadAward()
        viewModel.award.observe(viewLifecycleOwner, Observer {
            it?.data?.let {data->
                data.contentItems.let { list->
                    adapter.submitList(list)
                }
            }
        })
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvParent.adapter = adapter
        binding.rvParent.itemAnimator = null
        binding.rvParent.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}