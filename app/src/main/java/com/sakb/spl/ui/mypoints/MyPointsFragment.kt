package com.sakb.spl.ui.mypoints

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyPointsFragment : BaseFragment() {
    override val viewModel by viewModel<MyPointsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_points, container, false)
    }

}
