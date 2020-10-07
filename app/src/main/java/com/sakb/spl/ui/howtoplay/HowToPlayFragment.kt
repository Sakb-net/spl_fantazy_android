package com.sakb.spl.ui.howtoplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.HowToPlayFragmentBinding
import com.sakb.spl.ui.howtoplay.adapter.InstructionsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HowToPlayFragment : BaseFragment() {

    private lateinit var binding: HowToPlayFragmentBinding
    //private val binding = _binding!!

    override val viewModel by viewModel<HowToPlayViewModel>()

    private val adapter by lazy {
        InstructionsAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.how_to_play_fragment,
            container,
            false
        )
        return binding.root

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        binding.menu.setOnClickListener {
//            (activity as MainActivity).binding.drawerLayout.openDrawer(GravityCompat.START)
//        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = null
        //  viewModel = ViewModelProviders.of(this, viewModelFactory).get(HowToPlayViewModel::class.java)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.onClickListener = { position, contentRole ->
            Timber.e("pos is======== " + position)
            viewModel.updateColapseState(position)
        }
        loadInstructionsStatuesObserver()
        viewModel.instructions()
    }


    private fun loadInstructionsStatuesObserver() {


        viewModel.instructionsListLiveData.observe(this, Observer {
            Timber.e("observed = role " + it?.toString())
            adapter.submitList(it)
            // adapter.notifyDataSetChanged()
        })
    }


//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
