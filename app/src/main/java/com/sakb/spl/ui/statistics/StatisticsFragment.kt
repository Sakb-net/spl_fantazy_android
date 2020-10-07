package com.sakb.spl.ui.statistics


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.Club
import com.sakb.spl.utils.DividerItemDecoration
import com.sakb.spl.utils.FixedGridLayoutManager
import kotlinx.android.synthetic.main.fragment_statisitcs.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatisticsFragment : BaseFragment() {

    override val viewModel by viewModel<StatisticsViewModel>()


    internal var scrollX = 0

    internal var clubList: MutableList<Club> = ArrayList<Club>()


    lateinit var clubAdapter: ClubAdapter


    //  private  var _binding: FragmentStatisitcsBinding?=null
//    private  val binding = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //_binding = FragmentStatisitcsBinding.inflate(inflater, container, false)
        //return binding.root
        return inflater.inflate(R.layout.fragment_statisitcs, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //  changeViewsFonts()
        initListener()


        prepareClubData()

        setUpRecyclerView()

        rvClub.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                scrollX += dx

                headerScroll.scrollTo(scrollX, 0)
            }

        })
    }

    private fun initListener() {
//        binding.menu.setOnClickListener {
//            (activity as MainActivity).binding.drawerLayout.openDrawer(GravityCompat.START)
//        }
    }

    private fun changeViewsFonts() {
//        Util.changeViewTypeFace(
//            context,
//            Constants.FONT_REGULAR,
//            binding.toolbarTitle
//            /*, binding.navView.getHeaderView(0).UserName_tv*/
//        )

    }


    /**
     * Prepares dummy data
     */
    private fun prepareClubData() {
        clubList.add(
            Club(
                "Galatasaray",
                "https://tmssl.akamaized.net/images/wappen/head/141.png",
                "Istanbul, Turkey",
                "Ali Sami Yen",
                "Süper Lig",
                "Fatih Terim",
                "Bafetimbi Gomis"
            )
        )
        clubList.add(
            Club(
                "Real Madrid",
                "https://tmssl.akamaized.net//images/wappen/head/418.png",
                "Madrid, Spain",
                "Santiago Barnabeu",
                "La Liga",
                "Zidane",
                "Cristiano Ronaldo"
            )
        )
        clubList.add(
            Club(
                "Barcelona",
                "https://tmssl.akamaized.net//images/wappen/head/131.png",
                "Barcelona, Spain",
                "Camp Nou",
                "La Liga",
                "Ernesto Valverde",
                "Lionel Messi"
            )
        )
        clubList.add(
            Club(
                "Bayern München",
                "https://tmssl.akamaized.net//images/wappen/head/27.png",
                "München, Germany",
                "Allianz Arena",
                "Bundesliga",
                "Jupp Heynckes",
                "Robert Lewandowski"
            )
        )
        clubList.add(
            Club(
                "Manchester United",
                "https://tmssl.akamaized.net//images/wappen/head/985.png",
                "Manchester, England",
                "Old Trafford",
                "Premier League",
                "Jose Mourinho",
                "Paul Pogba"
            )
        )
        clubList.add(
            Club(
                "Manchester City",
                "https://tmssl.akamaized.net//images/wappen/head/281.png",
                "Manchester, England",
                " Etihad Stadium",
                "Premier League",
                "Pep Guardiola",
                "Kevin de Bruyne"
            )
        )
        clubList.add(
            Club(
                "Atletico Madrid",
                "https://tmssl.akamaized.net//images/wappen/head/13.png",
                "Madrid, Spain",
                "Estadio Metropolitano de Madrid ",
                "La Liga",
                "Diego Simeone",
                "Antoine Griezmann"
            )
        )
        clubList.add(
            Club(
                "Liverpool",
                "https://tmssl.akamaized.net//images/wappen/head/31.png",
                "Liverpool, Spain",
                "Anfield",
                "Premier League",
                "Klopp",
                "Mo Salah"
            )
        )
        clubList.add(
            Club(
                "Juventus",
                "https://tmssl.akamaized.net//images/wappen/head/506.png",
                "Turin, Italy",
                "Allianz Stadium",
                "Serie A",
                "Massimiliano Allegri",
                "Paulo Dybala"
            )
        )
        clubList.add(
            Club(
                "Arsenal",
                "https://tmssl.akamaized.net//images/wappen/head/11.png",
                "London, England",
                "Emirates Stadium",
                "Premier League",
                "Arsene Wenger",
                "Mesut Özil"
            )
        )
        clubList.add(
            Club(
                "Roma",
                "https://tmssl.akamaized.net//images/wappen/head/12.png",
                "Rome, Italy",
                " Olimpico di Roma",
                "Serie A",
                "Eusebio Di Francesco",
                "Cengiz Ünder"
            )
        )
        clubList.add(
            Club(
                "PSG",
                "https://tmssl.akamaized.net//images/wappen/head/583.png",
                "Paris, France",
                "Parc des Princes ",
                "Ligue 1",
                "Unai Emery",
                "Neymar"
            )
        )
        clubList.add(
            Club(
                "Chelsea",
                "https://tmssl.akamaized.net//images/wappen/head/631.png",
                "London, England",
                "Stamford Bridge",
                "Premier League",
                "Conte",
                "Eden Hazard"
            )
        )
        clubList.add(
            Club(
                "Tottenham",
                "https://tmssl.akamaized.net//images/wappen/head/148.png",
                "London, England",
                "Wembley Stadium ",
                "Premier League",
                "Mauricio Pochettino",
                "Harry Kane"
            )
        )
    }

    /**
     * Handles RecyclerView for the action
     */

    private fun setUpRecyclerView() {
        clubAdapter = ClubAdapter(context, clubList)

        val manager = FixedGridLayoutManager()
        manager.setTotalColumnCount(1)
        rvClub.layoutManager = manager
        rvClub.adapter = clubAdapter
        rvClub.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )


    }

/*
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/

}
