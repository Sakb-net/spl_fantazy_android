package com.sakb.spl.ui.powerfulleague

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.databinding.JoinNowDialogBinding
import com.sakb.spl.databinding.JoinToPowerfulLeagueFragmentBinding
import com.sakb.spl.ui.powerfulleague.adapter.PowerfulLeagueAdapter
import com.sakb.spl.ui.powerfulleague.adapter.PowerfulLeagueUIModel
import com.sakb.spl.utils.DividerItemDecorationNoLast
import org.koin.androidx.viewmodel.ext.android.viewModel


class JoinToPowerfulLeagueFragment : BaseFragment() {

    private lateinit var binding: JoinToPowerfulLeagueFragmentBinding
    override val viewModel by viewModel<JoinToPowerfulLeagueViewModel>()


    private val adapter by lazy {
        PowerfulLeagueAdapter().apply {
            onClickListener = { position, powerfulLeagueUIModel ->
                context?.joinNowDialog(R.drawable.ic_league_cup,
                    title = powerfulLeagueUIModel.name ?: "",
                    contentResID = R.string.content_join,
                    price = "ثمن الاشتراك : 50 ريال",
                    positive = { dialog -> dialog?.dismiss() },
                    negative = { dialog -> dialog?.dismiss() })
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.join_to_powerful_league_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.powerfulRecycler.addItemDecoration(
            DividerItemDecorationNoLast(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.powerfulRecycler.adapter = adapter
        binding.powerfulRecycler.itemAnimator = null

        // fake data
        val powerFulLeaguesList = mutableListOf<PowerfulLeagueUIModel>()
        repeat(25) {
            powerFulLeaguesList.add(
                PowerfulLeagueUIModel(
                    id = it.toString(),
                    name = "اسم الدوري",
                    order = it.toString()
                )
            )
        }
        adapter.submitList(powerFulLeaguesList)
//        binding.buttonSend.setOnClickListener {
//            //openConfirmationDialog()
//        }
    }

    private fun Context.joinNowDialog(
        @DrawableRes drawableRes: Int,
        title: String,
        contentResID: Int? = null,
        price: String? = null,
        positive: (dialog: AlertDialog?) -> Unit,
        negative: (dialog: AlertDialog?) -> Unit
    ) {
        val binding = JoinNowDialogBinding.inflate(LayoutInflater.from(this), null, false)
        val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(false)
        val dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding.dialogImage.setImageResource(drawableRes)
        binding.dialogTextTitle.text = title
        binding.dialogTextContent.text = contentResID?.let { this.getString(it) }
        binding.dialogTextPrice.text = contentResID?.let { price }

        binding.positiveButton.setOnClickListener { positive(dialog) }
        binding.negativeBtn.setOnClickListener { negative(dialog) }
        dialog.show()
    }

}
