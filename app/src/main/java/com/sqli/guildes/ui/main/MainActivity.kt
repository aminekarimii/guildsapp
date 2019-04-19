package com.sqli.guildes.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.sqli.guildes.R

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.core.extensions.replaceFragmentInActivity
import com.sqli.guildes.core.extensions.setupActionBar
import com.sqli.guildes.ui.common.BackPressListener
import com.sqli.guildes.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var backPressListener: BackPressListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(R.id.mainToolbar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        setupNavigationDrawer()

        replaceFragmentInActivity(HomeFragment.newInstance(), R.id.container)

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


    private fun setupNavigationDrawer() {
        mainRootView.apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
        setupDrawerContent(findViewById(R.id.nav_view))
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_navigation_menu_item -> { /* Do nothing, we're already on that screen */ }
                R.id.guilde_navigation_menu_item -> {}
                R.id.my_contributions_navigation_menu_item -> {}
                R.id.make_contribution_navigation_menu_item -> {}
                R.id.all_contributions_navigation_menu_item -> {}
                R.id.profile_navigation_menu_item -> {}
                R.id.sign_out_navigation_menu_item -> {}
            }
            // Close the navigation drawer when an item is selected.
            menuItem.isChecked = true
            mainRootView.closeDrawers()
            true
        }
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
