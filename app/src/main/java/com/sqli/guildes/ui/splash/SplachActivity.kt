package com.sqli.guildes.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.sqli.guildes.R
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.login.LoginActivity
import com.sqli.guildes.ui.login.LoginViewModel
import com.sqli.guildes.ui.base.BaseViewModelFactory
import com.sqli.guildes.ui.main.MainActivity

class SplachActivity : AppCompatActivity(), SplashNavigator {

    private lateinit var splashViewModel : SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)


        val dataManager = DataManager(this)
        val factory = BaseViewModelFactory(dataManager)
        splashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        splashViewModel.navigator = this


        splashViewModel.decideNextActivity()

    }

    override fun openLoginActivity() {
        LoginActivity.navigate(this)
        finish()
    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
