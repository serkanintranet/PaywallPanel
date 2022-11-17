package com.intranet.paywallpanel.ui.main

import android.content.Context
import android.nfc.NfcManager
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.elekse.esnekpos.base.BaseFragment
import com.google.android.material.navigation.NavigationView
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentMainBinding


class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    // region global initial
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout
    lateinit var navHostFragment: NavHostFragment
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            setupUI()
            handleClick()
        } catch (ex: Exception) {
            //Ex
        }
    }
    // endregion android lifecycle

    // region UI Process
    private fun setupUI() {
        // region navHost
        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)

        navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = view?.findViewById(R.id.drawer_layout)!!

        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.dashboardFragment, R.id.transfersFragment), drawerLayout)

        toolbar?.setupWithNavController(navController, appBarConfiguration)

        view?.findViewById<NavigationView>(R.id.nav_view)?.setupWithNavController(navController)

        binding.navView.itemIconTintList = null
        // endregion navHost
    }

    private fun handleClick() {

    }

    // endregion UI Process

    // region ObservableData

    // endregion ObservableData
}