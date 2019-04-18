package com.sqli.guildes.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sqli.guildes.R
import com.sqli.guildes.ui.home.HomeFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow()
        }
    }

}