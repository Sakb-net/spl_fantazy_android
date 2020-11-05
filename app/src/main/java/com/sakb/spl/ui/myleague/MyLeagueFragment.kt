package com.sakb.spl.ui.myleague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.ui.myleague.adapter.MyLeaguesAdapter
import com.sakb.spl.utils.showLeaguesSettingsDialog
import kotlinx.android.synthetic.main.fragment_my_league.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyLeagueFragment : BaseFragment() {

    override val viewModel by viewModel<MyLeagueViewModel>()

    lateinit var myLeaguesAdapter: MyLeaguesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadAllLeagues()

        viewModel.leaguesResponse.observe(viewLifecycleOwner, {
            myLeaguesAdapter = MyLeaguesAdapter(it.data?.groupEldwry) { link ->
                context?.showLeaguesSettingsDialog(link, { dialog, link ->
                    val bundle = bundleOf(LINK_LEAGUE to link)
                    findNavController().navigate(R.id.action_MyLeagueFragment_to_StandingFragment,
                        bundle)
                    dialog?.dismiss()
                }, { dialog, link ->
                    dialog?.dismiss()
                }, { dialog, link ->
                    dialog?.dismiss()
                })
            }
            rv_parent.adapter = myLeaguesAdapter
        })
    }

    companion object {
        const val LINK_LEAGUE = "link_league"
    }

}