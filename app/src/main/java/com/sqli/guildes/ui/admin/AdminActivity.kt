package com.sqli.guildes.ui.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sqli.guildes.R
import com.sqli.guildes.core.extensions.setupActionBar

class AdminActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        setupActionBar(R.id.adminToolbar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        navController = Navigation.findNavController(findViewById(R.id.adminNavHostFragment))

    }

    companion object {
        fun navigate(context: Context): Intent {
            return Intent(context, AdminActivity::class.java)
        }
    }
}
