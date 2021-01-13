package com.sakb.spl.ui.terms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.databinding.ActivityChooseFavTeamBinding
import com.sakb.spl.databinding.ActivityTermsBinding
import com.sakb.spl.ui.main.MainActivity
import com.sakb.spl.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class TermsActivity : BaseActivity() {
    private lateinit var context: Context
    override val viewModel by viewModel<TermsViewModel>()

    var _binding: ActivityTermsBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTermsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.toolbarTitle?.text = getString(R.string.terms)
        context = this
        viewModel.terms()

        viewModel.termsResultLiveData.observe(this, Observer { data ->

            binding?.terms?.text = data.data?.content?.trim()

            binding?.buttonStart?.setOnClickListener {
                if (binding?.checkbox?.isChecked == true)
                    startActivity(
                        Intent(this@TermsActivity, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                else
                    toast(getString(R.string.you_must_accept_terms_conditions))
            }
        })
    }

}
