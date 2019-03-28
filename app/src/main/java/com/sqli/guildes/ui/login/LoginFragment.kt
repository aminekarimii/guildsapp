package com.sqli.guildes.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sqli.guildes.R
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.base.BaseViewModelFactory
import com.sqli.guildes.ui.main.MainActivity
import kotlinx.android.synthetic.main.login_fragment.*
import onRightDrawableClicked

class LoginFragment : Fragment(), LoginNavigator {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val dataManager = context?.let { DataManager(it) }
        val factory = dataManager?.let { BaseViewModelFactory(it) }
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        loginViewModel.navigator = this


        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        inputUsername.onRightDrawableClicked { it.text.clear() }
        btnLogin.setOnClickListener { login() }

    }

    fun login() {
        val username : String = inputUsername.text.toString()
        val password : String = inputPassword.text.toString()

        if (!loginViewModel.isUsernameAndPasswordValid(username, password))
            return Toast.makeText(context, getString(R.string.invalid_username_password), Toast.LENGTH_SHORT).show()

        loginViewModel.getRequestToken(username, password)
    }

    override fun handleError(message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun openMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        activity!!.startActivity(intent)
        activity!!.finish()
    }




}
