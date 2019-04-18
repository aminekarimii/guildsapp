package com.sqli.guildes.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.sqli.guildes.R
import com.sqli.guildes.ui.login.LoginActivity
import com.sqli.guildes.ui.main.MainActivity
import com.sqli.guildes.utils.extensions.obtainViewModel

class SplachActivity : AppCompatActivity(), SplashNavigator {

    private lateinit var mViewModel : SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)


        mViewModel = obtainViewModel(SplashViewModel::class.java).apply {
            decideNextActivity.observe(this@SplachActivity, Observer {
                if (it) openMainActivity()
                else openLoginActivity()
            })
        }

        mViewModel.startSeeding()

    }

    override fun openLoginActivity() {
        startActivity(LoginActivity.navigate(this))
        finish()
    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}