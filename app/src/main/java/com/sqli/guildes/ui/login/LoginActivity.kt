package com.sqli.guildes.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.core.extensions.replaceFragmentInActivity
import com.sqli.guildes.ui.main.MainActivity

class LoginActivity : AppCompatActivity(){

    private lateinit var loginViewModel : LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        replaceFragmentInActivity(LoginFragment.newInstance(), R.id.container)

        loginViewModel = obtainViewModel().apply {
            isAuthenticated.observe(this@LoginActivity, Observer { if(it) openMainActivity() })
            message.observe(this@LoginActivity, Observer { handleError(it) })
        }
    }

    companion object {
        fun navigate (context: Context) : Intent {
            return Intent(context,LoginActivity::class.java)
        }
    }

    private fun handleError(message: String, length : Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(findViewById(android.R.id.content), message, length).show()
    }

    private fun openMainActivity() {
        val i = MainActivity.navigate(this)
        startActivity(i)
        finish()
    }

    fun obtainViewModel(): LoginViewModel = obtainViewModel(LoginViewModel::class.java)

}
