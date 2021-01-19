package com.sakb.spl.ui.leagueorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.LeagueOrderFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeagueOrderFragment : BaseFragment() {
    private lateinit var binding: LeagueOrderFragmentBinding
    override val viewModel by viewModel<LeagueOrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.league_order_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}