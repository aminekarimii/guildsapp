package com.sqli.guildes.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.main.MainActivity
import kotlinx.android.synthetic.main.login_fragment.*
import log
import onRightDrawableClicked
import safe

class LoginFragment : Fragment(){

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //UI setup
        inputUsername.onRightDrawableClicked { it.text.clear() }

        //ViewModel Setup
        val dataManager = context?.let { DataManager(it) }
        val loginViewModelFactory = dataManager?.let { LoginViewModelFactory(it) }
        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)
        loginViewModel.message.observe(viewLifecycleOwner, Observer{ message -> handleError(message)})

        //loginViewModel.getToken()

        btnLogin.setOnClickListener { login()}
    }

    fun handleError(message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun login() {
        val username : String = inputUsername.text.toString()
        val password : String = inputPassword.text.toString()

        if (!loginViewModel.isUsernameAndPasswordValid(username, password))
            return Toast.makeText(context, getString(R.string.invalid_username_password), Toast.LENGTH_SHORT).show()

        loginViewModel.getRequestToken(username, password)

        loginViewModel.requestToken.observe(viewLifecycleOwner, Observer { tokenResource ->
            when (tokenResource) {
                is Resource.Success -> {
                    log("Login................")
                    openHome()
                }
                is Resource.Error -> {
                    handleError(getString(R.string.error_login))
                }
                is Resource.Loading -> {
                    // TODO handle this
                }
            }.safe
        })
    }

    fun openHome() {
        val intent = Intent(activity, MainActivity::class.java)
        activity!!.startActivity(intent)
        activity!!.finish()
    }


}
