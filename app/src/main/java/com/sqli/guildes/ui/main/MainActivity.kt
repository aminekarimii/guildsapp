package com.sqli.guildes.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.sqli.guildes.R

import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.sqli.guildes.core.extensions.replaceFragmentInActivity
import com.sqli.guildes.ui.home.HomeFragment
import com.sqli.guildes.ui.login.LoginActivity


class MainActivity : AppCompatActivity() {

    companion object {
        fun navigate (context: Context) : Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragmentInActivity(HomeFragment.newInstance(), R.id.container)

    }

}
