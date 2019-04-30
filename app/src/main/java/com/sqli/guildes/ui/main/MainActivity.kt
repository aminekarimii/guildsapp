package com.sqli.guildes.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.sqli.guildes.R

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.core.extensions.replaceFragmentInActivity
import com.sqli.guildes.core.extensions.setupActionBar
import com.sqli.guildes.ui.common.BackPressListener
import com.sqli.guildes.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import log


class MainActivity : AppCompatActivity() {

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


        //replaceFragmentInActivity(HomeFragment.newInstance(), R.id.container)

        navController = Navigation.findNavController(findViewById(R.id.mainNavHostFragment))
        NavigationUI.setupActionBarWithNavController(this, navController, mainRootView)
        navView.setupWithNavController(navController)

        mainViewModel = obtainViewModel().apply {
            snackbar.observe(this@MainActivity, Observer {
                val snackBar = Snackbar.make(mainRootView, it.message, it.length)
                if (it.actionText != null && it.action != null) {
                    snackBar.setAction(it.actionText, it.action)
                }
                snackBar.show()
            })
            toolbarTitle.observe(this@MainActivity, Observer {
                supportActionBar?.apply { title = it }
            })
            backPressListener.observe(this@MainActivity, Observer {
                this@MainActivity.backPressListener = it
            })
        }
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
