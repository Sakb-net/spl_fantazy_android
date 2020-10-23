package com.sakb.spl.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.utils.LanguageUtil
import com.zeugmasolutions.localehelper.LocaleHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : BaseActivity(){

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
        val profileImage =header.findViewById<ImageView>(R.id.profile_image)
        val profileName = header.findViewById<TextView>(R.id.profile_name)
        profileName.text = user?.data?.displayName
        Glide.with(profileImage.context)
            .load(Constants.baseUrl + user?.data?.image)
            .circleCrop()
            .into(profileImage)

//        navView.setNavigationItemSelectedListener(this)
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
}
