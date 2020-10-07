package com.sakb.spl.ui.specialleague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakb.spl.R

import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.SpecialLeagueFragmentBinding
import com.sakb.spl.utils.showConfirmationDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class SpecialLeagueFragment : BaseFragment() {

    override val viewModel by viewModel<SpecialLeagueViewModel>()
    private lateinit var binding: SpecialLeagueFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SpecialLeagueFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSend.setOnClickListener {
            openConfirmationDialog()
        }
    }

    private fun openConfirmationDialog() {
        context?.showConfirmationDialog(
            R.drawable.ic_done,
            "تم الانضمام بنجاح لدوري \" المحترفين\""
        ) { dialog ->
            dialog?.dismiss()
        }
    }
}
