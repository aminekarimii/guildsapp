package com.sqli.guildes.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sqli.guildes.R
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.data.utils.UsersTypes
import com.sqli.guildes.ui.admin.AdminActivity
import com.sqli.guildes.ui.login.LoginActivity
import com.sqli.guildes.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_SCREEN_DURATION = 3000L
    }

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel = obtainViewModel(SplashViewModel::class.java).apply {

            decideNextActivity.observe(this@SplashActivity, Observer {
                when (it) {
                    -1 -> openLoginActivity()
                    UsersTypes.ADMIN.ordinal -> openAdminActivity()
                    UsersTypes.CONTRIBUTOR.ordinal -> openMainActivity()
                }
            })

            Handler().postDelayed(::startSeeding, SPLASH_SCREEN_DURATION)
        }
    }

    private fun openLoginActivity() {
        startActivity(LoginActivity.navigate(this))
        finish()
    }

    private fun openAdminActivity() {
        startActivity(AdminActivity.navigate(this))
        finish()
    }

    private fun openMainActivity() {
        startActivity(MainActivity.navigate(this))
        finish()
    }
}
