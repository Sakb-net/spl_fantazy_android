package com.sakb.spl.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sakb.spl.R
import com.sakb.spl.utils.Dialogs
import com.sakb.spl.utils.LocaleManager
import com.sakb.spl.utils.Snacky
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import com.zeugmasolutions.localehelper.LocaleHelperActivityDelegateImpl
import java.util.*


abstract class BaseActivity : LocaleAwareCompatActivity() {

    protected abstract val viewModel: BaseViewModel

    private val loadingView by lazy {
        Dialogs.getProgressDialog(this, R.string.loading_custom_title, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLoadingObserver()
        setupMessagesObserver()
    }

    private fun setupLoadingObserver() {
        viewModel.loadingState.observe(this, Observer { isVisible ->
            if (isVisible) {
                loadingView?.show()
            } else {
                loadingView?.dismiss()
            }
        })
    }

    private fun setupMessagesObserver() {
        viewModel.successEvent.observe(this, Observer { message ->
            Snacky.getSuccessSnacky(activity = this, message = message)?.show()
        })
        viewModel.errorEvent.observe(this, Observer { message ->
            Snacky.getErrorSnacky(
                activity = this,
                message = message ?: getString(R.string.something_wrong)
            )?.show()
        })
        viewModel.noConnectionErrorEvent.observe(this, Observer {
            Snacky.getErrorSnacky(activity = this, message = getString(R.string.need_internet))
                ?.show()
        })
        viewModel.unAuthorizedErrorEvent.observe(this, Observer {
            Snacky.getErrorSnacky(activity = this, message = getString(R.string.you_must_to_login))
                ?.show()
        })
    }
    fun restartActivity() {
        finish()
        val newIntent = intent
        newIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(newIntent)
    }

    override fun onDestroy() {
        loadingView?.dismiss()
        super.onDestroy()
    }

}