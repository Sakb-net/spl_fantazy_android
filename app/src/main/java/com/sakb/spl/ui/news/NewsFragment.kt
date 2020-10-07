package com.sakb.spl.ui.news


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.FragmentNewsBinding
import com.sakb.spl.ui.news.adapter.NewsAdapter
import com.sakb.spl.utils.RecyclerViewScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class NewsFragment : BaseFragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override val viewModel by viewModel<NewsViewModel>()

    private val videosAdapter by lazy { NewsAdapter() }
    private val scrollListener: RecyclerViewScrollListener by lazy {
        object : RecyclerViewScrollListener() {
            override fun onEndOfScrollReached(rv: RecyclerView) {
                Timber.e("End of the RecyclerView reached. Do your pagination stuff here")
                scrollListener.disableScrollListener()
                viewModel.loadMore()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun createVideosAdapter() = videosAdapter.apply {
        onClickListener = {
            //  findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun initRecyclerView() {
        createVideosAdapter()
        binding.newsList.recyclerView.itemAnimator = null
        binding.newsList.recyclerView.adapter = videosAdapter
        binding.newsList.recyclerView.addOnScrollListener(scrollListener)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        viewModel.newsLiveData.observe(this, Observer {
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
