package com.sakb.spl.ui.matches

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.youtube.player.internal.i
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.DataItemSubFix
import com.sakb.spl.databinding.FragmentMatchDetailsBinding
import kotlinx.android.synthetic.main.fragment_match_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentMatchDetailsBinding
    override val viewModel by viewModel<MatchesViewModel>()


    val matchDetails by lazy {
        arguments?.getParcelable<DataItemSubFix>(MATCH_DETAILS_OBJECT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", matchDetails.toString())
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.team1Tv.text = matchDetails?.nameFirst
        binding.team2Tv.text = matchDetails?.nameSecond
        Glide.with(binding.team1Iv).load(Constants.baseUrl + matchDetails?.imageFirst)
            .placeholder(R.drawable.placeholder).error(R.drawable.placeholder).override(150)
            .into(binding.team1Iv)
        Glide.with(binding.team2Iv).load(Constants.baseUrl + matchDetails?.imageSecond)
            .placeholder(R.drawable.placeholder).error(R.drawable.placeholder).override(150)
            .into(binding.team2Iv)
        binding.dateResultTeamOne.text = matchDetails?.firstGoon
        binding.dateResultTeamTwo.text = matchDetails?.secondGoon
        matchDetails?.details?.let { details->
            details.goals?.let {goals->
                if(goals.firstTeam?.isNullOrEmpty()== true && goals.secondTeam?.isNullOrEmpty()== true){
                    binding.goalsTv.visibility =View.GONE
                    binding.llGoals.visibility = View.GONE
                }else{
                    binding.goalsTv.visibility =View.VISIBLE
                    binding.llGoals.visibility = View.VISIBLE
                }
                goals.firstTeam?.let { firstTeam->
                    var txt =""
                    firstTeam.forEach {goal->
                        txt = txt + goal?.playerName?.plus(" (").plus(goal?.value).plus(")") +"\n"
                    }
                    binding.goalsTv1.text = txt.trim()
                }
                goals.secondTeam?.let { secondTeam->

                    var txt =""
                    secondTeam.forEach {goal->
                        txt = txt + goal?.playerName?.plus(" (").plus(goal?.value).plus(")") +"\n"
                    }
                    binding.goalsTv2.text = txt.trim()
                }
            }
            details.goalAssist?.let {assist->
                if(assist.firstTeam?.isNullOrEmpty()== true && assist.secondTeam?.isNullOrEmpty()== true){
                    binding.assistTv.visibility =View.GONE
                    binding.llAssist.visibility = View.GONE
                }else{
                    binding.assistTv.visibility =View.VISIBLE
                    binding.llAssist.visibility = View.VISIBLE
                }
                assist.firstTeam?.let { firstTeam->
                    var txt =""
                    firstTeam.forEach {goal->
                        txt = txt + goal?.playerName?.plus(" (").plus(goal?.value).plus(")") +"\n"
                    }
                    binding.assistTv1.text = txt.trim()
                }
                assist.secondTeam?.let { secondTeam->
                    var txt =""
                    secondTeam.forEach {goal->
                        txt = txt + goal?.playerName?.plus(" (").plus(goal?.value).plus(")") +"\n"
                    }
                    binding.assistTv2.text = txt.trim()
                }
            }
            details.goalsConceded?.let {conceded->
                if(conceded.firstTeam?.isNullOrEmpty()== true && conceded.secondTeam?.isNullOrEmpty()== true){
                    binding.concededTv.visibility =View.GONE
                    binding.llConceded.visibility = View.GONE
                }else{
                    binding.concededTv.visibility =View.VISIBLE
                    binding.llConceded.visibility = View.VISIBLE
                }
                conceded.firstTeam?.let { firstTeam->
                    var txt =""
                    firstTeam.forEach {goal->
                        txt = txt + goal?.playerName?.plus(" (").plus(goal?.value).plus(")") +"\n"
                    }
                    binding.concededTv1.text = txt.trim()
                }
                conceded.secondTeam?.let { secondTeam->
                    var txt =""
                    secondTeam.forEach {goal->
                        txt = txt + goal?.playerName?.plus(" (").plus(goal?.value).plus(")") +"\n"
                    }
                    binding.concededTv2.text = txt.trim()
                }
            }
        }


    }
    companion object {
        val MATCH_DETAILS_OBJECT="match_details_object"
    }
}