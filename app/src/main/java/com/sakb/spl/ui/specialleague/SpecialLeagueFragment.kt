package com.sakb.spl.ui.specialleague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.SpecialLeagueFragmentBinding
import com.sakb.spl.ui.myleague.MyLeagueFragment.Companion.LINK_TYPE
import com.sakb.spl.utils.showConfirmationDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class SpecialLeagueFragment : BaseFragment() {

    override val viewModel by viewModel<SpecialLeagueViewModel>()
    private lateinit var binding: SpecialLeagueFragmentBinding

    val type: String? by lazy {
        arguments?.getString(LINK_TYPE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = SpecialLeagueFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSend.setOnClickListener {
            if (binding.codeEt.text?.toString()?.trim().isNullOrBlank()) {
                return@setOnClickListener
            } else {
                binding.codeEt.text?.toString()?.trim()
                    ?.let { code ->
                        viewModel.loadJoinLeague(valCode = code,
                            type_league = type ?: "")
                    }
            }
        }

        viewModel.joinLeagueResponse.observe(viewLifecycleOwner, {
            it.data?.let { data ->
                //status 1 Join
                //status 2 Join before
                //status -1 your league
                //status 0 this league not found
                if (data.status == 1) {
                    openConfirmationDialog(getString(R.string.classic_league_dialoug))
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.you_are_join_before),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun openConfirmationDialog(string: String) {
        context?.showConfirmationDialog(
            R.drawable.ic_done,
            string
        ) { dialog ->
            dialog?.dismiss()
        }
    }

}
