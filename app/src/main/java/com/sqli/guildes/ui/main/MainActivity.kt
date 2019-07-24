package com.sqli.guildes.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.sqli.guildes.R

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.core.extensions.setupActionBar
import com.sqli.guildes.ui.common.BackPressListener
import com.sqli.guildes.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mainViewModel: MainViewModel
    private var backPressListener: BackPressListener? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(R.id.mainToolbar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        navController = Navigation.findNavController(findViewById(R.id.mainNavHostFragment))
        navView.setNavigationItemSelectedListener(this)

        setupMainViewModel()

        mainViewModel.checkAuthentication()
    }

    private fun setupMainViewModel() {
        mainViewModel = obtainViewModel().apply {
            snackbar.observe(this@MainActivity, Observer {
                val snackBar = Snackbar.make(mainRootView, it.message, it.length)
                if (it.actionText != null && it.action != null) {
                    snackBar.setAction(it.actionText, it.action)
                }
                snackBar.show()
            })
            toolbarTitle.observe(this@MainActivity, Observer {
                supportActionBar?.apply {
                    if(it.isEmpty()) hide()
                    else show()
                    title = it
                }
            })
            backPressListener.observe(this@MainActivity, Observer {
                this@MainActivity.backPressListener = it
            })
            isAuthenticated.observe(this@MainActivity, Observer {
                if(!it) {
                    val i = LoginActivity.navigate(this@MainActivity)
                    startActivity(i)
                    finish()
                }
            })
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.home_navigation_menu_item ->
                navController.navigate(R.id.homeFragment)
            R.id.my_guilde_navigation_menu_item ->
                navController.navigate(R.id.guildeDetailsFragment)
            R.id.my_contributions_navigation_menu_item -> { }
            R.id.make_contribution_navigation_menu_item -> { }
            R.id.all_contributions_navigation_menu_item -> { }
            R.id.profile_navigation_menu_item -> { }
            R.id.sign_out_navigation_menu_item -> { }
        }
        mainRootView.closeDrawer(GravityCompat.START)
        //menuItem.isChecked = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    mainRootView.openDrawer(GravityCompat.START)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }


    override fun onBackPressed() {
        if (backPressListener == null || backPressListener?.onBackPressed() == true) {
            super.onBackPressed()
        }
    }

    fun obtainViewModel(): MainViewModel = obtainViewModel(MainViewModel::class.java)

    companion object {
        fun navigate (context: Context) : Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}
