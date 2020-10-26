package com.sakb.spl.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sakb.spl.R
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.data.model.TransfersData
import com.sakb.spl.ui.login.LoginActivity
import com.sakb.spl.utils.Dialogs
import com.sakb.spl.utils.Snacky


abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel
    open val user by  lazy {
        PrefManager.getUser()
    }
    private val loadingView by lazy {
        Dialogs.getProgressDialog(requireContext(), R.string.loading_custom_title, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoadingObserver()
        setupMessagesObserver()
    }

    private fun setupLoadingObserver() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { isVisible ->
            if (isVisible) {
                loadingView?.show()
            } else {
                loadingView?.dismiss()
            }
        })
    }

    override fun onDestroyView() {
        loadingView?.dismiss()
        super.onDestroyView()
    }

    private fun setupMessagesObserver() {
        viewModel.successEvent.observe(viewLifecycleOwner, Observer { message ->
            Snacky.getSuccessSnacky(activity = requireActivity(), message = message)?.show()

        })

        viewModel.errorEvent.observe(viewLifecycleOwner, Observer { message ->
            Snacky.getErrorSnacky(
                activity = requireActivity(),
                message = message ?: getString(R.string.something_wrong)
            )?.show()
        })

        viewModel.noConnectionErrorEvent.observe(viewLifecycleOwner, Observer {
            Snacky.getErrorSnacky(
                activity = requireActivity(),
                message = getString(R.string.need_internet)
            )?.show()
        })

        viewModel.unAuthorizedErrorEvent.observe(viewLifecycleOwner, Observer {
            PrefManager.saveUser(null)
            val logoutIntent = Intent(context, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(logoutIntent)
            activity?.finish()
            Snacky.getErrorSnacky(
                activity = requireActivity(),
                message = getString(R.string.you_must_to_login)
            )?.show()
        })
    }


}