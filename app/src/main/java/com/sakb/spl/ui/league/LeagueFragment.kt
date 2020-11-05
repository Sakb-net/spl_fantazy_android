package com.sakb.spl.ui.league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.LeagueFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeagueFragment : BaseFragment() {

    private lateinit var binding: LeagueFragmentBinding
    override val viewModel by viewModel<LeagueViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.league_fragment,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.specialLeagueButton.setOnClickListener {
            findNavController().navigate(R.id.action_leagueFragment_to_specialLeagueFragment)
        }

        binding.powerfulLeague.setOnClickListener {
            findNavController().navigate(R.id.action_leagueFragment_to_joinToPowerfulLeagueFragment)
        }

        binding.classicButton.setOnClickListener {
            findNavController().navigate(R.id.action_leagueFragment_to_createClassicLeagueFragment)
        }

        binding.headButton.setOnClickListener {
            findNavController().navigate(R.id.action_leagueFragment_to_createHeadToHeadLeagueFragment)
        }

        binding.myLeaguesButton.setOnClickListener {
            findNavController().navigate(R.id.action_leagueFragment_to_MyLeagueFragment)
        }

    }

}
