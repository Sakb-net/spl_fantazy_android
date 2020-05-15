package com.sakb.spl.ui.terms

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sakb.spl.ui.main.MainActivity
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.databinding.ActivityTermsBinding
import com.sakb.spl.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class TermsActivity : BaseActivity() {


    private lateinit var binding: ActivityTermsBinding
    private lateinit var context : Context
    private lateinit var dialog: Dialog
    override val viewModel by viewModel<TermsViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms)
        binding.toolbarTitle.text =getString(R.string.terms)
        changeViewsFonts()
        context = this
        viewModel.terms()

        viewModel.termsResultLiveData.observe(this, Observer {data->



                        // context?.toast(""+data.message)
                       // if (data.statusCode==1){
                            binding.terms.text = data.data?.content

                        binding.buttonStart.setOnClickListener {
                            if (binding.checkbox.isChecked)
                            startActivity(
                                Intent(this@TermsActivity, MainActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            )
                            else
                                toast(getString(R.string.you_must_accept_terms_conditions))

                        }
                        //}




            })
    }



    private fun changeViewsFonts() {
//        Util.changeViewTypeFace(this@TermsActivity, Constants.FONT_REGULAR,
//            binding.toolbarTitle)


    }

}
