package com.sakb.spl.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings
import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.provider.Connect
import com.oppwa.mobile.connect.provider.Transaction
import com.oppwa.mobile.connect.provider.TransactionType
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.ui.transfers.TransfersActionsFragment.Companion.CHECKOUT_ID
import com.sakb.spl.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    override val viewModel by viewModel<MainViewModel>()

    private var drawerLayout: DrawerLayout? = null

    private val user by lazy {
        PrefManager.getUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.myTeamFragment,
                R.id.videosFragment,
                R.id.newsFragment,
                R.id.statisticsFragment,
                R.id.myPointsFragment,
                R.id.transfersFragment,
                R.id.helpFragment,
                R.id.howToPlayFragment,
                R.id.contactUsFragment,
                R.id.logoutFragment,
                R.id.myProfileFragment,
                R.id.awardFragment,
                R.id.languageFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val header = navView.getHeaderView(0)
        header.setOnClickListener {
            if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
                drawerLayout?.closeDrawer(GravityCompat.START)
            }
            navController.navigate(R.id.myProfileFragment)
        }
        val profileImage = header.findViewById<ImageView>(R.id.profile_image)
        val profileName = header.findViewById<TextView>(R.id.profile_name)
        profileName.text = user?.data?.displayName
        Glide.with(profileImage.context)
            .load(Constants.baseUrl + user?.data?.image)
            .circleCrop()
            .into(profileImage)

//        navView.setNavigationItemSelectedListener(this)
        viewModel.getCardGoldResultResponse.observe(this, {
            toast(it.data?.mesagePay ?: "")
            findNavController(R.id.nav_host_fragment).popBackStack(R.id.transfersActionFragment,
                true)
            //supportFragmentManager.popBackStack()
        })
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            CheckoutActivity.RESULT_OK -> {
                /* transaction completed */
                val transaction: Transaction? =
                    data?.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_TRANSACTION)
                /* resource path if needed */
                val resourcePath =
                    data?.getStringExtra(CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH)
                val checkoutId = CHECKOUT_ID
                if (transaction?.transactionType === TransactionType.SYNC) {
                    /* check the result of synchronous transaction */
                    viewModel.confirmGoldInfo(resourcePath ?: "", checkoutId ?: "")
                } else {
                    /* wait for the asynchronous transaction callback in the onNewIntent() */
                    val paymentBrands: MutableSet<String> = LinkedHashSet()

                    paymentBrands.add("VISA")
                    paymentBrands.add("MASTER")
                    CHECKOUT_ID = checkoutId
                    val checkoutSettings =
                        CheckoutSettings(
                            CHECKOUT_ID,
                            paymentBrands,
                            Connect.ProviderMode.TEST
                        )
                    // Set shopper result URL
                    checkoutSettings.checkoutId = CHECKOUT_ID
                    checkoutSettings.shopperResultUrl = resourcePath ?: ""
                    val intent =
                        checkoutSettings.createCheckoutActivityIntent(this)
                    //startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT)
                    onNewIntent(intent)
                }
            }
            CheckoutActivity.RESULT_CANCELED -> {
            }
            CheckoutActivity.RESULT_ERROR -> {
                /* error occurred */
                var error: PaymentError? =
                    data?.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_ERROR)
                Log.d("TAG",
                    "Msg: ${error?.errorMessage.toString()} \n Code: ${error?.errorCode.toString()} \n Info: ${error?.errorInfo.toString()}")
            }
        }
    }
}
