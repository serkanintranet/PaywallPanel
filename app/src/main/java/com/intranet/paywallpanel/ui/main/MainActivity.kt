package com.intranet.paywallpanel.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.ActivityMainBinding
import com.intranet.paywallpanel.util.*
import com.intranet.paywallpanel.util.drawerbehavior.Advance3DDrawerLayout
import com.intranet.paywallpanel.util.drawerbehavior.AdvanceDrawerLayout


class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var drawerLayout: Advance3DDrawerLayout
    lateinit var navHostFragment: NavHostFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            setupUI()
            handleClick()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun handleClick() {
        binding.include.rlMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun changeStatusBarColor() {
        val window: Window = window

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDarkColor)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.backgroundColor)
    }

    private fun setupUI() {
        // region navHost
        //val toolbar = findViewById<Toolbar>(R.id.toolbar)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = findViewById(R.id.drawer_layout)!!

        drawerLayout.setViewRotation(GravityCompat.START, 15F)
        drawerLayout.setViewScale(GravityCompat.START, 0.9f); //set height scale for main view (0f to 1f)
        drawerLayout.setViewElevation(GravityCompat.START, 20F); //set main view elevation when drawer open (dimension)
        drawerLayout.setViewScrimColor(GravityCompat.START, Color.TRANSPARENT); //set drawer overlay coloe (color)
        drawerLayout.drawerElevation = 20F; //set drawer elevation (dimension)
        drawerLayout.setContrastThreshold(3F); //set maximum of contrast ratio between white text and background color.
        drawerLayout.setRadius(GravityCompat.START, 25F);

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardFragment,
                R.id.transfersFragment,
                R.id.paymentGatewaysFragment,
                R.id.profileFragment,
                //R.id.testToolFragment,
                R.id.settingsFragment
            ), drawerLayout
        )

        // toolbar?.setupWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view)?.setupWithNavController(navController)
        binding.include.tvHeader.text = resources.getString(R.string.main_page)
        findViewById<NavigationView>(R.id.nav_view)?.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dashboardFragment -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    binding.include.tvHeader.text = resources.getString(R.string.main_page)
                    changeStatusBarColor()
                    menuItem.onNavDestinationSelected(navController) || super.onOptionsItemSelected(menuItem)
                }
                R.id.transfersFragment -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    changeStatusBarColor()
                    binding.include.tvHeader.text = resources.getString(R.string.transfers_page)
                    menuItem.onNavDestinationSelected(navController) || super.onOptionsItemSelected(menuItem)
                }
                R.id.paymentGatewaysFragment -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    changeStatusBarColor()
                    binding.include.tvHeader.text = resources.getString(R.string.gateways_page)
                    menuItem.onNavDestinationSelected(navController) || super.onOptionsItemSelected(menuItem)
                }
                R.id.profileFragment -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    changeStatusBarColor()
                    binding.include.tvHeader.text = resources.getString(R.string.profile)
                    menuItem.onNavDestinationSelected(navController) || super.onOptionsItemSelected(menuItem)
                }
                /*R.id.testToolFragment -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    changeStatusBarColor()
                    binding.include.tvHeader.text = resources.getString(R.string.test_tool_page)
                    menuItem.onNavDestinationSelected(navController) || super.onOptionsItemSelected(menuItem)
                }*/
                R.id.settingsFragment -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    changeStatusBarColor()
                    binding.include.tvHeader.text = resources.getString(R.string.settings_page)
                    menuItem.onNavDestinationSelected(navController) || super.onOptionsItemSelected(menuItem)
                }
                else -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    changeStatusBarColor()
                    displayAlert(resources.getString(R.string.logout_question), StatusType.QUESTION, true, object : AlertCallback{
                        override fun proceed() {
                            val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(this@MainActivity) }
                            preferenceHelper.setToken("")
                            navigateToSplash()
                        }

                        override fun cancel() {
                            
                        }
                    })
                    super.onOptionsItemSelected(menuItem)
                }
            }
        }

        //findViewById<NavigationView>(R.id.nav_view)?.setupWithNavController(navController)

        // endregion navHost
    }

}