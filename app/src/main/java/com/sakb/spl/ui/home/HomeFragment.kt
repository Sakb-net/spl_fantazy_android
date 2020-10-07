package com.sakb.spl.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.MergeAdapter
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.databinding.HomeFragmentBinding
import com.sakb.spl.ui.home.adapters.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override val viewModel by viewModel<HomeViewModel>()

    private val headerAdapter by lazy { HeaderAdapter() }
    private val headerFixturesAdapter by lazy { FixturesHeaderAdapter() }
    private val fixturesAdapter by lazy { FixturesAdapter() }
    private val footerFixturesAdapter by lazy { FooterFixtureAdapter() }
    private val newsAdapter by lazy { NewsAdapter() }
    private val titlesAdapter by lazy { TitlesHeaderAdapter() }
    private val titlesVideosAdapter by lazy { TitlesHeaderAdapter() }
    private val videosAdapter by lazy { VideosAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun createHeaderAdapter() = headerAdapter.apply {
        onChooseTeamClickListener = {
            // todo
            findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun createHeaderFixturesAdapter() = headerFixturesAdapter.apply {
        onClickListener = {
            //    findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun createFooterFixturesAdapter() = footerFixturesAdapter.apply {
        onClickListener = {
            //   findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun createFixturesAdapter() = fixturesAdapter.apply {
        onClickListener = {
            //  findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun createNewsAdapter() = newsAdapter.apply {
        onClickListener = {
            //  findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun createTitlesAdapter() = titlesAdapter.apply {
        onClickListener = {
            //  findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun createTitlesVideosAdapter() = titlesVideosAdapter.apply {
        onClickListener = {
            //  findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun createVideosAdapter() = videosAdapter.apply {
        onClickListener = {
            //  findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun initRecyclerView() {
        val mergeAdapter = MergeAdapter(
            createHeaderAdapter(),
            createHeaderFixturesAdapter(), createFixturesAdapter(),
            createFooterFixturesAdapter(),
            createTitlesAdapter(),
            createNewsAdapter(),
            createTitlesVideosAdapter(),
            createVideosAdapter()
        )
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = mergeAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.homeLiveData.observe(this, Observer {
            updateUI(it)
        })
        viewModel.loadHmeData()
    }

    private fun updateUI(data: HomeResponse?) {
        data?.data?.fixtures?.let {
            if (!it.isNullOrEmpty()) {
                it[0]?.apply {
                    val headerFixturesTitlesList = mutableListOf<HomeResponse.Fixture>()
                    headerFixturesTitlesList.add(this)
                    headerFixturesAdapter.submitList(headerFixturesTitlesList)
                    fixturesAdapter.submitList(this.matchGroup)
                }
            }
        }
        data?.data?.news?.let {
            if (!it.isNullOrEmpty()) {
                val list = mutableListOf<String>()
                list.add(getString(R.string.latest_news))
                titlesAdapter.submitList(list)
                newsAdapter.submitList(it)
            }
        }

        data?.data?.videos?.let {
            if (!it.isNullOrEmpty()) {
                val list = mutableListOf<String>()
                list.add(getString(R.string.latest_videos))
                titlesVideosAdapter.submitList(list)
                videosAdapter.submitList(it)
            }
        }
        initRecyclerView()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
