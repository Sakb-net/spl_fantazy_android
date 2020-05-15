package com.sakb.spl.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.VideosResponse
import com.sakb.spl.databinding.FragmentVideosBinding
import com.sakb.spl.ui.videos.adapters.VideosAdapter
import com.sakb.spl.utils.RecyclerViewScrollListener
import com.sakb.spl.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class VideosFragment : BaseFragment() {



    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!

    override val viewModel by viewModel<VideosViewModel>()

    private val videosAdapter by lazy { VideosAdapter() }
    private val scrollListener:RecyclerViewScrollListener by lazy {
        object : RecyclerViewScrollListener() {
            override fun onEndOfScrollReached(rv: RecyclerView) {
              Timber.e("End of the RecyclerView reached. Do your pagination stuff here")
               scrollListener.disableScrollListener()
                viewModel.loadMore()
            }
        } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun createVideosAdapter() = videosAdapter.apply {
        onClickListener = {
            //  findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun initRecyclerView() {
            createVideosAdapter()
        binding.videoList.recyclerView.itemAnimator = null
        binding.videoList.recyclerView.adapter = videosAdapter
        binding.videoList.recyclerView.addOnScrollListener(scrollListener)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        viewModel.videosLiveData.observe(this, Observer {
            videosAdapter.submitList(it)
            scrollListener.enableScrollListener()
        })
        viewModel.initLoading()
    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
