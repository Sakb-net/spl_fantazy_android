package com.sakb.spl.ui.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.FragmentChangePasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : BaseFragment() {

    override val viewModel by viewModel<ChangePasswordViewModel>()

    var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.buttonSend?.setOnClickListener {
            viewModel.changePassword(
                old_password = binding?.PasswordEtt?.text.toString(),
                new_password = binding?.NewPasswordEtt?.text.toString()
            )
        }
    }
}
