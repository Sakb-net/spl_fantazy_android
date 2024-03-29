package com.sakb.spl.ui.myteam

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.MyteamPlayersResponse
import com.sakb.spl.databinding.FragmentMyTeamBinding
import com.sakb.spl.ui.myteam.adapter.MyTeamPlayersAdapter
import com.sakb.spl.ui.myteam.adapter.MyTeamSwapPlayersItemAdapter
import com.sakb.spl.ui.myteam.adapter.dialog.MyTeamSubstitutesAdapter
import com.sakb.spl.ui.myteam.adapter.menu.MyTeamPlayersMenuAdapter
import com.sakb.spl.ui.playerprofile.PlayerProfileActivity
import com.sakb.spl.utils.DividerItemDecorationNoLast
import com.sakb.spl.utils.showWarningDialog
import com.sakb.spl.utils.toast
import kotlinx.android.synthetic.main.dialog_spl_myteam_swap_view.view.*
import kotlinx.android.synthetic.main.dialog_spl_myteam_view.view.*
import kotlinx.android.synthetic.main.dialog_spl_substitutes_view.view.*
import kotlinx.android.synthetic.main.dialog_view_common.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MyTeamFragment : BaseFragment() {

    private lateinit var binding: FragmentMyTeamBinding

    private lateinit var adapter: MyTeamPlayersAdapter
    override val viewModel by viewModel<MyTeamViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyTeamBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.menu.setOnClickListener {
//            (activity as MainActivity).binding.drawerLayout.openDrawer(GravityCompat.START)
//        }
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyTeamViewModel::class.java)


        viewModel.loadMyTeamPlayers(

        )

        loadTeamPlayers()
        // load team statues
       /// loadTeamStatuesObserver()
        // change
        changePlayersStatuesObserver()
        // check available to swap
       /// availablePayerToSwapStatuesObserver()
        ///availablePayerToSwapResponseDataObserver()
        addCaptainOrViceCaptainObserver()
        openSubstitutesDialog()
    }

    private fun loadTeamPlayers() {
        viewModel.MyTeamPlayersListLiveData.observe(this, Observer { data ->
            var all_found = true
            data?.data?.forEach {
                it?.forEach {
                    if (it.found_player?:0<=0)
                        all_found = false
                }
            }
            if (all_found) {
                binding.emptyView.visibility = View.GONE
                binding.nestedContainer.visibility = View.VISIBLE
                binding.buttonsLinearLayout.visibility = View.VISIBLE
                initListeners(data)
                updateUi(data)
            }
            else {
                binding.emptyView.visibility = View.VISIBLE
                binding.nestedContainer.visibility = View.GONE
                binding.buttonsLinearLayout.visibility = View.GONE
                binding.buttonOpenChooseTeam.setOnClickListener {
                    findNavController().navigate(R.id.chooseTeamPlayersFragment)
                }
            }
        })
    }

    private fun initListeners(data: MyteamPlayersResponse) {
        binding.buttonBenchBoost.setOnClickListener {
            openButtonBoostDialog()
        }

        binding.buttonTrippleCaptain.setOnClickListener {
            openCaptainTripleSheet()
        }


        binding.menuBtn.setOnClickListener {
           // context?.toast("Reset...")
            viewModel.selectedPlayer = 0
            viewModel.resetActivePlayer()

            binding.menuBtn.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    context!!,
                    R.color.colorGreenDark
                )
            )

            binding.preview.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    context!!,
                    R.color.white
                )
            )

            binding.menuBtn.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.white
                )
            )

            binding.preview.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.black
                )
            )
            viewModel.isMenuPreviewEnabled = true

            binding.stadIv.setImageDrawable(null)
            binding.stadIv.setBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.white
                )
            )
            binding.swapBg.visibility = View.GONE
            binding.rvSwapList.adapter = null
            //binding.stadIv.visibility = View.INVISIBLE
            data.data?.let {
                val _adapter = MyTeamPlayersMenuAdapter(it).apply {
                    onItemClicked = { pos, parentPosition, player ->


                        // if is transparent
                        if (player.isSelected) {
                            context?.toast("Reset...")
                            viewModel.selectedPlayer = 0
                            viewModel.resetActivePlayer()
                        }
                        else if (player.alPha == 1.0f) {

                            if (viewModel.selectedPlayer == 1) {
                                context?.toast("change now from server then reset!")

                                context?.showDialog(getString(R.string.save_changes), { it ->
                                    it?.dismiss()
                                    viewModel.changePlayers(

                                        viewModel.playerLinkOne, "" + player.link_player
                                    )


                                }, { it ->
                                    it?.dismiss()
                                    //  findNavController().navigateUp()

                                })


                            }
                            else {
                                if (parentPosition==4)
                                    context?.showSplMyTeamSwapDialog({
                                        it?.dismiss()
                                        context?.toast("change $pos plz!")
                                        viewModel.selectedPlayer = 1
                                        viewModel.playerLinkOne = "" + player.link_player
                                        viewModel.loadAvailablePlayerToSwap(

                                            /*"" + player.link_player, */"" + player.type_loc_player,
                                            pos, parentPosition, false
                                        )
                                    },
                                        {

                                            it?.dismiss()
                                            startActivity(
                                                Intent(
                                                    context,
                                                    PlayerProfileActivity::class.java
                                                ).putExtra(
                                                    "link",
                                                    player.link_player
                                                )
                                            )
                                        })

                                else
                                    context?.showSplMyTeamDialog(
                                        changeBtn = {
                                            it?.dismiss()
                                            context?.toast("change $pos plz!")
                                            viewModel.selectedPlayer = 1
                                            viewModel.playerLinkOne = "" + player.link_player

                                            viewModel.loadAvailablePlayerToSwap(
                                                //"" + player.link_player,

                                                "" + player.type_loc_player,
                                                pos,
                                                parentPosition,
                                                true
                                            )
                                            /*   viewModel.checkAvailablePlayerToSwap(
                                                   "" + user?.data?.accessToken,
                                                   "" + player.link_player,
                                                   lang,
                                                   "" + player.type_loc_player,
                                                   pos,
                                                   parentPosition,
                                                   true
                                               )*/
                                        },
                                        addCaptainBtn = {
                                            it?.dismiss()

                                            viewModel.addCaptainOrViceCaptain(
                                                pos,
                                                parentPosition,
                                                "" + player.link_player,
                                                CAPTAIN
                                            )
                                        },
                                        addSecondCaptainBtn = {
                                            it?.dismiss()

                                            viewModel.addCaptainOrViceCaptain(
                                                pos,
                                                parentPosition,
                                                "" + player.link_player,
                                                ASSIST
                                            )

                                        },
                                        playerProfileBtn = {

                                            it?.dismiss()
                                            startActivity(
                                                Intent(
                                                    context,
                                                    PlayerProfileActivity::class.java
                                                ).putExtra(
                                                    "link",
                                                    player.link_player
                                                )
                                            )
                                        })
                            }


                        }



                    }


                }
                binding.rvParent.setAdapter(_adapter)

            }
        }


        binding.preview.setOnClickListener {
            context?.toast("Reset...")
            viewModel.selectedPlayer = 0
            viewModel.resetActivePlayer()

            binding.preview.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    context!!,
                    R.color.colorGreenDark
                )
            )

            binding.menuBtn.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    context!!,
                    R.color.white
                )
            )

            binding.preview.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.white
                )
            )

            binding.menuBtn.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.black
                )
            )
            viewModel.isMenuPreviewEnabled = false

            binding.stadIv.setImageResource(R.drawable.pitch)

            data.data?.let {
                adapter = MyTeamPlayersAdapter(it).apply {
                    onItemClicked = { pos, parentPosition, player ->


                        // if is transparent
                        if (player.isSelected) {
                            context?.toast("Reset...")
                            viewModel.selectedPlayer = 0
                            viewModel.resetActivePlayer()
                        } else if (player.alPha == 1.0f) {

                            if (viewModel.selectedPlayer == 1) {
                                context?.toast("change now from server then reset!")

                                context?.showDialog(getString(R.string.save_changes), { it ->
                                    it?.dismiss()
                                    viewModel.changePlayers(

                                        viewModel.playerLinkOne, "" + player.link_player
                                    )


                                }, { it ->
                                    it?.dismiss()
                                    //  findNavController().navigateUp()

                                })


                            } else {
                                context?.showSplMyTeamDialog(
                                    changeBtn = {
                                        it?.dismiss()
                                        context?.toast("change $pos plz!")
                                        viewModel.selectedPlayer = 1
                                        viewModel.playerLinkOne = "" + player.link_player
                                        viewModel.checkAvailablePlayerToSwap(
                                            //"" + player.link_player,

                                            "" + player.type_loc_player,
                                            pos,
                                            parentPosition,
                                            true
                                        )
                                    },
                                    addCaptainBtn = {
                                        it?.dismiss()

                                        viewModel.addCaptainOrViceCaptain(
                                            pos,
                                            parentPosition,
                                            "" + player.link_player,
                                            Companion.CAPTAIN
                                        )
                                    },
                                    addSecondCaptainBtn = {
                                        it?.dismiss()

                                        viewModel.addCaptainOrViceCaptain(
                                            pos,
                                            parentPosition,
                                            "" + player.link_player,
                                            Companion.ASSIST
                                        )

                                    },
                                    playerProfileBtn = {

                                        it?.dismiss()
                                        startActivity(
                                            Intent(
                                                context,
                                                PlayerProfileActivity::class.java
                                            ).putExtra(
                                                "link",
                                                player.link_player
                                            )
                                        )
                                    })
                            }


                        }


                    }

                }
                binding.rvParent.setAdapter(adapter)

                binding.swapBg.visibility = View.VISIBLE
                binding.rvSwapList.adapter = MyTeamSwapPlayersItemAdapter(
                    it[4],
                    4,
                    onItemClicked = { childPos, parentPosition, data ->


                        // if is transparent
                        if (data.isSelected) {
                            context?.toast("Reset...")
                            viewModel.selectedPlayer = 0
                            viewModel.resetActivePlayer()
                        } else if (data.alPha == 1.0f) {
                            if (viewModel.selectedPlayer == 1) {
                                context?.toast("change now from server then reset!")
                                context?.showDialog(getString(R.string.save_changes), { it ->
                                    it?.dismiss()
                                    viewModel.changePlayers(
                                        viewModel.playerLinkOne, "" + data.link_player
                                    )


                                }, { it ->
                                    it?.dismiss()
                                    //  findNavController().navigateUp()

                                })


                            } else {
                                context?.showSplMyTeamSwapDialog({
                                    it?.dismiss()
                                    context?.toast("change $childPos plz!")
                                    viewModel.selectedPlayer = 1
                                    viewModel.playerLinkOne = "" + data.link_player
                                    viewModel.checkAvailablePlayerToSwap(
                                        //"" + data.link_player,
                                        "" + data.type_loc_player,
                                        childPos, parentPosition, false
                                    )
                                }, {

                                    it?.dismiss()
                                    startActivity(
                                        Intent(
                                            context,
                                            PlayerProfileActivity::class.java
                                        ).putExtra(
                                            "link",
                                            data.link_player
                                        )
                                    )
                                })
                            }
                        }


                    }
                )
            }
        }
    }

    private fun openCaptainTripleSheet() {
        context?.showWarningDialog(R.drawable.xtripple, R.string.tripple_captain_card, R.string.triple_content,{
                dialog ->  dialog?.dismiss()
        },{
                dialog ->  dialog?.dismiss()
        })
    }

    private fun openButtonBoostDialog() {

        context?.showWarningDialog(R.drawable.rocket, R.string.benchboost_text_view, R.string.boost_content,{
            dialog ->  dialog?.dismiss()
        },{
            dialog ->  dialog?.dismiss()
        })
    }

    private fun updateUi(data: MyteamPlayersResponse) {
        binding.rvParent.isNestedScrollingEnabled = false
        if (viewModel.isMenuPreviewEnabled) {
            binding.menuBtn.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    context!!,
                    R.color.colorGreenDark
                )
            )

            binding.preview.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    context!!,
                    R.color.white
                )
            )

            binding.menuBtn.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.white
                )
            )

            binding.preview.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.black
                )
            )

            binding.stadIv.setImageDrawable(null)
            binding.stadIv.setBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.white
                )
            )
            binding.swapBg.visibility = View.GONE
            binding.rvSwapList.adapter = null
            //binding.stadIv.visibility = View.INVISIBLE
            data.data?.let {
                val _adapter = MyTeamPlayersMenuAdapter(it).apply {
                    onItemClicked = { pos, parentPosition, player ->


                        // if is transparent
                        if (player.isSelected) {
                           // context?.toast("Reset...")
                            viewModel.selectedPlayer = 0
                            viewModel.resetActivePlayer()
                        } else if (player.alPha == 1.0f) {

                            if (viewModel.selectedPlayer == 1) {
                                context?.toast("change now from server then reset!")

                                context?.showDialog(getString(R.string.save_changes), { it ->
                                    it?.dismiss()
                                    viewModel.changePlayers(
                                        viewModel.playerLinkOne, "" + player.link_player
                                    )


                                }, { it ->
                                    it?.dismiss()
                                    //  findNavController().navigateUp()

                                })


                            }
                            else {


                                if (parentPosition!=4)
                                    context?.showSplMyTeamDialog(
                                        changeBtn = {
                                            it?.dismiss()
                                            context?.toast("change $pos plz!")
                                            viewModel.selectedPlayer = 1
                                            viewModel.playerLinkOne = "" + player.link_player
                                            viewModel.loadAvailablePlayerToSwap(
                                                //"" + player.link_player,
                                                "" + player.type_loc_player,
                                                pos,
                                                parentPosition,
                                                true
                                            )
                                        },
                                        addCaptainBtn = {
                                            it?.dismiss()

                                            viewModel.addCaptainOrViceCaptain(
                                                pos,
                                                parentPosition,
                                                "" + player.link_player,
                                                CAPTAIN
                                            )
                                        },
                                        addSecondCaptainBtn = {
                                            it?.dismiss()

                                            viewModel.addCaptainOrViceCaptain(
                                                pos,
                                                parentPosition,
                                                "" + player.link_player,
                                                ASSIST
                                            )

                                        },
                                        playerProfileBtn = {

                                            it?.dismiss()
                                            startActivity(
                                                Intent(
                                                    context,
                                                    PlayerProfileActivity::class.java
                                                ).putExtra(
                                                    "link",
                                                    player.link_player
                                                )
                                            )
                                        })

                                else
                                    context?.showSplMyTeamSwapDialog({
                                        it?.dismiss()
                                        context?.toast("change $pos plz!")
                                        viewModel.selectedPlayer = 1
                                        viewModel.playerLinkOne = "" + player.link_player


                                        viewModel.loadAvailablePlayerToSwap(
                                            //"" + player.link_player,
                                            "" + player.type_loc_player,
                                            pos, parentPosition, false
                                        )
                                    },
                                        {

                                            it?.dismiss()
                                            startActivity(
                                                Intent(
                                                    context,
                                                    PlayerProfileActivity::class.java
                                                ).putExtra(
                                                    "link",
                                                    player.link_player
                                                )
                                            )
                                        })
                            }


                        }


                    }


                }
                binding.rvParent.setAdapter(_adapter)

            }
        }
        else {
            binding.stadIv.setImageResource(R.drawable.pitch)
            data.data?.let {
                adapter = MyTeamPlayersAdapter(it).apply {
                    onItemClicked = { pos, parentPosition, player ->


                        // if is transparent
                        if (player.isSelected) {
                            context?.toast("Reset...")
                            viewModel.selectedPlayer = 0
                            viewModel.resetActivePlayer()
                        } else if (player.alPha == 1.0f) {

                            if (viewModel.selectedPlayer == 1) {
                                context?.toast("change now from server then reset!")

                                context?.showDialog(getString(R.string.save_changes), { it ->
                                    it?.dismiss()
                                    viewModel.changePlayers(
                                        viewModel.playerLinkOne, "" + player.link_player
                                    )


                                }, { it ->
                                    it?.dismiss()
                                    //  findNavController().navigateUp()

                                })


                            } else {
                                context?.showSplMyTeamDialog(
                                    changeBtn = {
                                        it?.dismiss()
                                        context?.toast("change $pos plz!")
                                        viewModel.selectedPlayer = 1
                                        viewModel.playerLinkOne = "" + player.link_player
                                        viewModel.checkAvailablePlayerToSwap(
                                            //"" + player.link_player,
                                            "" + player.type_loc_player,
                                            pos,
                                            parentPosition,
                                            true
                                        )
                                    },
                                    addCaptainBtn = {
                                        it?.dismiss()

                                        viewModel.addCaptainOrViceCaptain(
                                            pos,
                                            parentPosition,
                                            "" + player.link_player,
                                            Companion.CAPTAIN
                                        )
                                    },
                                    addSecondCaptainBtn = {
                                        it?.dismiss()

                                        viewModel.addCaptainOrViceCaptain(
                                            pos,
                                            parentPosition,
                                            "" + player.link_player,
                                            Companion.ASSIST
                                        )

                                    },
                                    playerProfileBtn = {

                                        it?.dismiss()
                                        startActivity(
                                            Intent(
                                                context,
                                                PlayerProfileActivity::class.java
                                            ).putExtra(
                                                "link",
                                                player.link_player
                                            )
                                        )
                                    })
                            }


                        }


                    }

                }
                binding.rvParent.setAdapter(adapter)

                binding.swapBg.visibility = View.VISIBLE
                binding.rvSwapList.adapter = MyTeamSwapPlayersItemAdapter(
                    it[4],
                    4,
                    onItemClicked = { childPos, parentPosition, data ->


                        // if is transparent
                        if (data.isSelected) {
                            context?.toast("Reset...")
                            viewModel.selectedPlayer = 0
                            viewModel.resetActivePlayer()
                        } else if (data.alPha == 1.0f) {
                            if (viewModel.selectedPlayer == 1) {
                                context?.toast("change now from server then reset!")
                                context?.showDialog(getString(R.string.save_changes), { it ->
                                    it?.dismiss()
                                    viewModel.changePlayers(

                                        viewModel.playerLinkOne, "" + data.link_player
                                    )


                                }, { it ->
                                    it?.dismiss()
                                    //  findNavController().navigateUp()

                                })


                            } else {
                                context?.showSplMyTeamSwapDialog({
                                    it?.dismiss()
                                 //   context?.toast("change $childPos plz!")
                                    viewModel.selectedPlayer = 1
                                    viewModel.playerLinkOne = "" + data.link_player
                                    viewModel.checkAvailablePlayerToSwap(
                                        //"" + data.link_player,
                                        "" + data.type_loc_player,
                                        childPos, parentPosition, false
                                    )
                                },
                                    {

                                        it?.dismiss()
                                        startActivity(
                                            Intent(
                                                context,
                                                PlayerProfileActivity::class.java
                                            ).putExtra(
                                                "link",
                                                data.link_player
                                            )
                                        )
                                    })
                            }
                        }


                    }
                )
            }
        }
    }

 /*   private fun loadTeamStatuesObserver() {
        viewModel.MyTeamPlayerResultLiveData.observe(this, Observer<Event<NetworkState>> {
            it.getContentIfNotHandled()?.let {
                // Only proceed if the event has never been handled
                when (it.status) {
                    Status.RUNNING -> (context as MainActivity).showProgress()
                    Status.SUCCESS -> {
                        (context as MainActivity).hideProgress()
                    }
                    Status.FAILED -> {
                        (context as MainActivity).hideProgress()
                        context?.toast("" + it.message)
                    }

                }
            }
        })
    }*/


//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }

    private fun openSubstitutesDialog(){
        viewModel.substitutesList.observe(this, Observer  {


                Timber.e("data ----------"+it.toString())
                context?.showSplSubstitutesDialog(it,dismissed = {
                    it?.dismiss()
                    context?.toast("Reset...")
                    viewModel.selectedPlayer = 0
                    viewModel.resetActivePlayer()
                },
                    selectedPlayer = {dialog, _, player ->
                        dialog?.dismiss()

//                        context?.toast("change now from server then reset!")

                        context?.showDialog(getString(R.string.save_changes), { it ->
                            it?.dismiss()
                            viewModel.changePlayers(
                                viewModel.playerLinkOne, "" + player.link_player
                            )


                        }, { it ->
                            it?.dismiss()
                            //  findNavController().navigateUp()

                        })

                    }
                )

        })
    }

    private fun changePlayersStatuesObserver() {
        viewModel.validateChangeResultStateLiveData.observe(this, Observer {data->

                        viewModel.selectedPlayer = 0
                    //    val data = it.message as AddDirectInsideChange
                        data.msgAdd?.let {
                            context?.toast(it)
                        }
                        //(context as MainActivity).hideProgress()




        })
    }

/*    private fun availablePayerToSwapStatuesObserver() {
        viewModel.checkAvailablePlayerToSwapStatusLiveData.observe(
            this,
            Observer<Event<NetworkState>> {
                it.getContentIfNotHandled()?.let {
                    // Only proceed if the event has never been handled
                    when (it.status) {
                        Status.RUNNING -> (context as MainActivity).showProgress()
                        Status.SUCCESS -> {
                            (context as MainActivity).hideProgress()
                        }
                        Status.FAILED -> {
                            (context as MainActivity).hideProgress()
                            context?.toast("" + it.message)
                        }

                    }
                }
            })
    }*/

    private fun addCaptainOrViceCaptainObserver() {
        viewModel.addCaptainOrViseState.observe(
            this, Observer {data->




                            context?.toast("" + data.data?.msgAdd)





            })
    }

  /*  private fun availablePayerToSwapResponseDataObserver() {
        viewModel.checkAvailablePlayerToSwapData.observe(this, Observer { data ->

            data.data?.let {
                Timber.e("=======$${it.allHide}")
                //  viewModel.updateActivePlayerForChange(childPos, parentPos, data)
            }

        })
    }*/


    private fun Context.showSplSubstitutesDialog(
        data :  List<MyteamPlayersResponse.Player>
        /*  parentPositions: Int = -1,
          onItemClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null,
          onChangeClick: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null,
          onOpenProfileClicked: ((pos: Int, MyteamPlayersResponse.Player) -> Unit)? = null,
          onResetClicked : ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)?,
          onRestorePlayerClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null*/
        //,
        // changeBtn: (dialog: AlertDialog?) -> Unit,
        , dismissed: (dialog: AlertDialog?) -> Unit
        ,selectedPlayer: (dialog: AlertDialog?, pos : Int, player : MyteamPlayersResponse.Player) -> Unit

    ) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_spl_substitutes_view, null)
        val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(true)
        val dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.substitutesRv.addItemDecoration(
            DividerItemDecorationNoLast(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        var isHandled = false
        view.substitutesRv.adapter = MyTeamSubstitutesAdapter(data/*,-1parentPositions, onItemClicked, onChangeClick, onOpenProfileClicked, onResetClicked,onRestorePlayerClicked*/).apply {

            onItemClicked = {pos, player ->
                isHandled = true
                selectedPlayer(dialog,pos,player)
            }
        }

        // view.changePlayerSwapBtn.setOnClickListener { changeBtn(dialog) }
        // view.playerProfileSwapBtn.setOnClickListener { playerProfileBtn(dialog) }

        dialog?.setOnDismissListener {
            if (!isHandled)
                dismissed(dialog)
        }
        dialog.show()
    }


    private fun Context.showDialog(
        resID: String,
        positive: (dialog: AlertDialog?) -> Unit,
        negative: (dialog: AlertDialog?) -> Unit
    ) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_view_common, null)
        val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(false)
        val dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        // view.dialogIcon.setImageResource(drawableRes)
        view.dialogCText.text = resID
        view.positiveCBtn.setOnClickListener { positive(dialog) }
        //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
        view.negativeCButton.setOnClickListener { negative(dialog) }
        dialog.show()
    }


    private fun Context.showSplMyTeamDialog(
        changeBtn: (dialog: AlertDialog?) -> Unit,
        addCaptainBtn: (dialog: AlertDialog?) -> Unit,
        addSecondCaptainBtn: (dialog: AlertDialog?) -> Unit,
        playerProfileBtn: (dialog: AlertDialog?) -> Unit

    ) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_spl_myteam_view, null)
        val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(true)
        val dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.changePlayerBtn.setOnClickListener { changeBtn(dialog) }
        view.addCaptainBtn.setOnClickListener { addCaptainBtn(dialog) }
        view.secondCaptainBtn.setOnClickListener { addSecondCaptainBtn(dialog) }
        view.playerProfileBtn.setOnClickListener { playerProfileBtn(dialog) }
        dialog.show()
    }


    private fun Context.showSplMyTeamSwapDialog(
        changeBtn: (dialog: AlertDialog?) -> Unit,
        playerProfileBtn: (dialog: AlertDialog?) -> Unit

    ) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_spl_myteam_swap_view, null)
        val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(true)
        val dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.changePlayerSwapBtn.setOnClickListener { changeBtn(dialog) }
        view.playerProfileSwapBtn.setOnClickListener { playerProfileBtn(dialog) }
        dialog.show()
    }

    companion object {
        const val ASSIST: String = "assist"
        const val CAPTAIN: String = "captain"
    }


}