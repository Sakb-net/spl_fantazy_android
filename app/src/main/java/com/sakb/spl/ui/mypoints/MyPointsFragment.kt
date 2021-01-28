package com.sakb.spl.ui.mypoints

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.data.model.DataItemPoints
import com.sakb.spl.data.model.GetPointSubeldawryResponse
import com.sakb.spl.databinding.FragmentMyPointsBinding
import com.sakb.spl.ui.mypoints.adapters.MyPointSwapPlayersItemAdapter
import com.sakb.spl.ui.mypoints.adapters.MyPointsPlayersAdapter
import com.sakb.spl.ui.mypoints.menu.MyPointPlayersMenuAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyPointsFragment : BaseFragment() {
    private lateinit var binding: FragmentMyPointsBinding
    override val viewModel by viewModel<MyPointsViewModel>()

    private lateinit var adapter: MyPointsPlayersAdapter

    var option = mutableListOf<DataItemPoints>()
    var builder: AlertDialog.Builder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadPointsEldwry()
        viewModel.pointsEldwry.observe(viewLifecycleOwner, {
            it?.data?.let { list ->
                option = list.filterNotNull() as MutableList<DataItemPoints>
            }
            builder = AlertDialog.Builder(requireContext(), R.style.MaterialThemeDialog)
            initDialogRounds()
        })

        binding.bannerTextView.setOnClickListener {
            builder?.show()
        }

        viewModel.pointsSubeldwry.observe(viewLifecycleOwner, {
            initListeners(it)
            updateUi(it)
        })
    }

    private fun initListeners(getPointSubeldawryResponse: GetPointSubeldawryResponse?) {
        binding.menuBtn.setOnClickListener {
            binding.menuBtn.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                R.color.colorGreenDark
            )

            binding.preview.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                R.color.white
            )

            binding.menuBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )

            binding.preview.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            viewModel.isMenuPreviewEnabled = true
            binding.stadIv.setImageDrawable(null)
            binding.stadIv.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.swapBg.visibility = View.GONE
            binding.rvSwapList.adapter = null
            getPointSubeldawryResponse?.playerMaster?.let {
                val _adapter = MyPointPlayersMenuAdapter(it)
                binding.rvParent.adapter = _adapter
            }
        }
        binding.preview.setOnClickListener {
            binding.preview.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                R.color.colorGreenDark
            )

            binding.menuBtn.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                R.color.white
            )

            binding.preview.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )

            binding.menuBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            viewModel.isMenuPreviewEnabled = false

            binding.stadIv.setImageResource(R.drawable.pitch)

            adapter = MyPointsPlayersAdapter(getPointSubeldawryResponse?.playerMaster)
            binding.rvParent.adapter = adapter

            binding.swapBg.visibility = View.VISIBLE
            binding.rvSwapList.adapter =
                getPointSubeldawryResponse?.playerMaster?.get(4)?.let { listInside ->
                    MyPointSwapPlayersItemAdapter(
                        listInside,
                        4)
                }
        }
    }

    private fun updateUi(getPointSubeldawryResponse: GetPointSubeldawryResponse) {
        if (getPointSubeldawryResponse.playerMaster?.isNullOrEmpty() == true) {
            binding.buttonsLinearLayout.visibility = View.GONE
            binding.nestedContainer.visibility = View.GONE
        } else {
            binding.buttonsLinearLayout.visibility = View.VISIBLE
            binding.nestedContainer.visibility = View.VISIBLE
            getPointSubeldawryResponse.data?.let {
                if (it.benchCard == 0) {
                    binding.cardTv.visibility = View.GONE
                } else {
                    binding.cardTv.visibility = View.VISIBLE
                    binding.cardTv.text = getString(R.string.benchboost_text_view)
                }
                if (it.tripleCard == 0) {
                    binding.cardTv.visibility = View.GONE
                } else {
                    binding.cardTv.visibility = View.VISIBLE
                    binding.cardTv.text = getString(R.string.tripple_captain_card)
                }
            }
            getPointSubeldawryResponse.playerMaster.let {
                if (viewModel.isMenuPreviewEnabled) {
                    binding.menuBtn.backgroundTintList = ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.colorGreenDark
                    )

                    binding.preview.backgroundTintList = ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.white
                    )

                    binding.menuBtn.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )

                    binding.preview.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )

                    binding.stadIv.setImageDrawable(null)
                    binding.stadIv.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                    binding.swapBg.visibility = View.GONE
                    binding.rvSwapList.adapter = null
                    //binding.stadIv.visibility = View.INVISIBLE
                    it.let {
                        val _adapter = MyPointPlayersMenuAdapter(it)
                        binding.rvParent.adapter = _adapter
                    }
                } else {
                    binding.stadIv.setImageResource(R.drawable.pitch)

                    it.let {
                        adapter = MyPointsPlayersAdapter(it)
                    }
                    binding.rvParent.adapter = adapter

                    binding.swapBg.visibility = View.VISIBLE
                    binding.rvSwapList.adapter = MyPointSwapPlayersItemAdapter(
                        it[4],
                        4)
                }
            }
        }
    }

    private fun updateUIPoints(item: DataItemPoints) {
        binding.totalPointsText.text = item.finalPoint.toString()
        binding.avargeTv.text = item.avgPoint.toString()
        binding.heighestTv.text = item.heighPoint.toString()
        binding.rankTv.text = item.sortGwla.toString()
        binding.transferTv.text = item.transfer.toString()
    }

    private fun initDialogRounds() {
        val options = option.map {
            it.let {
                it.langNumWeek?.trim()
            }
        }.toTypedArray()

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter<CharSequence>(
            requireContext(), R.layout.item_check_list, options
        )

        var selectedItem: Int
        builder?.setTitle(getString(R.string.select_round))
        builder?.setSingleChoiceItems(
            adapter, -1
        ) { dialogInterface: DialogInterface, item: Int ->
            selectedItem = item
            binding.bannerTextView.text = options[selectedItem]
            updateUIPoints(option[selectedItem])
            option[selectedItem].link?.let { link ->
                callPlayerSubeldawry(link)
            }
            dialogInterface.dismiss()
        }

        binding.bannerTextView.text = options[0]
        updateUIPoints(option[0])
        option[0].link?.let { link ->
            callPlayerSubeldawry(link)
        }
        builder?.create()
    }

    fun callPlayerSubeldawry(link: String) {
        viewModel.loadPointsSubeldwry(link)
    }
}
