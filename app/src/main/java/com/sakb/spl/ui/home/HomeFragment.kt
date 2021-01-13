package com.sakb.spl.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.data.model.*
import com.sakb.spl.databinding.HomeFragmentBinding
import com.sakb.spl.ui.home.adapters.*
import com.sakb.spl.ui.login.LoginActivity
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
        savedInstanceState: Bundle?,
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun createHeaderAdapter() = headerAdapter.apply {
        onChooseTeamClickListener = {
            if (PrefManager.getUser() == null) {
                val intent = Intent(activity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(0, 0)
            } else {
                findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
            }

        }
        onPointClickListener = {
            findNavController().navigate(R.id.action_homeFragment_to_myPoints)
        }
        onMyTeamClickListener = {
            findNavController().navigate(R.id.action_homeFragment_to_myTeam)
        }
        onTransClickListener = {
            val bundle = bundleOf(TRANSFERS_DATA to transfersData)
            findNavController().navigate(R.id.action_homeFragment_to_trans, bundle)
        }
    }

    private fun createHeaderFixturesAdapter() = headerFixturesAdapter.apply {
        onClickListener = {
            //    findNavController().navigate(R.id.action_homeFragment_to_chooseTeamPlayersFragment)
        }
    }

    private fun createFooterFixturesAdapter() = footerFixturesAdapter.apply {
        onClickListener = {
            findNavController().navigate(R.id.action_homeFragment_to_moreMatches)
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
        val mergeAdapter = ConcatAdapter(
            createHeaderAdapter(),
            createHeaderFixturesAdapter(),
            createFixturesAdapter(),
            createFooterFixturesAdapter()//,
//            createTitlesAdapter(),
//            createNewsAdapter(),
//            createTitlesVideosAdapter(),
//            createVideosAdapter()
        )
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = mergeAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.homeLiveData.observe(this, Observer {
            updateUI(it)
        })

        viewModel.fixturesResponse.observe(viewLifecycleOwner, Observer {
            updateFixtureUI(it)
        })

        viewModel.homePointEldaweryResponse.observe(viewLifecycleOwner, Observer {
            updateHomeUI(it)
        })

        viewModel.publicPointEldaweryResponse.observe(viewLifecycleOwner, Observer {
            updatePublicUI(it)
        })
        // viewModel.loadHmeData()
        viewModel.loadLastFixtureData()
        if (user?.data?.chooseTeam == 1) {
            viewModel.loadHomePointEldwry()
            viewModel.loadPublicPointEldwry()
        }
    }

    private fun updatePublicUI(publicPointEldaweryResponse: PublicPointEldaweryResponse?) {
        publicPointEldaweryResponse?.data?.let { dataPublic ->
            transfersData.transferFree = dataPublic.countFreeWeekgamesubstitute
            binding.llInfoTeam.visibility = View.VISIBLE
            dataPublic.sumTotalSubeldwry?.let {
                binding.llWeek.visibility = View.VISIBLE
                binding.weekBody.text = it.toString()
            }
            dataPublic.sumTotalPoints?.let {
                binding.llTotalPoint.visibility = View.VISIBLE
                binding.TotalPointBody.text = it.toString()
            }
            dataPublic.sortFinalUsers?.let {
                binding.llOrder.visibility = View.VISIBLE
                binding.OrderBody.text = it.toString()
            }
            dataPublic.countFreeWeekgamesubstitute?.let {
                binding.llFreeTrans.visibility = View.VISIBLE
                binding.FreeTransBody.text = it.toString()
            }
            dataPublic.gameWeekChanges?.let {
                binding.llNoTransfer.visibility = View.VISIBLE
                binding.NoTransferBody.text = it.toString()
            }
            dataPublic.totalChanges?.let {
                binding.llTotalTrans.visibility = View.VISIBLE
                binding.TotalTransBody.text = it.toString()
            }
        }
    }

    private fun updateHomeUI(homePointEldawryResponse: HomePointEldawryResponse?) {
        homePointEldawryResponse?.homePoints?.let { homePoints ->
            headerAdapter.homePoint(homePoints)
        }
        homePointEldawryResponse?.data?.let {
            transfersData.changePoint = it.changePoint
            binding.weekTitle.text = "${getString(R.string.weekNum)} (${it.numWeek})"
        }
    }

    private fun updateFixtureUI(data: GetLastFixturesResponse?) {
        data?.data?.let {
            if (!it.isNullOrEmpty()) {
                it[0]?.apply {
                    val headerFixturesTitlesList = mutableListOf<DataItem>()
                    headerFixturesTitlesList.add(this)
                    headerFixturesAdapter.submitList(headerFixturesTitlesList)
                    fixturesAdapter.submitList(this.matchGroup)
                    footerFixturesAdapter.hideFooter(data?.data)
                }
            }
        }
        initRecyclerView()
    }

    private fun updateUI(data: HomeResponse?) {
        data?.data?.fixtures?.let {
            if (!it.isNullOrEmpty()) {
                it[0]?.apply {
                    val headerFixturesTitlesList = mutableListOf<HomeResponse.Fixture>()
                    headerFixturesTitlesList.add(this)
                    //headerFixturesAdapter.submitList(headerFixturesTitlesList)
                    //fixturesAdapter.submitList(this.matchGroup)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TRANSFERS_DATA = "transfers_data"
        var transfersData: TransfersData = TransfersData()
    }
}
