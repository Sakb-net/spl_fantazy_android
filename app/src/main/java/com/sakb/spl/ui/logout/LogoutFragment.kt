package com.sakb.spl.ui.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.ui.login.LoginActivity
import com.sakb.spl.utils.showDialogLogOut
import org.koin.androidx.viewmodel.ext.android.viewModel


class LogoutFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override val viewModel by viewModel<LogOutViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.showDialogLogOut(getString(R.string.are_sure_logout), {
                it ->it?.dismiss()
            performLogout()

        },{
                it -> it?.dismiss()
            findNavController().navigateUp()

        })
    }

    private fun performLogout() {
        PrefManager.saveUser(null)
        val logoutIntent = Intent(context, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(logoutIntent)
        activity?.finish()
    }
}
