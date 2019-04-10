package com.sqli.guildes.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.ui.main.MainActivity
import com.sqli.guildes.utils.extensions.obtainViewModel

class LoginActivity : AppCompatActivity() , LoginNavigator{


    companion object {
        fun navigate (context: Context) : Intent {
            return Intent(context,LoginActivity::class.java)
        }
    }


    override fun handleError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private lateinit var mViewModel : LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)


        mViewModel = obtainViewModel().apply {

            requestToken.observe(this@LoginActivity, Observer {
                if (it is Resource.Success) openMainActivity()
            })

            message.observe(this@LoginActivity, Observer { handleError(it) })

        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow()
        }
    }

    fun obtainViewModel(): LoginViewModel = obtainViewModel(LoginViewModel::class.java)



}
