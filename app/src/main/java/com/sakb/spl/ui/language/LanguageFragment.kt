package com.sakb.spl.ui.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.utils.LanguageUtil
import com.sakb.spl.utils.showDialogLogOut
import com.zeugmasolutions.localehelper.LocaleHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LanguageFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language, container, false)
    }

    override val viewModel by viewModel<LanguageViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.showDialogLogOut(getString(R.string.are_sure_change_language), { it ->
            it?.dismiss()
            performChangeLanguage()

        }, { it ->
            it?.dismiss()
            findNavController().navigateUp()
        })
    }

    private fun performChangeLanguage() {
        if (LanguageUtil.isArabic()) {
            LocaleHelper.setLocale(this@LanguageFragment.requireContext(), Locale("en"))
            //PrefManager.saveLanguage("en")
            (activity as BaseActivity).restartActivity()
        } else {
            LocaleHelper.setLocale(this@LanguageFragment.requireContext(), Locale("ar"))
            //PrefManager.saveLanguage("ar")
            (activity as BaseActivity).restartActivity()
        }
    }


}
