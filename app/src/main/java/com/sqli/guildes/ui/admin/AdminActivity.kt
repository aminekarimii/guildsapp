package com.sqli.guildes.ui.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sqli.guildes.R
import com.sqli.guildes.ui.main.MainActivity

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
    }

    companion object {
        fun navigate(context: Context): Intent {
            return Intent(context, AdminActivity::class.java)
        }
    }
}
