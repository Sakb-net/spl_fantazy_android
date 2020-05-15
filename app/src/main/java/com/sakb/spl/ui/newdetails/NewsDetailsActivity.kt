package com.sakb.spl.ui.newdetails

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.data.model.NewsResponse
import com.sakb.spl.databinding.ActivityNewsBinding
import com.sakb.spl.constants.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsDetailsActivity :  BaseActivity() {

    private lateinit var binding: ActivityNewsBinding
    override val viewModel by viewModel<NewsDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()
        val news : NewsResponse.Data? = intent.getParcelableExtra("obj")

        binding.name.text = news?.name
        binding.createdAtTv.text = news?.created_at
        binding.content.text = news?.description

        Glide.with(this).load(Constants.baseUrl+news?.image).placeholder(R.drawable.placeholder).into(binding.newsIv)

    }

    private fun changeViewsFonts() {
//        Util.changeViewTypeFace(this@NewsDetailsActivity,
//            Constants.FONT_REGULAR,
//            binding.toolbarTitle)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
